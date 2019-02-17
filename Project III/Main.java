/*
    Authors:
        Hisni Mohammed M.H.  (E/15/131)
        Suhail S. (E/15/348)
    Group No : 9
    Project III | CPU Simulator
*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        
        String fileName;
        if(args.length == 0){
            fileName = "prog.s" ;
        }else {
            fileName = args[0];
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            InstructionMemory insMem = new InstructionMemory();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if(!line.startsWith("//") && line.length()>0){
                    insMem.addInstruction(line);
                }
            }
            reader.close();
            ControlUnit CU = new ControlUnit();

        } catch (IOException e) {
            System.out.println("Program Error");
            System.exit(-1);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Program Error");
            System.exit(-1);
        }
    }
}
