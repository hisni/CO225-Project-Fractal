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
        return Math.sqrt( Math.pow(z.getReal(), 2) + Math.pow(z.getImag(), 2) );
    }
}

