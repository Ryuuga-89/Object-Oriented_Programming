package oop.ex4B2;

import java.util.List;

public class Program {
    private List<Instruction> instructions;
    int accumulator;
    int programCounter;
    
    public Program(List<Instruction> instructions) {
        this.instructions = instructions;
        this.accumulator = 0;
        this.programCounter = 0;
    }
    
    public int execute() {
        while (programCounter >= 0 && programCounter < instructions.size()) {
            Instruction inst = instructions.get(programCounter);
            inst.execute(this);
        }
        return accumulator;
    }
}
