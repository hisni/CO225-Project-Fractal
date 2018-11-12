import java.awt.Color;

public final class Mandelbrot extends Fractal implements Runnable {

    private double realMin, realMax, complexMin, complexMax;
    private int iterations, heightStart, heightEnd;

    public Mandelbrot( int threadNo, int NumOfThreads ) {       //Constructor for 2 Arguments
        this.realMin = -1;                      //Default values
        this.realMax = 1;
        this.complexMin = -1;
        this.complexMax = 1;
        this.iterations = 1000;
        this.heightStart = (PANEL_HEIGHT/NumOfThreads)*(threadNo-1);        //Calculating points range to process in a single Thread
        this.heightEnd = (PANEL_HEIGHT/NumOfThreads)*threadNo;
    }

    public Mandelbrot( double x1, double x2, double y1, double y2,int threadNo, int NumOfThreads ) {    //Constructor for 6 Arguments
        this.realMin = x1;                      //Given range and Default value for number of iterations 
        this.realMax = x2;
        this.complexMin = y1;
        this.complexMax = y2;
        this.iterations = 1000;
        this.heightStart = (PANEL_HEIGHT/NumOfThreads)*(threadNo-1);
        this.heightEnd = (PANEL_HEIGHT/NumOfThreads)*threadNo;
    }

    public Mandelbrot(double x1, double x2, double y1, double y2, int numIter,int threadNo, int NumOfThreads ) {    //Constructor for 7 Arguments
        this.realMin = x1;                      //Given Range and number of iterations
        this.realMax = x2;
        this.complexMin = y1;
        this.complexMax = y2;
        this.iterations = numIter;
        this.heightStart = (PANEL_HEIGHT/NumOfThreads)*(threadNo-1);
        this.heightEnd = (PANEL_HEIGHT/NumOfThreads)*threadNo;
    }

    public void run(){
        double xscale = Math.abs(realMin-realMax)/(double)PANEL_WIDTH;          //Unit real scale
        double yscale = Math.abs(complexMin-complexMax)/(double)PANEL_HEIGHT;   //Unit complex scale
    
        boolean divergentState;
        double real,complex;         //Cordinates
        Complex znew;
    
        for (int j=heightStart; j<heightEnd; j++){      //Looping through Vertical axis of panel
            complex = complexMax - j*yscale;            //Complex part in complex plane
        
            for (int i=0; i<PANEL_WIDTH; i++){          //Looping through Horizontal  axis of panel
                real = realMin + i*xscale;              //real part in complex plane
                Complex z = new Complex(0,0);           //Initial complex point
                Complex c = new Complex(real,complex);  //Maped complex point of Panel point
                divergentState = false;

                for (int s=0; s<iterations; s++){       //Count number of iteration
                    znew = Complex.square(z);           //Square of Zn
                    z = Complex.addition(c,znew);       // Zn^2 + c
                            
                    if(Complex.absolute(z)>4){          //Check for mandelbrot number
                        picture.setRGB(i,j,Color.HSBtoRGB(s/256f,1,s/(s+8f)));  //Not in mandelbrot
                        repaint();
                        divergentState = true;
                        break;
                    }
                }
        
                if(divergentState == false){                    //In mandelbrot set
                    picture.setRGB(i,j,Color.black.getRGB());
                    repaint();
                }
            }
        }
    }
}
