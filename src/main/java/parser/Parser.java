package parser;

import exceptions.instruction.InstructionHasNotClosedException;
import exceptions.parser.IllegalParserArgumentException;
import exceptions.parser.ParserInsertNotValidException;
import exceptions.parser.ParserInstructionBodyHasNotFoundException;
import exceptions.token.TokenHasNotClosedException;
import lexer.Lexer;
import lexer.Token;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;
import repository.Storage;
import repository.instruction.release.Instruction;
import repository.instruction.release.baseInstruction.InsertInstruction;
import repository.instruction.release.baseInstruction.ObjInstruction;
import repository.instruction.release.baseInstruction.StringInstruction;
import utils.GraphViewUtil;

import java.util.*;

public class Parser {

    final public DirectedGraph<Token, DefaultEdge> tokenGraph =
            new DefaultDirectedGraph<>(DefaultEdge.class);

    final public DirectedGraph<Instruction, DefaultEdge> targetGraph =
            new DefaultDirectedGraph<>(DefaultEdge.class);


    public final Map<String, List<InsertInstruction>> inserts = new HashMap<>();


    Boolean debug;


    public Parser(Lexer lexer, Boolean debug) {
        this.debug = debug;
        this.createTree(lexer.tokenize).parseTree();

    }

    public Parser(Lexer lexer) {
        this(lexer, false);
    }


    public static Parser parse(String regularExpression) {
        return new Parser(new Lexer(regularExpression));
    }

    Parser parseTree() {

        Object[] objects = tokenGraph.vertexSet().toArray();
        createInstruction((Token) objects[0], Storage.instructionByName("obj"));

        if (debug) GraphViewUtil.graphImage(targetGraph, "ParseTree");

        optimizeTree();

        return this;
    }

    private void optimizeTree() {
        DepthFirstIterator<Instruction, DefaultEdge> iterator = new DepthFirstIterator<>(targetGraph);

        List<Instruction> list = new ArrayList<>();

        while (iterator.hasNext()) {
            Instruction next = iterator.next();
            if (iterator.hasNext() && targetGraph.outDegreeOf(next) == 1 && next instanceof ObjInstruction)
                list.add(next);
        }


        for (Instruction item : list) {
            if (targetGraph.incomingEdgesOf(item).size() != 0) {
                Instruction parent = Graphs.predecessorListOf(targetGraph, item).get(0);
                Instruction child = Graphs.successorListOf(targetGraph, item).get(0);
                targetGraph.addEdge(parent, child);
            }
            targetGraph.removeVertex(item);
        }

        if (debug) GraphViewUtil.graphImage(targetGraph, "OptimizeParseTree");
    }

    public Parser createTree(List<Token> list) {


        Stack<Token> stack = new Stack<>();
        Token context = new Token("(", true);
        String trap = null;
        StringBuilder newToken = new StringBuilder();

        tokenGraph.addVertex(context);
        stack.push(context);

        for (int i = 0; i < list.size(); i++) {
            Token token = list.get(i);
            String item = token.getToken();

            if (trap != null && trap.equals(item)) {
                token = new Token(newToken.toString(), false);
                newToken = new StringBuilder();
                trap = null;
                item = "";
            } else if (trap != null) {
                if (list.size() == i + 1)
                    throw new TokenHasNotClosedException("Token %s hasn't closed!".formatted(trap));
                newToken.append(item);
                continue;
            }

            if (item.equals("'") || item.equals("\"")) {
                trap = item;
                continue;
            }

            tokenGraph.addVertex(token);
            tokenGraph.addEdge(stack.lastElement(), token);

            switch (item) {
                case "(" -> stack.push(token);
                case ")" -> {
                    if (stack.size() == 1)
                        throw new IllegalParserArgumentException("Syntax Error, expression contains extra characters ')'");
                    stack.pop();
                }

            }

        }
        if (stack.size() > 1)
            throw new InstructionHasNotClosedException("Syntax Error, expression contains extra characters '('");
        Token v = new Token(")", true);
        tokenGraph.addVertex(v);
        tokenGraph.addEdge(context, v);

        if (debug) GraphViewUtil.graphImage(tokenGraph, "CreateTree");

        return this;
    }

    Instruction createInstruction(Token token, Instruction instruction) {

        targetGraph.addVertex(instruction);

        if (tokenGraph.outDegreeOf(token) == 0) {
            return instruction;
        }

        List<Token> tokens = Graphs.successorListOf(tokenGraph, token);

        Boolean isClosed = false;


        for (int i = 0; i < tokens.size(); i++) {
            Instruction childArgument = null;
            String item = tokens.get(i).getToken();
            String prev = i - 1 >= 0 ? tokens.get(i - 1).getToken() : null;
            String next = i + 1 < tokens.size() ? tokens.get(i + 1).getToken() : null;

            if (next != null && !(next.equals(",") || item.equals(",") || item.equals("$") || next.equals(")"))
                    && !(Storage.isNotSymbolInstruction(item) && next.equals("(")))
                throw new IllegalParserArgumentException(tokens, i);


            switch (item) {
                case "(" -> childArgument = createInstruction(tokens.get(i), Storage.instructionByName("obj"));
                case ")" -> isClosed = true;
                case "," -> {
                    if (prev == null)
                        addNode(instruction, Storage.instructionByName("Null"));
                    if (next == null || next.equals(",") || next.equals(")"))
                        addNode(instruction, Storage.instructionByName("Null"));
                }
                case "$" -> {
                    if (next == null || Storage.isInstruction(next)) {
                        throw new ParserInsertNotValidException(tokens, i);
                    }
                    childArgument = addInsertNode(new InsertInstruction(next));
                    i++;
                }
                default -> childArgument = new StringInstruction(item);

            }

            if (Storage.isNotSymbolInstruction(item)) {
                if (next == null || !next.equals("(")) throw new ParserInstructionBodyHasNotFoundException(tokens, i);
                childArgument = createInstruction(tokens.get(++i), Storage.instructionByName(item));
            }


            if (childArgument == null) continue;

            addNode(instruction, childArgument);
        }

        if (!isClosed) throw new InstructionHasNotClosedException("Instruction hasn't closed");

        return instruction;
    }

    private void addNode(Instruction parent, Instruction child) {
        targetGraph.addVertex(child);
        parent.addVertex(child, targetGraph);
    }

    private Instruction addInsertNode(InsertInstruction insertInstruction) {
        String value = insertInstruction.getValue().getString();
        if (inserts.containsKey(value)) inserts.get(value).add(insertInstruction);
        else inserts.put(value, List.of(insertInstruction));
        return insertInstruction;
    }


}

