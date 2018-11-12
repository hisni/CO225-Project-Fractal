import java.awt.Color;

public final class Mandelbrot extends Fractal implements Runnable {

    private double realMin, realMax, complexMin, complexMax;
    private int iterations, heightStart, heightEnd;

    public Mandelbrot( int threadNo, int NumOfThreads ) {
        this.realMin = -1;
        this.realMax = 1;
        this.complexMin = -1;
        this.complexMax = 1;
        this.iterations = 1000;
        this.heightStart = (PANEL_HEIGHT/NumOfThreads)*(threadNo-1);
        this.heightEnd = (PANEL_HEIGHT/NumOfThreads)*threadNo;
    }

    public Mandelbrot( double x1, double x2, double y1, double y2,int threadNo, int NumOfThreads ) {
        this.realMin = x1;
        this.realMax = x2;
        this.complexMin = y1;
        this.complexMax = y2;
        this.iterations = 1000;
        this.heightStart = (PANEL_HEIGHT/NumOfThreads)*(threadNo-1);
        this.heightEnd = (PANEL_HEIGHT/NumOfThreads)*threadNo;
    }

    public Mandelbrot(double x1, double x2, double y1, double y2, int numIter,int threadNo, int NumOfThreads ) {
        this.realMin = x1;
        this.realMax = x2;
        this.complexMin = y1;
        this.complexMax = y2;
        this.iterations = numIter;
        this.heightStart = (PANEL_HEIGHT/NumOfThreads)*(threadNo-1);
        this.heightEnd = (PANEL_HEIGHT/NumOfThreads)*threadNo;
    }

    public void run(){
        double xscale = Math.abs(realMin-realMax)/(double)PANEL_WIDTH;  // Unit x scale
        double yscale = Math.abs(complexMin-complexMax)/(double)PANEL_HEIGHT;  // Unit y scale
    
        boolean divergentState;
        double x,y;
        Complex znew;
    
        for (int j=heightStart; j<heightEnd; j++){   //implement y scale
            y = complexMax - j*yscale;
        
            for (int i=0; i<PANEL_WIDTH; i++){     //implement x scale
            divergentState = false;   
            Complex c = new Complex(0,0); 
            x = realMin + i*xscale;
            Complex z = new Complex(x,y);
        
                for (int s=0; s<iterations; s++){  //Count number of iteration
                    znew = Complex.square(c);
                    c = Complex.addition(z,znew);
                            
                    if(Complex.absolute(c)>4){  // check for mandelbrot number
                        picture.setRGB(i,j,Color.HSBtoRGB(s/256f,1,s/(s+8f)));
                        repaint();
                        divergentState = true;
                        break;
                    }
                }
        
                if(divergentState == false){                    //in mandelbrot
                    picture.setRGB(i,j,Color.black.getRGB());
                    repaint();
                }
            }
        }
    }
}
