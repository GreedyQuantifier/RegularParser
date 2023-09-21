package parser;

import exceptions.instruction.InstructionHasNotClosedException;
import exceptions.parser.IllegalParserArgumentException;
import exceptions.parser.ParserInsertNotValidException;
import exceptions.parser.ParserInstructionBodyHasNotFoundException;
import exceptions.token.TokenHasNotClosedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParserExceptionTest {

    @Test
    public void testContainerException() {
        Assertions.assertThrows(TokenHasNotClosedException.class, () -> Parser.parse("1,'ewqewq"));
        Assertions.assertThrows(TokenHasNotClosedException.class, () -> Parser.parse("'ewqewq"));
    }



    @Test
    public void testBasicSyntax() {
        Assertions.assertThrows(RuntimeException.class, () -> Parser.parse("("));
        Assertions.assertThrows(IllegalParserArgumentException.class, () -> Parser.parse(")"));
        Assertions.assertThrows(RuntimeException.class, () -> Parser.parse("(("));
    }


    @Test
    public void testIsSyntaxValid() {
        Assertions.assertThrows(IllegalParserArgumentException.class, () -> Parser.parse("(sda)sda"));
        Assertions.assertThrows(IllegalParserArgumentException.class, () -> Parser.parse("ads(sda)"));
        Assertions.assertThrows(IllegalParserArgumentException.class, () -> Parser.parse("()()"));

    }


    @Test
    public void testInsertValid() {
        Assertions.assertThrows(ParserInsertNotValidException.class, () -> Parser.parse("$href"));
        Assertions.assertThrows(ParserInsertNotValidException.class, () -> Parser.parse("$href()"));
    }


    @Test
    public void testIsInstructionClose() {
        Assertions.assertThrows(InstructionHasNotClosedException.class, () -> Parser.parse("href("));
        Assertions.assertThrows(InstructionHasNotClosedException.class, () -> Parser.parse("("));
    }

    @Test
    public void testHasInstructionBody() {
        Assertions.assertThrows(ParserInstructionBodyHasNotFoundException.class, () -> Parser.parse("href"));
    }


}
