package repository.instruction.release.stringInstruction;

import Interpreter.Data;
import repository.Type;
import repository.instruction.release.Instruction;
import repository.instruction.release.InstructionOverload;

import java.util.Arrays;

public class ReplaceInstruction extends Instruction {
    public ReplaceInstruction() {
    }

    @Override
    protected InstructionOverload getValidRealize(Type[] type) {
        if (type.length != 3)
            throw new IllegalArgumentException("Illegal argument count, need count 3 , actual " + type.length);

        if (!(type[0].equals(Type.Str) && type[1].equals(Type.Str) && (type[2].equals(Type.Str) || type[2].equals(Type.Null))))
            throw new IllegalArgumentException("Illegal argument types, actual " + Arrays.toString(type));

        return list ->
                Data.stringToData(list.get(0).getValue().toString().replace((CharSequence) list.get(1).getValue().getObject(), (CharSequence) list.get(2).getValue().getObject()));
    }
}
