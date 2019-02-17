import java.util.HashMap;

public class DataMemory {
	private HashMap<Integer,Integer> dataMemory;	//Data memory as a Hash Map
	
	public DataMemory(){     //Constructor
        dataMemory = new HashMap<Integer, Integer>();     
    }
    
	public int readDM(int address) {		//Read Data from Data Memory
		return dataMemory.get( address );
	}
    
	public void writeDM( int address, int data ) {	//Write Data to Data Memory
		dataMemory.put( address, data );
	}
}
