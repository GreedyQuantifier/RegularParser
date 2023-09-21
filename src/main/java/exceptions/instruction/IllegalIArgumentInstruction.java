package exceptions.instruction;

import exceptions.parser.ParserBaseInstructionException;

public class IllegalIArgumentInstruction extends ParserBaseInstructionException {
    public IllegalIArgumentInstruction(String message) {
        super(message);
    }
}
