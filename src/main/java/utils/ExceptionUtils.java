package utils;

import repository.instruction.release.Instruction;

public class ExceptionUtils {

    public static String messageErrorExecute(Instruction instruction) {
        return "Instruction %s  cannot be evaluate. Position %d."
                .formatted(instruction.getClass().toString(), instruction.getPosition());


    }
}
