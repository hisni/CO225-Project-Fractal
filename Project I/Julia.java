/*  
    Group No : 10
    Date: 14/11/2018
    Group Project | Fractals
*/
public final class Julia extends JPExtend implements Runnable {
    private Complex juliaConst;
    private int iterations, heightStart, heightEnd;

    public Julia(double real, double complex, int threadNo,  int NumOfThreads ){ //Constructor for 4 Arguments
        juliaConst = new Complex( real, complex );              //Initial Conditions
        this.iterations = 1000;
        this.heightStart = (PANEL_HEIGHT/NumOfThreads)*(threadNo-1);    //Calculating points range to process in a single Thread
        this.heightEnd = (PANEL_HEIGHT/NumOfThreads)*threadNo;
    }

    public void run(){
        double real;                //Cordinates in complex plane
        double complex;
        boolean divergeState;
        Complex znew;
        double unitScale = 2.0/PANEL_HEIGHT;            //Common Unit Scale for real and complex
        
        for (int j=heightStart; j<heightEnd; j++){      //Looping through Vertical axis of panel
            for(int i=0; i<PANEL_WIDTH; i++){           //Looping through Horizontal  axis of panel
                
                real = -1 + unitScale*i;                //Real part in complex plane
                complex = 1 - unitScale*j;              //Complex part in complex plane
                Complex z = new Complex(real,complex);
                divergeState = false;

                for (int s=0; s < iterations; s++){     //Count of number of iteration
                    znew = Complex.square(z);           //Square of Zn
                    z = Complex.addition( juliaConst , znew );  // Zn^2 + JuliaConstant
                                   
                    if(Complex.absolute(z)>4){          //Check for julia set number
                        plotNotInSet( i, j, s );        //Not in Julia set
                        divergeState = true;
                        break;
                    }
                }
                
                if( divergeState == false ){          //In Julia set
                    plotInSet( i, j );
                }
            }
        }
    }
}
