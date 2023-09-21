package repository.instruction.release.baseInstruction;

import Interpreter.Data;
import exceptions.interpreter.InstructionExecutionException;
import repository.Type;
import repository.instruction.release.Instruction;
import repository.instruction.release.InstructionOverload;
import utils.ExceptionUtils;

public class StringInstruction extends Instruction {


    public StringInstruction(String token) {
        super(Data.stringToData(token), true,true);
    }


    @Override
    public InstructionOverload getValidRealize(Type[] type) {
        throw new InstructionExecutionException(ExceptionUtils.messageErrorExecute(this));
    }

}
