import java.math.*;

class Complex {
    private double real, imaginary;

    Complex(double x, double y) {
        this.real = x;
        this.imaginary = y;
    }

    public double getReal() {
        return (this.real);
    }

    public double getImag() {
        return (this.imaginary);
    }

    public static double absolute(Complex z){
        return Math.sqrt( z.getReal()*z.getReal() + z.getImag()*z.getImag() );
    }

    public static  Complex addition(Complex z1, Complex z2){
        double x = z1.getReal() + z2.getReal();
        double y = z1.getImag() + z2.getImag();
        return new Complex(x,y);
    }

    public static Complex square(Complex z){
        double x =  z.getReal()*z.getReal() - z.getImag()*z.getImag();
        double y = 2*z.getReal()*z.getImag();
        return new Complex(x,y);
    }
}

