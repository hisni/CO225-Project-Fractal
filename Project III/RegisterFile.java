
public class RegisterFile {
    private static int[] regSet;
    
    public RegisterFile(){      //Constructor
        regSet = new int[32];   //Set of 32 Registers (Registers as Integer Array)
    }

    public int readReg( int address ) {      //Read Data from Register
		return regSet[address];
	}
    
    public void writeReg( int address, int data ) {  //Write Data to Register
        regSet[address] = data;
        System.out.println("RegWrite at "+address+" - "+regSet[address]);
	}
}
