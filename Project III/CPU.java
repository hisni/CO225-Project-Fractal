public class CPU{
    public InstructionMemory IM;
    public DataMemory DM;
    public RegisterFile RegFile;
    public ALU alu;
    public ControlUnit CU;

    public CPU(){
        IM = new InstructionMemory();       //Instruction memory
        DM = new DataMemory();              //Data memory
        RegFile = new RegisterFile();       //Register File
        alu = new ALU();                    //ALU
        CU = new ControlUnit( IM, DM, RegFile, alu );   //Control Unit
    }

    public void Start(){    //Start the program by Fetching Instructions
        CU.startFetching();   
    }
}