/*
    Group Project | Auction Server
    E/15/131 | E/15/348
    Group No : 10
*/
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.*;
import java.io.*;

class Client implements Runnable, ActionListener{

    public static final int AUTH_NAME = 0;      //Initial case for get Clients' Name
    public static final int GET_SYMBOL = 1;     //Case for get the symbol
    public static final int GET_BID_PRICE = 2;  //Case for get the bid amount

    private PrintWriter out;
    private Socket connectedSocket;     //Connected socket unique to each thread
    private int currentState;           //Variable to switch between states    
    public float bidPrice;              //Variable get bid amount
    public String  clientName;          //Variable get client name
    public String symbol=null;          //Variable get symbol
    private Timer timer;                //Timer to create an action event
    private int logLength=0;            //Length of the bid History arraylist
    public boolean newBidState=false;   //Variable to track whether a new bid is placed or not

    public void actionPerformed(ActionEvent e) {
        //Check whether someone has placed a new Bid on current Symbol
        //If so notify others who are bidding on same symbol about the bid.
        if( StocksDB.stockLog.get( symbol ) != null ) {
            int newLogLength = StocksDB.stockLog.get(symbol).size();
            if ( newLogLength > 0 ){
                for( int i=logLength ; i<newLogLength ; i++ ){                      //Notify All the new Bids
                    StockLog SL = StocksDB.stockLog.get(symbol).get( i );
                    if ( newLogLength > logLength &&  clientName != SL.clientName ) {
                        logLength = newLogLength;
                        out.print( "\n\n"+SL.clientUpdate()+"\n\nYour new Bid: " );
                        out.flush();
                    }
                }
            }
        }
    }

    public Client( Socket socket) {     //Constructor
		connectedSocket = socket;
        timer = new Timer(500, this);   //Create Timer and assign current ActionListener
        timer.start();                  //Start timer
    }

    public float getStockPrice( String key ){       //Method to Retrive Current Stock Price
        return StocksDB.stockPrice(key);
    }

    public void run(){
		try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.connectedSocket.getInputStream()));   //To get input stream
            PrintWriter out = new PrintWriter(new OutputStreamWriter(this.connectedSocket.getOutputStream()));      //To give output stream
            this.out = out;
		    String line; 
            out.print("\nWELCOME TO AUCTION SERVER\n");
            out.print("(Enter 'quit' to exit)\n");
            out.print("\nEnter Client Name : ");
            out.flush();
            currentState = AUTH_NAME;

            //Read inputs from the user
            for(line = in.readLine(); line != null && !line.equalsIgnoreCase("quit"); line = in.readLine()) {
                if( line.equalsIgnoreCase("") ){        //If client enter a empty string continue
                    continue;
                }
                switch (currentState) {                 //Switch between states
                    case AUTH_NAME:                     //Intial Case to get Client Name
                        clientName = line;
                        out.print("Enter the Symbol of Bidding item : ");
                        currentState = GET_SYMBOL;
                        break;

                    case GET_SYMBOL :                   //Case to get Symbol
                        symbol = line;
                        if ( StocksDB.stocksDetails.containsKey(symbol) ){      //Check whether symbol is in StocksDB
                            if( StocksDB.stockLog.get(symbol)!=null ){
                                logLength = StocksDB.stockLog.get(symbol).size();
                            }
                            currentState = GET_BID_PRICE;                       //If valid change state to get Bid amount
                            out.print("\nBidding Item :"+symbol + "\nCurrent price is "+ getStockPrice(symbol)+"\n");
                            out.print("\nEnter your Bid: ");
                        
                        }
                        else{                           //If not valid symbol Ask for Valid symbol
                            currentState = GET_SYMBOL;
                            //out.print("Security Symbol does not exists, Enter a valid Symbol.\nEnter the Symbol of Bidding item : ");
                            out.print("-1\nEnter the Symbol of Bidding item : ");
                        }
                        break;

                    case GET_BID_PRICE:                 //Case to get bid amount
                        currentState = GET_BID_PRICE;
                        if( StocksDB.stockLog.get(symbol)!=null ){
                            logLength = StocksDB.stockLog.get(symbol).size();
                        }

                        try {                           //Check for valid Format of Bid amount 
                            bidPrice = Float.parseFloat(line);    
                        }catch (NumberFormatException e) {      //If not ask for a valid Bid
                            out.println("Invalid Format");
                            out.print("Enter your new bid: ");
                            out.flush();
                            break;
                        }
                        
                        if( bidPrice > getStockPrice( symbol) ){        //Check bidPrice is valid i.e greater than current price
                            newBidState = true;                         //If Valid update Currnt price
                            StocksDB.setPrice(symbol, bidPrice, clientName);
                            out.print("You placed a bid of " + bidPrice + " on "+ symbol + ".");
                            out.print("\nCurrent price is "+ getStockPrice(symbol));
                            out.print("\nEnter your new bid on "+ symbol +": ");
                        }
                        else{                                           //If not valid ask for new Bid higher than current price
                            out.print("Your Bid is not accepted. Bid higher than " + getStockPrice(symbol) + " on "+ symbol + "." );
                            out.print("\nEnter Your new bid on "+ symbol +": ");
                        }
                        break;
                    default:
                        out.println("Undefined state");
                        return;
                }
                out.flush();
            }
            out.close();
            in.close();
            this.connectedSocket.close();

        }catch (IOException e) { 
            System.out.println(e); 
        }catch( NumberFormatException e){
            System.out.println(e);
		}finally { 	    
            try{
                this.connectedSocket.close(); 
            }catch(Exception e){}	
        }
    }
}