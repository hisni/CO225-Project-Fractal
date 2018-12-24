/*
    Group Project | Auction Server
    E/15/131 | E/15/348
    Group No : 10
*/
import java.io.*;
import java.util.*;

public class StocksDB{
    public static Map<String,StockInfo> stocksDetails = new HashMap<String, StockInfo>();   //HashMap to Store Stock items and their details
    public static Map<String, ArrayList<StockLog>> stockLog = new HashMap<>();              //HashMap to Store Bidding History
    private String [] header;

    public StocksDB( String csvFile )  {

        BufferedReader csvRead=null;
        
        String stockRead="";
        String [] splitStockInfo;

        try { 
            csvRead = new BufferedReader( new FileReader( csvFile ) );      //Reading *.CSV file

            header = csvRead.readLine().split(",");         //Reading the first line which has the names of fields and identifying them
            int IndexSymbol = findIndex("Symbol");          //Find the Index of Field - Symbol
	        int IndexName = findIndex("Security Name");     //Find the Index of Field - Security Name
            int IndexPrice = findIndex("Price");            //Find the Index of Field - Price
            
            while( (stockRead = csvRead.readLine()) != null ){      //Read next line, split and adding to HashMap
                splitStockInfo = stockRead.split(","); 
                stocksDetails.put( splitStockInfo[IndexSymbol], new StockInfo(splitStockInfo[IndexName], Float.parseFloat(splitStockInfo[IndexPrice]) ) );
            }

        }catch (FileNotFoundException e) {      //Handling Exceptions
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(-1);
        } finally {
            if ( csvRead != null ) {
                try {
                    csvRead.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private int findIndex( String key ){        //Method to identify Index of a given Feild Nmae from the the First line of *.CSV file
        for(int i=0; i < header.length; i++){
            if( header[i].equalsIgnoreCase(key) ){
                return i;
            }
        }
        return -1;
    }

    public void printStock( ) {         //Method to print All the Stock items and their Details
        for( Map.Entry< String, StockInfo > entry : stocksDetails.entrySet() ){
            String key = entry.getKey();
            System.out.println( key + " " + getSecurityName(key) + " " + stockPrice(key) );
        }
    }

    public static void setPrice( String symbol, Float price, String client ) { //Method to update current stock price
        stocksDetails.get(symbol).setPrice(price);
    }

    public static String getSecurityName( String key ){     //Method To get Security Name 
        return StocksDB.stocksDetails.get(key).getName();
    }

    public static float stockPrice( String key ){          //Method To get Stock Price
        return StocksDB.stocksDetails.get(key).getPrice();
    }

    public static void setBidLog(String client, String symbol, Float Price, String time  ){ //Method to Store Bidding History

        StockLog log = new StockLog(client, symbol, Price, time );
        if ( stockLog.containsKey(symbol) ) {
            stockLog.get(symbol).add( log );
        } else {
            ArrayList<StockLog> list = new ArrayList<>();
            list.add( log );
            stockLog.put(symbol, list);
        }

    }

}

class StockLog {            //Object to Maintain the Bidding History
    String clientName;
    String BIDtime="";
    String BIDsymbol;
    Float BIDprice;

    public StockLog(String client, String symbol ,Float price, String time) {
        clientName = client;
        BIDtime = time;
        BIDsymbol = symbol;
        BIDprice = price;
    }

    public String details(){        //Method to return Bidding History in specific String Format for display in GUI
        return  "Bid: "+BIDprice + " placed by "+ clientName + " at " + BIDtime+"\n";
    }

    public String clientUpdate(){   //Method to return Bidding History in specific String Format for notify clients
        return "New Bid is placed by "+clientName+". Current Price: "+BIDprice;
    }
}

class StockInfo{        //Object to Maintain Details about a Stock Item 
    private String SecurityName;
    private float stockPrice;

    public StockInfo(String SName, float price ) {
        SecurityName = SName;
        stockPrice = price;
    }

    public String getName() {
        return SecurityName;
    }

    public float getPrice() {
        return stockPrice;
    }

    public void setPrice( float price ) {
        stockPrice = price;
    }
}  