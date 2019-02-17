
public class RegisterFile {
    public static int[] regSet = new int[32];
    
    public static int readReg( int address ) {
		return regSet[address];
	}
    
    public static void writeReg( int address, int data ) {
		regSet[address] = data;
	}

}
