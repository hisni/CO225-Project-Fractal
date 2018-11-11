import java.awt.Color;

public class Mandelbrot extends Fractal {

    private final double x1, x2, y1, y2; // real(x) and imaginary(y) numbers
    private final int iterations;

    public Mandelbrot() { // defaut value for mandelbrot
        this.x1 = -2;
        this.x2 = 2;
        this.y1 = -2;
        this.y2 = 2;
        this.iterations = 1000;
        plot();
        plotFractal();
    }

    public Mandelbrot(double x1, double x2, double y1, double y2) { // given 4 arguments for mandelbrot
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.iterations = 1000;
        plot();
        plotFractal();

    }

    public Mandelbrot(double x1, double x2, double y1, double y2, int numIter) { // given 5 arguments for mandelbrot
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.iterations = numIter;
        plot();
        plotFractal();

    }

    public void plot(){
        double xscale = Math.abs(x1-x2)/800.0;  // Unit x scale
        double yscale = (double)Math.abs(y1-y2)/800.0;  // Unit y scale
    
        boolean divergentState;
        double x,y;
        Complex znew;
    
        for (int j=0; j<800; j++){   //implement y scale
            y = y2 - j*yscale;
       
             for (int i=0; i<800; i++){     //implement x scale
                divergentState = false;   
                Complex c = new Complex(0,0); 
                x = x1 + i*xscale;
                Complex z = new Complex(x,y);
          
                for (int s=0; s<iterations; s++){  //Count number of iteration
                    znew = Complex.square(c);
                    c = Complex.addition(z,znew);
                    
            
                    if(Complex.absolute(c)>2){  // check for mandelbrot number
                        picture.setRGB(i,j,Color.HSBtoRGB(s/256f,1,s/(s+8f)));  //not in mandelbrot
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
