package oop.ex4B2;

import java.util.List;
import java.util.ArrayList;

public class Parser {
    public Program parse(List<String> lines) {
        List<Instruction> instructions = new ArrayList<>();
        
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) { // 念の為空列確認
                continue;
            }
            
            String[] parts = line.split("\\s+"); // コマンドと数値に分割
            String command = parts[0];
            
            Instruction inst;
            switch (command) { // コマンドで場合分け
                case "acc":
                    if (parts.length != 2) {
                        // 引数の数が異常
                        throw new IllegalArgumentException("acc requires one argument");
                    }
                    try {
                        int value = Integer.parseInt(parts[1]);
                        inst = new AccInstruction(value);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid integer argument: " + parts[1]);
                    }
                    break;
                    
                case "jmp":
                    if (parts.length != 2) {
                        // 引数の数が異常
                        throw new IllegalArgumentException("jmp requires one argument");
                    }
                    try {
                        int target = Integer.parseInt(parts[1]);
                        inst = new JmpInstruction(target);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid integer argument: " + parts[1]);
                    }
                    break;
                    
                case "jnq":
                    if (parts.length != 2) {
                        // 引数の数が異常
                        throw new IllegalArgumentException("jnq requires one argument");
                    }
                    try {
                        int target = Integer.parseInt(parts[1]);
                        inst = new JnqInstruction(target);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid integer argument: " + parts[1]);
                    }
                    break;
                    
                case "inc":
                    if (parts.length != 1) {
                        // 引数の数が異常
                        throw new IllegalArgumentException("inc takes no arguments");
                    }
                    inst = new IncInstruction();
                    break;
                    
                case "dec":
                    if (parts.length != 1) {
                        // 引数の数が異常
                        throw new IllegalArgumentException("dec takes no arguments");
                    }
                    inst = new DecInstruction();
                    break;
                    
                default:
                    throw new IllegalArgumentException("Unknown instruction: " + command);
            }
            
            instructions.add(inst);
        }
        
        return new Program(instructions);
    }
}

// 命令の基底インターフェース
interface Instruction {
    void execute(Program program);
}

// acc命令
class AccInstruction implements Instruction {
    private int value;
    
    public AccInstruction(int value) {
        this.value = value;
    }
    
    public void execute(Program program) {
        program.accumulator += value;
        program.programCounter++;
    }
}

// jmp命令
class JmpInstruction implements Instruction {
    private int target;
    
    public JmpInstruction(int target) {
        this.target = target;
    }
    
    public void execute(Program program) {
        program.programCounter = target;
    }
}

// jnq命令
class JnqInstruction implements Instruction {
    private int target;
    
    public JnqInstruction(int target) {
        this.target = target;
    }
    
    public void execute(Program program) {
        if (program.accumulator != target) {
            program.programCounter = target;
        } else {
            program.programCounter++;
        }
    }
}

// inc命令
class IncInstruction implements Instruction {
    public void execute(Program program) {
        program.accumulator += 1;
        program.programCounter++;
    }
}

// dec命令
class DecInstruction implements Instruction {
    public void execute(Program program) {
        program.accumulator -= 1;
        program.programCounter++;
    }
}
