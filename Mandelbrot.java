import java.awt.Color;

public final class Mandelbrot extends Fractal {

    private double realMin, realMax, complexMin, complexMax;
    private int iterations;

    public Mandelbrot() {
        this.realMin = -1;
        this.realMax = 1;
        this.complexMin = -1;
        this.complexMax = 1;
        this.iterations = 1000;
        plot();
        plotFractal();
    }

    public Mandelbrot(double x1, double x2, double y1, double y2) {
        this.realMin = x1;
        this.realMax = x2;
        this.complexMin = y1;
        this.complexMax = y2;
        this.iterations = 1000;
        plot();
        plotFractal();

    }

    public Mandelbrot(double x1, double x2, double y1, double y2, int numIter) {
        this.realMin = x1;
        this.realMax = x2;
        this.complexMin = y1;
        this.complexMax = y2;
        this.iterations = numIter;
        plot();
        plotFractal();

    }

    public void plot(){
        double xscale = Math.abs(realMin-realMax)/800.0;  // Unit x scale
        double yscale = Math.abs(complexMin-complexMax)/800.0;  // Unit y scale
    
        boolean divergentState;
        double x,y;
        Complex znew;
    
        for (int j=0; j<800; j++){   //implement y scale
            y = complexMax - j*yscale;
       
             for (int i=0; i<800; i++){     //implement x scale
                divergentState = false;   
                Complex c = new Complex(0,0); 
                x = realMin + i*xscale;
                Complex z = new Complex(x,y);
          
                for (int s=0; s<iterations; s++){  //Count number of iteration
                    znew = Complex.square(c);
                    c = Complex.addition(z,znew);
                    
            
                    if(Complex.absolute(c)>2){  // check for mandelbrot number
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
