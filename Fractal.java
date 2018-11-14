/*
    Authors:
    Hisni Mohammed M.H.  (E/15/131)
    Suhail S. (E/15/348)
    Date: 14/11/2018
    Group Project | Fractals
*/
import java.io.*;

public class Fractal {
    private static int NumOfThreads = 4;        //Number of Threads to run
    private static double realMin, realMax, complexMin, complexMax, realJulia, complexJulia ;
    private static int iterations;

    public static void main(String[] args) {
        int numOfArgs = args.length;                                //Number of Arguments
        JPExtend fractal = new JPExtend();
        Mandelbrot [] mandelbrot = new Mandelbrot[NumOfThreads];    //Runnable Object Mandelbrot Array
        Julia [] julia = new Julia[NumOfThreads];                   //Runnable Object Julia Array
        Thread [] thread = new Thread[NumOfThreads];                //Array of Threads
        
        if( numOfArgs>0 && args[0].toLowerCase().equals("mandelbrot") ){       //If Input Mandelbrot set
            if ( numOfArgs == 6 ){                              //Mandelbrot with 5 Arguments
                realMin = Double.parseDouble(args[1]);
                realMax = Double.parseDouble(args[2]);
                complexMin = Double.parseDouble(args[3]);
                complexMax = Double.parseDouble(args[4]);
                iterations = Integer.parseInt(args[5]);
            }
            else if ( numOfArgs == 5 ){                         //Mandelbrot with 4 Arguments -> Set default values for itrations
                realMin = Double.parseDouble(args[1]);
                realMax = Double.parseDouble(args[2]);
                complexMin = Double.parseDouble(args[3]);
                complexMax = Double.parseDouble(args[4]);
                iterations = 1000;
            }
            else{                                               //Mandelbrot with 0 Arguments -> Set default values
                realMin = -1;
                realMax = 1;
                complexMin = -1;
                complexMax = 1;
                iterations = 1000;
            }

            for( int i = 0; i < NumOfThreads; i++){     //Running creating Threads and running
                mandelbrot[i] = new Mandelbrot( realMin, realMax, complexMin, complexMax, iterations, i+1, NumOfThreads );
                thread[i] = new Thread( mandelbrot[i] );
                thread[i].start();
            }
            joinThreads( thread );                      //Check whether Threads are finished Process
            fractal.plotFractal( "Mandelbrot Set");     //Draw Picture after all the threads are finished 
        }
        else if( numOfArgs>0 && args[0].toLowerCase().equals("julia") ) {      //If Input Julia set
            
            if( numOfArgs == 3 ){                               //Julia with 2 Arguments
                realJulia = Double.parseDouble(args[1]);
                complexJulia = Double.parseDouble(args[2]);
            }
            else {                                          //Julia with 0 Arguments
                realJulia = -0.4;
                complexJulia = 0.6;
            }

            for( int i = 0; i < NumOfThreads; i++){         //Running creating Threads and running
                julia[i] = new Julia( realJulia, complexJulia, i+1, NumOfThreads );
                thread[i] = new Thread( julia[i] );
                thread[i].start();
            }
            joinThreads( thread );                      //Check whether Threads are finished Process
            fractal.plotFractal( "Julia Set");          //Draw Picture after all the threads are finished
        }
        else{
            System.out.println("Give a valid Fractal set name");    //If fractal set name is not given or wrong input
        }
    }

    private static void joinThreads(Thread [] thread) {      //Meathod to Join Threads after they Finish
        for(int i = 0; i <NumOfThreads ; i++){
            try {
                thread[i].join();
            } catch (InterruptedException e) {
                System.out.println("Thread error");
            }
        }        
    }
}