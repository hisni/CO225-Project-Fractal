import java.util.*;

public class ControlUnit{
    public static int ProgCounter;
    private String Instruction;

    public ControlUnit(){
        ProgCounter = 0;
        RunProg();
    }

    private void RunProg(){

        for ( Instruction = InstructionMemory.readInstruction(ProgCounter);
            Instruction != null; Instruction = InstructionMemory.readInstruction(ProgCounter) ) {
            
            //System.out.println(Instruction);
            decodeInst( Instruction );
            ProgCounter++;
        }
    }

    private void decodeInst( String ins ){
        
        int[] inst = new int[32];

    	for( int i=0; i<ins.length(); i++ ){
    		if ( ins.charAt(i)=='0' ){
    			inst[i] = 0;
			}else{
				inst[i] = 1;
			}
        }
        
        int opCodeDec = binaryToDecimal(Arrays.copyOfRange(inst,0, 6));

        if (opCodeDec == 0) {
            System.out.println("R-Type");

        } else if (opCodeDec == 2 || opCodeDec == 3 || opCodeDec == 16) {
            System.out.println("J-Type");
            
        } else if (opCodeDec == 8 || opCodeDec == 12 || opCodeDec == 4|| opCodeDec == 7|| opCodeDec == 35|| opCodeDec == 43) {
            System.out.println("I-Type");
            
        }else{
            System.out.println("Unsupported");
        }
        
    }

    public static int binaryToDecimal(int[] binary) {
        int decimal = 0;
        for (int i = binary.length - 1; i >= 0; --i) {
            decimal += binary[i] * Math.pow(2, binary.length - 1 - i);
        }
        return decimal;
    }

}