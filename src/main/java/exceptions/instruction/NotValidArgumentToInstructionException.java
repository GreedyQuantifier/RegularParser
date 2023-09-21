package exceptions.instruction;

import exceptions.parser.ParserBaseInstructionException;

public class NotValidArgumentToInstructionException extends ParserBaseInstructionException {
    public NotValidArgumentToInstructionException(String message) {
        super(message);
    }
}
