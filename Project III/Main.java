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
        if( args.length != 1 ){       //If file name not given
            System.out.println("File Name not given");
            System.exit(-1);
        }else {
            fileName = args[0];
            try {
                CPU cpu = new CPU();        //Create a CPU object
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    if( !line.startsWith("//") && line.length()>0 ){    //Ignore Comments and Empty lines
                        cpu.IM.writeIM(line);       //Read by line and Store in instruction memory
                    }
                }
                reader.close();
                cpu.Start();        //Start to fetching
    
            } catch (IOException e) {       //Exceptions handling
                System.out.println("Program Error");
                System.exit(-1);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Program Error");
                System.exit(-1);
            }
        }
    }
}
