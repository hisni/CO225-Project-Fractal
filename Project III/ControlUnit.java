import java.util.*;

public class ControlUnit{
    private int ProgCounter;        //Program Counter
    private String Instruction;     //Fetched Instruction from IM
    private InstructionMemory IM;
    private DataMemory DM;
    RegisterFile RegFile;
    private ALU alu;

    public ControlUnit( InstructionMemory im, DataMemory dm, RegisterFile regfile, ALU alu ){
        ProgCounter = 0;
        IM = im;
        DM = dm;
        RegFile = regfile;
        this.alu = alu;
    }

    public void startFetching(){    //Method to fetch insttruction from Instruction Memory
        for ( Instruction = IM.readIM(ProgCounter);
            Instruction != null; Instruction = IM.readIM(ProgCounter) ) {
            
            decodeInst( Instruction );  //Start Decoding Instructions
            updatePC();                 //Update Program counter to fetch next instruction
            System.out.println();
        }
    }

    private void decodeInst( String ins ){  //Methode to decode instructions
        int[] inst = new int[32];
    	for( int i=0; i<ins.length(); i++ ){    //Convert String Instruction into Integer array
    		if ( ins.charAt(i)=='0' ){
    			inst[i] = 0;
			}else{
				inst[i] = 1;
			}
        }

        int opcode = BinToDec(Arrays.copyOfRange(inst,0, 6));  //Extract Opcode from Instruction
        System.out.println(opcode);
        if (opcode == 0) {      //If R-Type Instruction
            System.out.println("R-Type");
            executeRType( inst );
        } else if (opcode == 8 || opcode == 12 || opcode == 4|| opcode == 7|| opcode == 35|| opcode == 43) {    //If I-Type Instruction
            System.out.println("I-Type");
            executeIType( inst );
        }else if (opcode == 2 || opcode == 3 || opcode == 16) {     //If J-Type Instruction
            System.out.println("J-Type");
            executeJType( inst );
        }else{  
            System.out.println("Invalid Instruction");
        }
    }

    private void executeRType( int[] instruction ){     //Execute R-Type Instruction
        int rs = BinToDec( Arrays.copyOfRange(instruction, 6, 11) );    //Extract rs from Instruction
        int rt = BinToDec( Arrays.copyOfRange(instruction, 11, 16) );   //Extract rt from Instruction
        int rd = BinToDec( Arrays.copyOfRange(instruction, 16, 21) );   //Extract rd from Instruction
        int shamt = BinToDec( Arrays.copyOfRange(instruction, 21, 26) );//Extract shamt from Instruction
        int func = BinToDec( Arrays.copyOfRange(instruction, 26, 32) ); //Extract func from Instruction

        int rsData = RegFile.readReg( rs );     //Read rs Data from register file 
        int rtData = RegFile.readReg( rt );     //Read rt Data from register file
        int select = ALUcontrol( func ) ;       //Set ALU control
        
        if( (select < 12) && (select >= 0) ){   //Check for valid ALU control
            int rdData = alu.ALUoperation( rsData, rtData, select );    //Use ALU to perform operation
            RegFile.writeReg( rd, rdData );     //Write rd value in register file
        }
        else{
            System.out.println("Incorrect use of R-Type Instruction");
        }
    }

