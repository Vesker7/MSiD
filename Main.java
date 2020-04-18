import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class Main 
{
	static ArrayList <Offer> buy;
	static ArrayList <Offer> sell;
	
	private static void bitbayUpdate(URL source) throws IOException
	{
		String tab[];
		int i=0; buy = new ArrayList<Offer>(); sell = new ArrayList<Offer>();
		
		URLConnection connection = source.openConnection();
		connection.connect();
		
		BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		tab = buffer.readLine().replaceAll("[:\\[\\]\\{\\}]", "").split(",");
		
		tab[0]=tab[0].replace("\"bids\"", "");

		for(; !tab[i].contains("asks"); i+=2)
		{
			Offer temp = new Offer(true,Double.parseDouble(tab[i]),Double.parseDouble(tab[i+1]));
			buy.add(temp);
		}
		
		tab[i]=tab[i].replace("\"asks\"", "");
		
		
		for(; i<tab.length-1; i+=2)
		{
			Offer temp = new Offer(false,Double.parseDouble(tab[i]),Double.parseDouble(tab[i+1]));
			sell.add(temp);
		}
	}

	private static void printBuySell(URL source) throws IOException
	{
		bitbayUpdate(source);
		
		for(Offer o : buy)
		{
			System.out.println(o);
		}
		
		System.out.println("\n___________________________________________________\n");
		
		for(Offer o : sell)
		{
			System.out.println(o);
		}
	}
	
	private static double buySellDiff() 
	{
		System.out.println(String.format("|BUY: %-15.2f|SELL: %-15.2f|", buy.get(0).price, sell.get(0).price));
		return ( (sell.get(0).price - buy.get(0).price)/buy.get(0).price )*100;
	}

	private static void inspectDiff(URL source) throws IOException, InterruptedException
	{
		while(true)
		{
			bitbayUpdate(source);
			System.out.println("Ró¿nica: " + String.format("%f", buySellDiff()) + "%");
			Thread.sleep(5000);
		}
	}

    public static void main(String[] args) throws Exception 
    {
    	
    	URL bitBayUrl = new URL("https://bitbay.net/API/Public/BTC/orderbook.json");
    	
    	inspectDiff(bitBayUrl);
    	
        
    }
}