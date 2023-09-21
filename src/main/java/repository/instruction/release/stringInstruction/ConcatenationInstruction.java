package repository.instruction.release.stringInstruction;

import Interpreter.Data;
import repository.Type;
import repository.instruction.release.Instruction;
import repository.instruction.release.InstructionOverload;

public class ConcatenationInstruction extends Instruction {

    public ConcatenationInstruction() {
        super(true);
    }

    protected InstructionOverload getValidRealize(Type[] object) {
        for (Type type : object) {
            if (!type.equals(Type.Str))
                throw new IllegalArgumentException();
        }
        return (list) -> {
            StringBuilder stringBuilder = new StringBuilder();
            for (Instruction argument : list) {
                stringBuilder.append((String) argument.getValue().getObject());
            }
            return Data.stringToData(stringBuilder.toString());
        };
    }


}
