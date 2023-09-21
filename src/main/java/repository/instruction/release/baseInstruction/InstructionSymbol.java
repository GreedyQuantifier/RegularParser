package repository.instruction.release.baseInstruction;

import exceptions.interpreter.InstructionExecutionException;
import repository.Type;
import repository.instruction.release.Instruction;
import repository.instruction.release.InstructionOverload;
import utils.ExceptionUtils;

public class InstructionSymbol extends Instruction {

    String name;


    public InstructionSymbol(String name) {
        this.name = name;
    }

    @Override
    protected InstructionOverload getValidRealize(Type[] type) {
        throw new InstructionExecutionException(ExceptionUtils.messageErrorExecute(this));
    }

}