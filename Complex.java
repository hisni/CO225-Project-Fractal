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

    public static double absolute(Complex z){       //Method to calculate Absolute value of a complex number 
        return Math.sqrt( Math.pow(z.getReal(), 2) + Math.pow(z.getImag(), 2) );
    }

    public static  Complex addition(Complex z1, Complex z2){    //Method to add 2 complex numbers
        double x = z1.getReal() + z2.getReal();
        double y = z1.getImag() + z2.getImag();
        return new Complex(x,y);
    }

    public static Complex square(Complex z){                    //Method get the square of a complex number
        double x =  Math.pow(z.getReal(), 2) - Math.pow(z.getImag(), 2);
        double y = 2*z.getReal()*z.getImag();
        return new Complex(x,y);
    }
}

