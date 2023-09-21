package exceptions.interpreter;

import repository.instruction.release.Instruction;

public class InstructionException extends RuntimeException {
    public InstructionException(String s) {
        super(s);
    }


}


