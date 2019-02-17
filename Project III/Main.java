import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String fileName;
        if(args.length == 0){
            fileName = "instructions.s" ;
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
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
