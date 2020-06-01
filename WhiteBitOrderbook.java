import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class WhiteBitOrderbook extends Orderbook
{
	public WhiteBitOrderbook(String currency1, String currency2) throws IOException 
	{
		super(new URL("https://whitebit.com/api/v1/public/depth/result?market="+currency1+"_"+currency2+"&limit=10"));
	}
	
	@Override
	void update(int limit) throws IOException 
	{
		String tab[];
		buy = new ArrayList<Offer>(); sell = new ArrayList<Offer>();
		int i=0, counter=0;
		
		//long start = System.currentTimeMillis();
		
		URLConnection connection = source.openConnection();
		connection.addRequestProperty("User-Agent", "Mozilla");
		connection.connect();
		
		BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		//System.out.println("Czas: "+ (System.currentTimeMillis()-start));
		
		tab = buffer.readLine().replaceAll("[:\\[\\]\\{\\}\"]", "").split(",");

		tab[0]=tab[0].replace("asks", "");
		
		for(; !tab[i].contains("bids"); i+=2)
		{
			if(counter < limit)
			{
				Offer temp = new Offer(false,Double.parseDouble(tab[i]),Double.parseDouble(tab[i+1]));
				sell.add(temp);
				counter++;
			}
		}
		
		counter = 0;
		tab[i]=tab[i].replace("bids", "");
		
		for(; i<tab.length-1; i+=2)
		{
			if(counter < limit)
			{
				Offer temp = new Offer(true,Double.parseDouble(tab[i]),Double.parseDouble(tab[i+1]));
				buy.add(temp);
				counter++;
			}
		}
		
		buy.trimToSize();
		sell.trimToSize();
	}

}