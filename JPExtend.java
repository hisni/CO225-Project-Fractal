import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.awt.Color;
import java.awt.image.BufferedImage;

public class JPExtend extends JPanel{
    
    public static int PANEL_WIDTH = 800;          //Fixed Panel Width
    public static int PANEL_HEIGHT = 800;         //Fixed Panel Height
    protected static BufferedImage picture = new BufferedImage(PANEL_WIDTH,PANEL_HEIGHT,BufferedImage.TYPE_INT_RGB);
    
    protected void plotFractal( String fractalSet ){
        JFrame canvas = new JFrame( fractalSet );
        canvas.setContentPane(this);
        canvas.setPreferredSize( new Dimension( PANEL_WIDTH , PANEL_HEIGHT ) );
        canvas.setSize( PANEL_WIDTH, PANEL_HEIGHT );
        canvas.pack();
        canvas.setVisible(true);
        canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    @Override
    protected void paintComponent(Graphics r){
        r.drawImage(picture,0,0,this); 
    }

    protected void plotInSet( int x, int y ){                  //RGB setting for points in fractal set
        picture.setRGB(x,y,Color.black.getRGB());
        repaint();
    }

    protected void plotNotInSet( int x, int y, int ITR ){      //RGB setting for points not in fractal set
        picture.setRGB(x,y,Color.HSBtoRGB(ITR/256f,1,ITR/(ITR+16f)));
        repaint();
    }

}