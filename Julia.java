import java.awt.Color;

public final class Julia extends Fractal {
    private Complex juliaConst;
    public int iterations;

    public Julia(){
        juliaConst = new Complex( -0.4, 0.6 );
        this.iterations = 1000;
        plotFractal();
        plot();
    }
    
    public Julia(double x, double y ){
        juliaConst = new Complex( x, y );
        this.iterations = 1000;
        plotFractal();
        plot();
    }

    public void plot(){
        
        boolean divergentState;
        double scale = (double) 1.0/400.0; //common scale for x and y
        double x,y;
        Complex znew;
          
        for (int j=0; j<800; j++){      //impliment x scale
            y = 1 - scale*j;
            
            for(int i=0; i<800; i++){      //impliment y scale
                divergentState = false;
                x = -1 + scale*i;
                Complex z = new Complex(x,y);
                
                for (int s=0; s < iterations; s++){  //count of number of iteration
                    znew = Complex.square(z);
                    z = Complex.addition( juliaConst , znew );
                                   
                    if(Complex.absolute(z)>2){
                        picture.setRGB(i,j,Color.HSBtoRGB(s/256f,1,s/(s+8f)));
                        repaint();
                        divergentState = true;
                        break;
                    }
                }
                
                if( divergentState == false ){
                    picture.setRGB(i,j,Color.black.getRGB());   
                    repaint();
                }
            }
        }
    }

}
