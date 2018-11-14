import java.io.*;

public class Fractal {
    static int NumOfThreads = 4;        //Number of Threads to run

    public static void main(String[] args) {
        int numOfArgs = args.length;                                //Number of Arguments
        JPExtend fractal = new JPExtend();
        Mandelbrot [] mandelbrot = new Mandelbrot[NumOfThreads];    //Runnable Object Mandelbrot Array
        Julia [] julia = new Julia[NumOfThreads];                   //Runnable Object Julia Array
        Thread [] thread = new Thread[NumOfThreads];                //Array of Threads

        if ( numOfArgs > 0 ){
            if( args[0].toLowerCase().equals("mandelbrot") ){       //If Input Mandelbrot set
                
                if ( numOfArgs == 6 ){                              //Mandelbrot with 5 Arguments
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
                    joinThreads( thread );                  //Check whether Threads are finished Process
                    fractal.plotFractal( "Mandelbrot Set");         //Draw Picture after all the threads are finished 
                }
                else if ( numOfArgs == 5 ){                         //Mandelbrot with 4 Arguments
                    Double x1 = Double.parseDouble(args[1]);
                    Double x2 = Double.parseDouble(args[2]);
                    Double y1 = Double.parseDouble(args[3]);
                    Double y2 = Double.parseDouble(args[4]);
                    
                    for( int i = 0; i < NumOfThreads; i++){
                        mandelbrot[i] = new Mandelbrot( x1, x2, y1, y2, i+1, NumOfThreads );
                        thread[i] = new Thread( mandelbrot[i] );
                        thread[i].start();
                    }
                    joinThreads( thread );                  //Check whether Threads are finished Process 
                    fractal.plotFractal( "Mandelbrot Set");         //Draw Picture after all the threads are finished
                }
                else{                                               //Mandelbrot with 0 Arguments 
                    for( int i = 0; i < NumOfThreads; i++){
                        mandelbrot[i] = new Mandelbrot( i+1, NumOfThreads );
                        thread[i] = new Thread( mandelbrot[i] );
                        thread[i].start();
                    }
                    joinThreads( thread );
                    fractal.plotFractal( "Mandelbrot Set");         //Draw Picture after all the threads are finished
                    
                }
            }
            else if( args[0].toLowerCase().equals("julia") ) {      //If Input Mandelbrot set
                
                if( numOfArgs == 3 ){                               //Julia with 2 Arguments
                    Double real = Double.parseDouble(args[1]);
                    Double complex = Double.parseDouble(args[2]);

                    for( int i = 0; i < NumOfThreads; i++){
                        julia[i] = new Julia( real, complex, i+1, NumOfThreads );
                        thread[i] = new Thread( julia[i] );
                        thread[i].start();
                    }
                    joinThreads( thread );                      //Check whether Threads are finished Process
                    fractal.plotFractal( "Julia Set");          //Draw Picture after all the threads are finished
                }
                else {                                          //Julia with 0 Arguments
                    for( int i = 0; i < NumOfThreads; i++){
                        julia[i] = new Julia( i+1, NumOfThreads );
                        thread[i] = new Thread( julia[i] );
                        thread[i].start();
                    }
                    joinThreads( thread );                      //Check whether Threads are finished Process
                    fractal.plotFractal( "Mandelbrot Set");     //Draw Picture after all the threads are finished
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