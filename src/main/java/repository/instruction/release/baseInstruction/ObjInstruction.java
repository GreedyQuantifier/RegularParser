package repository.instruction.release.baseInstruction;

import Interpreter.Data;
import repository.Type;
import repository.instruction.release.Instruction;
import repository.instruction.release.InstructionOverload;

public class ObjInstruction extends Instruction {

    @Override
    protected InstructionOverload getValidRealize(Type[] type) {
        return Data::objToData;
    }

}
