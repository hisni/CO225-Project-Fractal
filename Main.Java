import java.io.*;

public class Main {
    static int NumOfThreads = 8;

    public static void main(String[] args) {
        int numOfArgs = args.length;
        Mandelbrot [] mandelbrot = new Mandelbrot[NumOfThreads];
        Julia [] julia = new Julia[NumOfThreads];
        Thread [] thread = new Thread[NumOfThreads];

        if ( numOfArgs > 0 ){
            if( args[0].toLowerCase().equals("mandelbrot") ){
                if ( numOfArgs == 6 ){
                    Double x1 = Double.parseDouble(args[1]);
                    Double x2 = Double.parseDouble(args[2]);
                    Double y1 = Double.parseDouble(args[3]);
                    Double y2 = Double.parseDouble(args[4]);
                    Integer iterations = Integer.parseInt(args[5]);

                    for( int i = 0; i < NumOfThreads; i++){
                        mandelbrot[i] = new Mandelbrot( x1, x2, y1, y2, iterations, i+1, NumOfThreads );
                        thread[i] = new Thread( mandelbrot[i] );
                        thread[i].start();
                    }
                    joinThreads( thread );
                    mandelbrot[1].plotFractal();
                    
                }
                else if ( numOfArgs == 5 ){
                    Double x1 = Double.parseDouble(args[1]);
                    Double x2 = Double.parseDouble(args[2]);
                    Double y1 = Double.parseDouble(args[3]);
                    Double y2 = Double.parseDouble(args[4]);
                    
                    for( int i = 0; i < NumOfThreads; i++){
                        mandelbrot[i] = new Mandelbrot( x1, x2, y1, y2, i+1, NumOfThreads );
                        thread[i] = new Thread( mandelbrot[i] );
                        thread[i].start();
                    }
                    joinThreads( thread );
                    mandelbrot[1].plotFractal();
                }
                else{                                       
                    for( int i = 0; i < NumOfThreads; i++){
                        mandelbrot[i] = new Mandelbrot( i+1, NumOfThreads );
                        thread[i] = new Thread( mandelbrot[i] );
                        thread[i].start();
                    }
                    joinThreads( thread );
                    mandelbrot[1].plotFractal();
                }
            }
            else if( args[0].toLowerCase().equals("julia") ) {
                if( numOfArgs == 3 ){
                    Double real = Double.parseDouble(args[1]);
                    Double complex = Double.parseDouble(args[2]);

                    for( int i = 0; i < NumOfThreads; i++){
                        julia[i] = new Julia( real, complex, i+1, NumOfThreads );
                        thread[i] = new Thread( julia[i] );
                        thread[i].start();
                    }
                    joinThreads( thread );
                    julia[1].plotFractal();
                }
                else {
                    for( int i = 0; i < NumOfThreads; i++){
                        julia[i] = new Julia( i+1, NumOfThreads );
                        thread[i] = new Thread( julia[i] );
                        thread[i].start();
                    }
                    joinThreads( thread );
                    julia[1].plotFractal();
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

    public static void joinThreads(Thread [] thread) {
        for(int i = 0; i <NumOfThreads ; i++){
            try {
                thread[i].join();
            } catch (InterruptedException e) {
                System.out.println("Thread error");
            }
        }        
    }
}