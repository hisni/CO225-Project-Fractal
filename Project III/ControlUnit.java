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
            
            decodeInst( Instruction );
            updatePC();
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
        
        int opcode = BinToDec(Arrays.copyOfRange(inst,0, 6));

        if (opcode == 0) {
            System.out.println("R-Type");
            executeRType( inst );
        } else if (opcode == 8 || opcode == 12 || opcode == 4|| opcode == 7|| opcode == 35|| opcode == 43) {
            System.out.println("I-Type");
            executeIType( inst );
        }else if (opcode == 2 || opcode == 3 || opcode == 16) {
            System.out.println("J-Type");
            executeJType( inst );
        }else{
            System.out.println("Invalid Instruction");

        }
        
    }

    private void executeRType( int[] instruction ){
        int rs = BinToDec( Arrays.copyOfRange(instruction, 6, 11) );
        int rt = BinToDec( Arrays.copyOfRange(instruction, 11, 16) );
        int rd = BinToDec( Arrays.copyOfRange(instruction, 16, 21) );
        int shamt = BinToDec( Arrays.copyOfRange(instruction, 21, 26) );
        int func = BinToDec( Arrays.copyOfRange(instruction, 26, 32) );

        int rsData = RegisterFile.readReg( rs );
        int rtData = RegisterFile.readReg( rt );
        int select = 0;

        switch ( func ) {
            case 32:
                select = 0;
                break;
            case 34:
                select = 1;
                break;
            case 24:
                select = 2;
                break;
            case 26:
                select = 3;
                break;
            case 36:
                select = 4;
                break;
            case 37:
                select = 5;
                break;
            case 39:
                select = 6;
                break;
            case 38:
                select = 7;
                break;
            case 2:
                select = 8;
                break;
            case 0:
                select = 9;
                break;
            case 42:
                select = 10;
                break;
            case 12:
                select = 11;
                break;
            default:
                System.out.println("Incorrect R-Type Instruction");
        }

        int rdData = ALU.ALUoperation( rsData, rtData, select );
        RegisterFile.writeReg( rd, rdData );
    }

    private void executeIType( int[] instruction ){
        int opCode = BinToDec( Arrays.copyOfRange(instruction, 0, 6) );
        int rs = BinToDec( Arrays.copyOfRange(instruction, 6, 11) );
        int rt = BinToDec( Arrays.copyOfRange(instruction, 11, 16) );
        int imm = BinToDec( Arrays.copyOfRange(instruction, 16, 32) );

        int rsData = RegisterFile.readReg( rs );
        int rtData = 0;
        int address = 0;

        switch (opCode) {
            case 8:
                rtData = ALU.ALUoperation( rsData,  imm, 0 );
                RegisterFile.writeReg( rt, rtData );
                break;
            case 12:
                rtData = ALU.ALUoperation( rsData,  imm, 1 );
                RegisterFile.writeReg( rt, rtData );
                break;
            case 4:
                rtData = RegisterFile.readReg( rt );
                if ( ALU.ALUoperation( rsData, rtData, 12 ) == 1 ) {
                    updatePC( imm-1 );
                }
                break;
            case 7:
                rtData = RegisterFile.readReg( rt );
                if (ALU.ALUoperation( rsData, rtData, 13 ) == 1) {
                    updatePC( imm-1 );
                }
                break;
            case 35:
                address = ALU.ALUoperation( rsData, imm, 0 );
                rtData = DataMemory.readDM( address );
                RegisterFile.writeReg( rt, rtData );
                break;
            case 43:
                rtData = RegisterFile.readReg( rt );
                address = ALU.ALUoperation( rsData, imm, 0 );
                DataMemory.writeDM( address, rtData );
                break;
            default:
                System.out.println("Incorrect I-Type Instruction");
        }
    }

    private void executeJType( int[] instruction ){
        int opCode = BinToDec( Arrays.copyOfRange(instruction, 0, 6) );
        int targetAddr = BinToDec( Arrays.copyOfRange(instruction, 6, 32) );

        switch (opCode) {
            case 2:
                updatePC( targetAddr-1 );
                break;
            case 3:
                RegisterFile.writeReg( 31, ProgCounter+1 );
                updatePC( targetAddr - 1 );
                break;
            case 16:
                int address = RegisterFile.readReg( targetAddr );
                updatePC( address - 1 );
                break;
            default:
                System.out.println("Incorrect J-Type Instruction");
        }
    }

    public static int BinToDec(int[] binary) {
        int decimal = 0;
        for (int i = binary.length - 1; i >= 0; --i) {
            decimal += binary[i] * Math.pow(2, binary.length - 1 - i);
        }
        return decimal;
    }

    private void updatePC( int n ) {
        ProgCounter = n;
    }

    private void updatePC() {
        ProgCounter++;
    }
}