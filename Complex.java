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

    public static double abs(CNum z){  //absolute of complex number
        return Math.sqrt(z.setReal()*z.setReal() + z.setImage()*z.setImage());
    }
}

