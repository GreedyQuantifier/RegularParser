package repository.instruction.release.baseInstruction;

import Interpreter.Data;
import exceptions.interpreter.InstructionException;
import repository.Type;
import repository.instruction.release.Instruction;
import repository.instruction.release.InstructionOverload;
import repository.instruction.release.InterimInstruction;

public class PutInstruction extends Instruction implements InterimInstruction {

    String key;

    public PutInstruction() {
        super(true);
    }

    @Override
    public String getKey() {
        return key;
    }





//    Как это исправить лучше?
    @Override
    protected InstructionOverload getValidRealize(Type[] type) {
        if (type.length == 2 && type[0].equals(Type.Str)) {
            key = getInstructions().get(0).getValue().getString();
            return (list) -> getInstructions().get(1).getValue();
        }
        throw new InstructionException("132");
    }

}