    private void executeIType( int[] instruction ){     //Execute I-Type Instruction
        int opcode = BinToDec( Arrays.copyOfRange( instruction, 0, 6 ) );   //Extract opcode from Instruction
        int rs = BinToDec( Arrays.copyOfRange( instruction, 6, 11 ) );      //Extract rs from Instruction
        int rt = BinToDec( Arrays.copyOfRange( instruction, 11, 16 ) );     //Extract rt from Instruction
        int imm = BinToDec( Arrays.copyOfRange( instruction, 16, 32 ) );    //Extract imm from Instruction

        int rsData = RegFile.readReg( rs );     //Read rs Data from register file
        int rtData = 0;
        int address = 0;

        switch ( opcode ) {     //Decode & Execute I-Type instruction
            case 8:     //Add immediate
                rtData = alu.ALUoperation( rsData,  imm, 0 );   //ALU addition
                RegFile.writeReg( rt, rtData );     //Write to register
                break;
            case 12:    //Sub immediate
                rtData = alu.ALUoperation( rsData,  imm, 1 );   //ALU subtraction
                RegFile.writeReg( rt, rtData );     //Write to register
                break;
            case 4:     //Branch equal
                rtData = RegFile.readReg( rt );
                if ( alu.ALUoperation( rsData, rtData, 12 ) == 1 ) {
                    updatePC( imm-1 );      //If equal update PC with target Address
                }
                break;
            case 7:     //Branch greater than
                rtData = RegFile.readReg( rt );
                if (alu.ALUoperation( rsData, rtData, 13 ) == 1) {
                    updatePC( imm-1 );      //If greater update PC with target Address
                }
                break;
            case 35:    //Load word
                address = alu.ALUoperation( rsData, imm, 0 );   //Calculate DM Address
                rtData = DM.readDM( address );      //Read Data Memory
                RegFile.writeReg( rt, rtData );     //Write to register
                break;
            case 43:    //Store word
                rtData = RegFile.readReg( rt );     //Read to register
                address = alu.ALUoperation( rsData, imm, 0 );   //Calculate DM Address
                DM.writeDM( address, rtData );      //Write to Data Memory
                break;
            default:
                System.out.println("Incorrect use of I-Type Instruction");
        }
    }

    private void executeJType( int[] instruction ){ //Decode & Execute J-Type instruction
        int opcode = BinToDec( Arrays.copyOfRange(instruction, 0, 6) );
        int targetAddr = BinToDec( Arrays.copyOfRange(instruction, 6, 32) );

        switch (opcode) {
            case 2:     //Jump to target
                updatePC( targetAddr-1 );       //Update PC with target Address
                break;
            case 3:     //Link and jump to target
                RegFile.writeReg( 31, ProgCounter+1 );  //Store next instruction address in link register
                updatePC( targetAddr - 1 );     //Update PC with target Address
                break;
            case 16:    //Jump to address in target register
                int address = RegFile.readReg( targetAddr );    //Read target address from register
                updatePC( address - 1 );        //Update PC with target Address
                break;
            default:
                System.out.println("Incorrect use of J-Type Instruction");
        }
    }

    public static int BinToDec(int[] binary) {      //Method to convert binary array into Decimal
        int decimal = 0;
        for (int i = binary.length - 1; i >= 0; --i) {
            decimal += binary[i] * Math.pow(2, binary.length - 1 - i);
        }
        return decimal;
    }

    private void updatePC( int n ) {    //Method to update PC with given address
        ProgCounter = n;
    }

    private void updatePC() {       //Method to increment PC by 1
        ProgCounter++;
    }

    private int ALUcontrol( int func ){     //Methode to set ALU controls for R-Type instructions
        int select;
        switch ( func ) {
            case 32:        //Addition
                select = 0;
                break;
            case 34:		//Subtraction
                select = 1;
                break;
            case 24:		//Multiplication
                select = 2;
                break;
            case 26:		//Integer division
                select = 3;
                break;
            case 36:		//Bitwise AND
                select = 4;
                break;
            case 37:		//Bitwise OR
                select = 5;
                break;
            case 39:    	//Bitwise NOR
                select = 6;
                break;
            case 38:		//Bitwise XOR
                select = 7;
                break;
            case 2:         //Shift right
                select = 8;
                break;
            case 0:         //Shift left
                select = 9;
                break;
            case 42:        //Set if (Data1 < Data2)
                select = 10;
                break;
            case 12:		//Syscall
                select = 11;
                break;
            default:        //Invalid Instruction or Default value
                select = -1;
        }
        return select;
    }
}