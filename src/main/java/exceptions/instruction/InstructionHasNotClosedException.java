package exceptions.instruction;

import exceptions.parser.ParserBaseInstructionException;

public class InstructionHasNotClosedException extends ParserBaseInstructionException {
    public InstructionHasNotClosedException(String exception) {
        super(exception);
    }
}
