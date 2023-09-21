package repository.instruction.release;

import Interpreter.Data;

import java.util.List;

@FunctionalInterface
public interface InstructionOverload {
    Data doFunction(List<Instruction> list);
}
