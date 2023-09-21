package repository.instruction.release;

import Interpreter.Data;
import exceptions.interpreter.InstructionExecutionException;
import org.jgrapht.Graph;
import repository.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public abstract class Instruction {

    private final List<Instruction> instructions = new ArrayList<>();
    boolean constantValue = false;
    int positionExpression;
    private boolean isExecute;
    private Data value;


    public Instruction() {
    }

    public Instruction(boolean constantValue) {
        this.constantValue = constantValue;
    }

    public Instruction(Data content) {
        value = content;
    }

    public Instruction(boolean constantValue, Data value) {
        this.constantValue = constantValue;
        this.value = value;
    }

    public Instruction(Data value, boolean isExecute, boolean constantValue) {
        this.isExecute = isExecute;
        this.value = value;
        this.constantValue = constantValue;
    }


    abstract protected InstructionOverload getValidRealize(Type[] type);

    public Data execute() {

        Type[] objects =
                instructions.stream().map(instruction -> instruction.getValue().getType()).toArray(Type[]::new);
        value = getValidRealize(objects).doFunction(instructions);
        isExecute = true;

        return value;
    }

    @Override
    public String toString() {
        return getClass().toString();
    }


    //    DebugFunction
    public String tree() {
        String.format("%s ( value = %s ,ret type = %s)", this.getClass().toString(), Optional.ofNullable(value));

        return Arrays.toString(instructions.toArray());
    }


    public Instruction addVertex(Instruction argument, Graph graph) {
        instructions.add(argument);
        graph.addEdge(this, argument);
        return this;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public Data getValue() {
        if (!isExecute()) throw new InstructionExecutionException("");
        return value;
    }

    protected void setValue(Data value) {
        this.value = value;
    }

    public Type getType() throws InstructionExecutionException {
        if (!isExecute || value == null)
            throw new InstructionExecutionException("Instruction %s is not execute. Position %d"
                    .formatted(this.getClass().toString(), getPosition()));
        return value.getType();
    }

    public int getPosition() {
        return positionExpression;
    }


    public boolean isExecute() {
        return isExecute;
    }


    public boolean isConstantValue() {
        return constantValue;
    }
}
