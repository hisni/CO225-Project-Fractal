/*
    Authors:
    Hisni Mohammed M.H.  (E/15/131)
    Suhail S. (E/15/348)
    Group No : 10
    Date: 24/12/2018
    Group Project | Auction Server
*/
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;
import java.util.*; 

public class MainServer{
    
    public static ArrayList<Client> clientList = new ArrayList<>(); //Array list to keep track of clients connected to server
    public static final int BASE_PORT = 2000;
    public StocksDB stockDetails=null;     
    private static ServerSocket serverSoc; 
    
    public MainServer( StocksDB stock ) throws IOException { 
		serverSoc = new ServerSocket(BASE_PORT);                //Create new Server Socket
		this.stockDetails = stock;
    }
    
    public void serverLoop() throws IOException { 
		while(true) {                                           //Server Listning continuously
		    Socket socket = serverSoc.accept();
		    Client client = new Client( socket );
		    Thread clientThread = new Thread( client );         //Create new Threads for each Client
		    clientList.add( client );
		    clientThread.start(); 	    
		}
    }
    
    public static void main(String[] args) throws IOException{

        StocksDB stockItems = new StocksDB("stocks.csv");       //Create StockDB object and Store Datas in *.CSV file
        MainServer server = new MainServer(stockItems);         //Calling MainServer constructor and passing Created StockDB object

        JFrame frame = new JFrame("Current Stock Prices");      //Create new GUI
        frame.setSize(600, 600);
  		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  		frame.add(new Display(server));                         //Calling Display class Constructor
  		frame.pack();
        frame.setVisible(true);

		server.serverLoop();            //Running SereverLoop method continuously listening for lients requests

    }
}
