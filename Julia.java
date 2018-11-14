public final class Julia extends JPExtend implements Runnable {
    private Complex juliaConst;
    private int iterations, heightStart, heightEnd;

    public Julia(double real, double complex, int threadNo,  int NumOfThreads ){ //Constructor for 4 Arguments
        juliaConst = new Complex( real, complex );                               //Given Jullia Constant
        this.iterations = 1000;
        this.heightStart = (PANEL_HEIGHT/NumOfThreads)*(threadNo-1);    //Calculating points range to process in a single Thread
        this.heightEnd = (PANEL_HEIGHT/NumOfThreads)*threadNo;
    }

    public void run(){
        double real,complex;
        double unitScale = 2.0/PANEL_HEIGHT;            //Common Unit Scale for real and complex
        Complex znew;
        boolean divergentState;
        
        for (int j=heightStart; j<heightEnd; j++){      //Looping through Vertical axis of panel
            for(int i=0; i<PANEL_WIDTH; i++){           //Looping through Horizontal  axis of panel
                
                real = -1 + unitScale*i;                    //Real part in complex plane
                complex = 1 - unitScale*j;                  //Complex part in complex plane
                Complex z = new Complex(real,complex);
                divergentState = false;

                for (int s=0; s < iterations; s++){     //Count of number of iteration
                    znew = Complex.square(z);           //Square of Zn
                    z = Complex.addition( juliaConst , znew );  // Zn^2 + JuliaConstant
                                   
                    if(Complex.absolute(z)>4){          //Check for julia set number
                        plotNotInSet( i, j, s );        //Not in Julia set
                        divergentState = true;
                        break;
                    }
                }
                
                if( divergentState == false ){          //In Julia set
                    plotInSet( i, j );
                }
            }
        }
    }

}
