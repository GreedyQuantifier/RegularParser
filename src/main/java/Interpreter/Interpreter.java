package Interpreter;

import lexer.Lexer;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import parser.Parser;
import repository.instruction.release.Instruction;
import repository.instruction.release.baseInstruction.InsertInstruction;
import repository.instruction.release.baseInstruction.PutInstruction;
import utils.GraphViewUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Interpreter {

    Boolean debug;

    DirectedGraph<Instruction, DefaultEdge> inputGraph;

    Map<String, List<InsertInstruction>> inserts;

    List<PutInstruction> putInstructions = new ArrayList<>();


    public Interpreter(String s) {
        this(s, false);
    }

    public Interpreter(String s, boolean debug) {
        this.debug = debug;
        Parser parser = new Parser(new Lexer(s), debug);
        inputGraph = parser.targetGraph;
        inserts = parser.inserts;
        firstStep();
    }

    private void firstStep() {

        step((Instruction) inputGraph.vertexSet().toArray()[0]);
        if (debug) GraphViewUtil.graphImage(inputGraph, "firstStepOptimize");
    }

    Instruction step(Instruction instruction) {
        if (instruction instanceof PutInstruction)
            putInstructions.add((PutInstruction) instruction);


        if (instruction.isExecute())
            return instruction;

        List<Instruction> children = instruction.getInstructions();

        boolean constantInstruct = true;

        for (Instruction child : children) {
            step(child);
            constantInstruct &= child.isExecute();
        }

        if (constantInstruct && instruction.isConstantValue()) {
            this.execute(instruction);
            children.forEach(child -> inputGraph.removeVertex(child));
        }


        return instruction;
    }


    public Data execute(Instruction next) {

        if (next.isExecute())
            return next.getValue();

        if (inputGraph.outDegreeOf(next) == 0)
            return next.execute();

        List<Instruction> child = Graphs.successorListOf(inputGraph, next);

        child.forEach(this::execute);
        return next.execute();
    }

    public Data execute() {
        interimEvaluate();
        if (debug) GraphViewUtil.graphImage(inputGraph, "finaleTree");
        Instruction next = (Instruction) inputGraph.vertexSet().toArray()[0];
        return execute(next);
    }

    private void interimEvaluate() {

        for (PutInstruction putInstruction : putInstructions) {
            Data value = putInstruction.execute();
            String key = putInstruction.getKey();
            if (inserts.containsKey(key)) inserts.get(key).forEach(instruction -> instruction.setValue(value));
            inserts.remove(key);
            inputGraph.removeVertex(putInstruction);
        }
    }

    String deepFetch(Instruction argument) {
        List<Instruction> argumentTypes = argument.getInstructions();


        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < argumentTypes.size(); i++) {
            stringBuilder.append(argumentTypes.get(i).getValue().toString());
            if (argumentTypes.size() != i + 1)
                stringBuilder.append(",");
        }
        return stringBuilder.toString();
    }
}