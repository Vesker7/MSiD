public class Offer
{
	boolean type; // 0-BUY 1-SELL
	double price;
	double ammount;
	
	public Offer()
	{
		type = true;
		price = 0;
		ammount = 0;
	}
	
	public Offer(boolean type, double price, double ammount)
	{
		this.type = type;
		this.price = price;
		this.ammount = ammount;
	}

	@Override
	public String toString() 
	{
		String s = "";
		
		if(!type) s+="SELL ";
		else s+="BUY  ";
		
		s+= String.format("|price: %-15.2f|ammount: %-12.8f|", price, ammount);
		return s;
	}
	
}
