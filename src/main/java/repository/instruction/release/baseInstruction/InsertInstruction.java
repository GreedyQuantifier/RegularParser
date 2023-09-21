package repository.instruction.release.baseInstruction;

import Interpreter.Data;
import exceptions.interpreter.InstructionExecutionException;
import repository.Type;
import repository.instruction.release.Instruction;
import repository.instruction.release.InstructionOverload;
import utils.ExceptionUtils;

public class InsertInstruction extends Instruction {
    public InsertInstruction() {
        super();
    }

    public InsertInstruction(String value) {
        super(Data.stringToData(value),true,true);
    }


    @Override
    public InstructionOverload getValidRealize(Type[] type) {
        throw new InstructionExecutionException(ExceptionUtils.messageErrorExecute(this));
    }

    public void setValue(Data val) {
        super.setValue(val);
    }

}
