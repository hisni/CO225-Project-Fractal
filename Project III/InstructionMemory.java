import java.util.*;

public class InstructionMemory {
    private static HashMap<Integer, String> InsMemory;
    public int address;

    public InstructionMemory(){
        InsMemory = new HashMap<Integer, String>();
        address = 0;
    }

    public void addInstruction( String instruction ) {
        InsMemory.put(address, instruction);
        address++;
    }

    public static String readInstruction(int pc){
        return InsMemory.get(pc);
    }

}