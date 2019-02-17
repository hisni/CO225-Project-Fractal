import java.util.HashMap;

public class DataMemory {
    private static HashMap<Integer,Integer> dataMemory;
    
	public static int readDM(int address) {
		return dataMemory.get( address );
	}
    
	public static void writeDM( int address, int data ) {
		dataMemory.put( address, data );
	}
}
