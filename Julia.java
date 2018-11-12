import java.awt.Color;

public final class Julia extends Fractal implements Runnable {
    private Complex juliaConst;
    private int iterations, heightStart, heightEnd;

    public Julia( int threadNo,  int NumOfThreads ){
        juliaConst = new Complex( -0.4, 0.6 );
        this.iterations = 1000;
        this.heightStart = (PANEL_HEIGHT/NumOfThreads)*(threadNo-1);
        this.heightEnd = (PANEL_HEIGHT/NumOfThreads)*threadNo;
    }
    
    public Julia(double x, double y, int threadNo,  int NumOfThreads ){
        juliaConst = new Complex( x, y );
        this.iterations = 1000;
        this.heightStart = (PANEL_HEIGHT/NumOfThreads)*(threadNo-1);
        this.heightEnd = (PANEL_HEIGHT/NumOfThreads)*threadNo;
    }

    public void run(){
        
        boolean divergentState;
        double scale = 2.0/PANEL_HEIGHT; //common scale for x and y
        double x,y;
        Complex znew;
          
        for (int j=heightStart; j<heightEnd; j++){      //impliment x scale
            y = 1 - scale*j;
            
            for(int i=0; i<PANEL_WIDTH; i++){      //impliment y scale
                divergentState = false;
                x = -1 + scale*i;
                Complex z = new Complex(x,y);
                
                for (int s=0; s < iterations; s++){  //count of number of iteration
                    znew = Complex.square(z);
                    z = Complex.addition( juliaConst , znew );
                                   
                    if(Complex.absolute(z)>4){
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
