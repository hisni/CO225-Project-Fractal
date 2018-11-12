import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.awt.image.BufferedImage;

public class Fractal extends JPanel{
    
    public static final int PANEL_WIDTH = 800;
    public static final int PANEL_HEIGHT = 800;
    protected static BufferedImage picture = new BufferedImage(PANEL_WIDTH,PANEL_HEIGHT,BufferedImage.TYPE_INT_RGB);
    
    public void plotFractal(){
        JFrame canvas = new JFrame("Fractals");
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
}