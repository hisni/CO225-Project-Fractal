import java.awt.Color;

public final class Julia extends Fractal implements Runnable {
    private Complex juliaConst;
    private int iterations, heightStart, heightEnd;

    public Julia( int threadNo,  int NumOfThreads ){                    //Constructor for 2 Arguments
        juliaConst = new Complex( -0.4, 0.6 );                          //Default Values
        this.iterations = 1000;
        this.heightStart = (PANEL_HEIGHT/NumOfThreads)*(threadNo-1);    //Calculating points range to process in a single Thread
        this.heightEnd = (PANEL_HEIGHT/NumOfThreads)*threadNo;
    }
    
    public Julia(double real, double complex, int threadNo,  int NumOfThreads ){ //Constructor for 4 Arguments
        juliaConst = new Complex( real, complex );                               //Given Jullia Constant
        this.iterations = 1000;
        this.heightStart = (PANEL_HEIGHT/NumOfThreads)*(threadNo-1);    //Calculating points range to process in a single Thread
        this.heightEnd = (PANEL_HEIGHT/NumOfThreads)*threadNo;
    }

    public void run(){
        double real,complex;
        double scale = 2.0/PANEL_HEIGHT;            //Common Unit real scale for real and complex
        Complex znew;
        boolean divergentState;
        
        for (int j=heightStart; j<heightEnd; j++){      //Looping through Vertical axis of panel
            complex = 1 - scale*j;                      //Complex part in complex plane
            
            for(int i=0; i<PANEL_WIDTH; i++){           //Looping through Horizontal  axis of panel
                real = -1 + scale*i;                    //Real part in complex plane
                Complex z = new Complex(real,complex);
                divergentState = false;

                for (int s=0; s < iterations; s++){     //Count of number of iteration
                    znew = Complex.square(z);           //Square of Zn
                    z = Complex.addition( juliaConst , znew );  // Zn^2 + JuliaConstant
                                   
                    if(Complex.absolute(z)>4){          //Check for julia set number
                        picture.setRGB(i,j,Color.HSBtoRGB(s/256f,1,s/(s+8f)));      //Not in Julia set
                        repaint();
                        divergentState = true;
                        break;
                    }
                }
                
                if( divergentState == false ){          //In Julia set
                    picture.setRGB(i,j,Color.black.getRGB());   
                    repaint();
                }
            }
        }
    }

}
