package parser;

import Interpreter.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import repository.instruction.release.Instruction;
import repository.instruction.release.baseInstruction.InsertInstruction;
import repository.instruction.release.baseInstruction.NullInstruction;
import repository.instruction.release.baseInstruction.ObjInstruction;
import repository.instruction.release.baseInstruction.StringInstruction;
import repository.instruction.release.stringInstruction.ConcatenationInstruction;

import java.util.List;
import java.util.stream.Collectors;

class ParserTest {

    @Test
    public void testContainer() {
        Assertions.assertIterableEquals(set("(1,'()')"),
                List.of(ObjInstruction.class, StringInstruction.class, StringInstruction.class));
        Assertions.assertIterableEquals(set("1,'(\",ewq,ewq,\")'"),
                List.of(ObjInstruction.class, StringInstruction.class, StringInstruction.class));

    }


    @Test
    public void testBasicFeatures() {

        String expressionBasicEvaluate = "1,2";
        Assertions.assertIterableEquals(set(expressionBasicEvaluate),
                List.of(ObjInstruction.class, StringInstruction.class, StringInstruction.class));


        Assertions.assertEquals(getContent("$var").get(0).getString(), "var");

        Assertions.assertIterableEquals(set("$var"),
                List.of(InsertInstruction.class));



//        Assertions.assertEquals(getContent("$'href'").get(0).getString(), "href");


        Assertions.assertIterableEquals(set("concat(1,2)"),
                List.of(ConcatenationInstruction.class, StringInstruction.class, StringInstruction.class));

        Assertions.assertIterableEquals(set("12,12"), set("(12,12)"));

        Assertions.assertIterableEquals(set(""),
                List.of(ObjInstruction.class));


    }


    @Test
    public void testOptimizeTreeFunction() {
        Assertions.assertIterableEquals(set("(arg,arg2)"),
                List.of(ObjInstruction.class, StringInstruction.class, StringInstruction.class));

    }


    @Test
    public void testValidTreeWithNull() {


        Assertions.assertIterableEquals(set("(),arg"),
                List.of(ObjInstruction.class, ObjInstruction.class, StringInstruction.class));
        Assertions.assertIterableEquals(set("arg,()"),
                List.of(ObjInstruction.class, StringInstruction.class, ObjInstruction.class));
        Assertions.assertIterableEquals(set("arg,arg2"),
                List.of(ObjInstruction.class, StringInstruction.class, StringInstruction.class));
        Assertions.assertIterableEquals(set("(),()"),
                List.of(ObjInstruction.class, ObjInstruction.class, ObjInstruction.class));


        Assertions.assertIterableEquals(set("arg,"),
                List.of(ObjInstruction.class, StringInstruction.class, NullInstruction.class));

        Assertions.assertIterableEquals(set(",arg"),
                List.of(ObjInstruction.class, NullInstruction.class, StringInstruction.class));

        Assertions.assertIterableEquals(set("(,)"),
                List.of(ObjInstruction.class, NullInstruction.class, NullInstruction.class));

        Assertions.assertIterableEquals(set(",,"),
                List.of(ObjInstruction.class, NullInstruction.class, NullInstruction.class, NullInstruction.class));
    }


    List<Class> set(String expression) {
        return Parser.parse(expression).targetGraph.vertexSet().stream().map(Instruction::getClass)
                .collect(Collectors.toList());
    }

    List<Data> getContent(String expression) {
        return Parser.parse(expression).targetGraph.vertexSet().stream().map(Instruction::getValue)
                .collect(Collectors.toList());
    }


}