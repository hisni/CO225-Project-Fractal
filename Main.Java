import java.io.*;

public class Main {
    public static void main(String[] args) {
        int numOfArgs = args.length;
        if ( numOfArgs > 0 ){
            if( args[0].toLowerCase().equals("mandelbrot") ){
                if ( numOfArgs == 6 ){
                    Double x1 = Double.parseDouble(args[1]);
                    Double x2 = Double.parseDouble(args[2]);
                    Double y1 = Double.parseDouble(args[3]);
                    Double y2 = Double.parseDouble(args[4]);
                    Integer iterations = Integer.parseInt(args[5]);
                    Mandelbrot mandelbrot = new Mandelbrot(x1,x2,y1,y2,iterations);
                }
                else if ( numOfArgs == 5 ){
                    Double x1 = Double.parseDouble(args[1]);
                    Double x2 = Double.parseDouble(args[2]);
                    Double y1 = Double.parseDouble(args[3]);
                    Double y2 = Double.parseDouble(args[4]);
                    Mandelbrot mandelbrot = new Mandelbrot(x1,x2,y1,y2);
                }
                else{
                    Mandelbrot mandelbrot = new Mandelbrot();
                }
            }
            else if( args[0].toLowerCase().equals("julia") ) {
                if( numOfArgs == 3 ){
                    Double real = Double.parseDouble(args[1]);
                    Double complex = Double.parseDouble(args[2]);
                    Julia julia = new Julia(real, complex);
                }
                else {
                    Julia julia = new Julia();
                }
            }
            else{
                System.out.println("Input a valid Fractal set");
            }
        }
        else{
                System.out.println("Fractal set Name not given");
        }
    }
}

        /*double x=4;
        double y=3;
        double a=1;
        double b=-1;
        

        Complex c1 = new Complex( x, y );
        Complex c2 = new Complex( a, b );
        double r1 = Complex.absolute( c1 );
        double r2 = Complex.absolute( c2 );
        Complex c3 = Complex.addition( c1, c2 );
        Complex c4 = Complex.square( c1 );
        Complex c5 = Complex.square( c2 );

        System.out.println(r1);
        System.out.println(r1);
        System.out.println("Addition " + c3.getReal() + " " + c3.getImag() );
        System.out.println("sq c1 " + c4.getReal() + " " + c4.getImag() );
        System.out.println("sq c2 " + c5.getReal() + " " + c5.getImag() );
        */