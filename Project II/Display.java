/*
    Group Project | Auction Server
    E/15/131 | E/15/348
    Group No : 10
*/
import java.awt.*;
import javax.swing.Timer;
import java.text.*;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.*;

public class Display extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    MainServer server;
    Timer timer;                    //Timer to create an action event
    JLabel[] labels;
    Label [] label;
    JButton[] showButton;
    Button[] button;                //Buttons to Display Bid history of a symbol
    JTextArea textArea;             //Text area to dispaly all bids placed
    JPanel display;
    JPanel bidHistory;

    Map<Integer, ArrayList<String>> BidLog = new HashMap<>();       //Store Bid History for each symbol
    
    class Label{        //Class to create label and add some styles
	    JLabel jlabel;
        Label( JLabel label, JPanel panel, String name ){    
            jlabel = label = new JLabel(name);
            label.setFont( new Font("Serif", Font.PLAIN, 14) );
            label.setBorder( BorderFactory.createLineBorder(Color.BLACK) );
            label.setHorizontalAlignment( SwingConstants.CENTER );
            panel.add( label );
        }
    }

    class Button{       //Class to create buttons and add some features
        JButton jbutton;
        Button(JButton button,JPanel panel, String name){
            jbutton = button = new JButton( name );
            button.addActionListener(Display.this);
            button.setFont(new Font("Serif", Font.PLAIN, 14));
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            button.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(button);
        }
    }

    public Display( MainServer server ){        //Constructor
        super(new BorderLayout());

        for (int i=0;i<8 ;i++ ) {               //Intializing Bid History for each symbol
            ArrayList<String> list = new ArrayList<>();
            BidLog.put(i, list);
        }

        this.server = server;
        //Creating and Formaintg GUI
        labels = new JLabel[28];
        label = new Label[24];
        showButton = new JButton[8];
        button = new Button[8];
        display = new JPanel();
        bidHistory = new JPanel();
        bidHistory.setLayout(new BorderLayout());

        textArea = new JTextArea(10, 20);       //Create text area to display all bids
        textArea.setEditable(false);
        textArea.append("All Bidding History\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        bidHistory.add(scrollPane);
    
        display.setLayout(new GridLayout(9, 4));

        //Creating Header Labels i.e. Feilds
        setHeader(0,"Symbol");
        setHeader(1,"Security Name");
        setHeader(2,"Current Price");
        setHeader(3," Show Bid History");
        
        //Creating contents Labels i.e. Feilds and Buttons
        createLabelRow( 1, "FB" );
        createLabelRow( 2, "VRTU" );
        createLabelRow( 3, "MSFT" );
        createLabelRow( 4, "GOOGL" );
        createLabelRow( 5, "YHOO" );
        createLabelRow( 6, "XLNX" );
        createLabelRow( 7, "TSLA" );
        createLabelRow( 8, "TXN" );

        add( display, BorderLayout.CENTER);
        add( bidHistory, BorderLayout.SOUTH);
        timer = new Timer(500, this); 
        timer.start(); 
    
    }

    public void setHeader( int i, String FieldName ){       //Method to create and set Header Feilds
        labels[i]=new JLabel(FieldName);
        labels[i].setFont(new Font("Serif", Font.BOLD, 18));
        labels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
        labels[i].setHorizontalAlignment(SwingConstants.CENTER);
        display.add(labels[i]);
    }

    public void createLabelRow( int i ,String symbol) {     //Method to Create table of labels and Button
        label[ (i-1)*3 ] = new Label( labels[3*i+1], display, symbol );
 		labels[3*i+1] = label[ (i-1)*3 ].jlabel;
         
        label[ (i-1)*3 + 1 ] = new Label( labels[3*i+2], display, SecurityName(symbol) );
 		labels[3*i+2] = label[ (i-1)*3 +1 ].jlabel;
         
        label[ (i-1)*3 + 2 ] = new Label( labels[3*i+3], display, Price(symbol) );
 		labels[3*i+3] = label[ (i-1)*3 +2 ].jlabel;
         
        button[i-1] = new Button( showButton[i-1], display, symbol );       //Button for display Bids placed on a symbol 
        showButton[i-1] = button[i-1].jbutton;
    }

    public void actionPerformed(ActionEvent e) {
        //When a new bid is placed update the current price, update the bid history and update the text area. Checks in every 500ms
        String timeStamp;
        for( Client s: MainServer.clientList ){     //Checks all the clients for new bids
            timeStamp = new SimpleDateFormat("EEE, MMM d, yyyy 'at' h:mm a").format(Calendar.getInstance().getTime()); //get system time and date
            if(s.newBidState){                      //If a new Bid is placed
                StocksDB.setBidLog( s.clientName, s.symbol, s.bidPrice, timeStamp );        //Updating History
                textArea.append(timeStamp + " : " + s.clientName + " placed a Bid of "+ Price( s.symbol ) +" on "+ s.symbol  +".\n");      //Updating text area
                textArea.setCaretPosition(textArea.getDocument().getLength());
    
                for( int j=0; j<8; j++ ){       //Updating Current price of Bid Item when new bid is placed
                    if( s.symbol.equals( label[3*j].jlabel.getText() ) ){
                        labels[ (j+2)*3 ].setText( Price(s.symbol) ); 
                        setSymbolLogs(j,s.symbol);   
                    }
                }
                s.newBidState=false;
            }          
        }

        //When a button is clicked display all the bids placed on Symbol corresponds to the button
        if( e.getSource() instanceof JButton ) {
            String buttonSymbol = ((JButton)e.getSource()).getText();
            for( int i = 0; i<8; i++){   
                if( buttonSymbol.equals( label[3*i].jlabel.getText() ) ){
                    if( BidLog.get(i).isEmpty() ){
                        JOptionPane.showMessageDialog(null, "No Bids have been placed" , "All Bids for " + buttonSymbol, JOptionPane.PLAIN_MESSAGE);    
                    }
                    else{
                        JOptionPane.showMessageDialog(null, BidLog.get(i)+"\n" , "All Bids for " + buttonSymbol, JOptionPane.PLAIN_MESSAGE);
                    }
                    
                }
            }
        }
    }

    public void setSymbolLogs(int k, String symbol ) {          //Method to update history for a symbol
        int length = StocksDB.stockLog.get( symbol ).size();
        ArrayList<String> list = new ArrayList<>();
        BidLog.put(k, list);
        
        for(int i =0; i< length;i++){
            BidLog.get(k).add(StocksDB.stockLog.get(symbol).get(i).details());
        }
    }

    public String SecurityName( String key ){               //Method to retrive Security Name
        return StocksDB.stocksDetails.get(key).getName();
    }

    public String Price( String key ){                      //Method to retrive Stock Price
        return  Float.toString(StocksDB.stocksDetails.get(key).getPrice());
    }
}