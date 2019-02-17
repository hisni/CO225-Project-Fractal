import java.util.*;

public class InstructionMemory {
    private HashMap<Integer, String> InsMemory;  //Instruction memory as a Hash Map
    private int address;

    public InstructionMemory(){     //Constructor
        InsMemory = new HashMap<Integer, String>();     
        address = 0;
    }

    public void writeIM( String instruction ) {      //Write Data to Data Memory
        InsMemory.put(address, instruction);
        address++;
    }

    public String readIM(int pc){       //Read Data from Instruction Memory
        return InsMemory.get(pc);
    }
}