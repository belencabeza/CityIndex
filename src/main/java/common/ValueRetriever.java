package common;

import java.math.BigDecimal;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.utils.Mods;

public class ValueRetriever {
	
	//get quantity in trade
	public String getQuantityTrade(Application app){
		String quantity=app.label("#11").get();
		int num=13;
		
		do{
			quantity=app.label("#"+num).get();
			while(quantity==null)
			{
				num=num+1;
				quantity=app.label("#"+num).get();
			}
			num=num+1;			
		}while(!quantity.contains(">="));

		
		if (quantity.contains("."))
		{
			quantity=quantity.substring(3, quantity.indexOf("."));
		}
		else
		{
			quantity=quantity.substring(3);
		}
		if (quantity.contains(","))
		{
			quantity=quantity.replaceAll( "[^\\d]", "" );
		}
		long quantityentero= Long.parseLong(quantity);
		quantity=Long.toString(quantityentero+10);
		return quantity;
	}
	
	public String getQtyTrade(Application app){
		String qty=app.label("#11").get(new Mods.Builder().thinktime(5000).build());
		int num=13;
		do{
			qty=app.label("#"+num).get();
			while(qty==null)
			{
				num=num+1;
				qty=app.label("#"+num).get();
			}
			num=num+1;			
		}while(!qty.contains(">="));
		return Integer.toString(num);
	}
	
	//get stop price in Stop&Limit
	public String getStopPrice(Application app){
		String stopprice=app.label("#10").get();
	
		stopprice=stopprice.substring(2, stopprice.indexOf("."));
		if (stopprice.contains(","))
		{
			stopprice=stopprice.replaceAll( "[^\\d]", "" );
		}
		BigDecimal stopentero= new BigDecimal(new Float(stopprice));
		stopentero=stopentero.add(new BigDecimal(10));
		stopprice=stopentero.toString();
		return stopprice;
	}
	
	//get limit price in Stop&Limit
	public String getLimitPrice(Application app){
		String limitprice=app.label("#19").get();
	
		limitprice=limitprice.substring(2, limitprice.indexOf("."));
		if (limitprice.contains(","))
		{
			limitprice=limitprice.replaceAll( "[^\\d]", "" );
		}
		BigDecimal limitentero= new BigDecimal(new Float(limitprice));
		if (limitentero.longValue()<=1)
		{
			limitentero=limitentero.subtract(new BigDecimal(0.1));
		}
		else
		{
			limitentero=limitentero.subtract(new BigDecimal(10));
		}
		limitprice=limitentero.toString();
		return limitprice;
	}
	
	//get stop quantity value in Stop&Limit
	public String getStopQuantityInput(Application app){
		String stopquantity=app.input("#2").get("value");
		
		long stopquantityentero= Long.parseLong(stopquantity);
		stopquantity=Long.toString(stopquantityentero-2);
		return stopquantity;
	}
	
	//get stop price value in Stop&Limit
	public String getStopPriceInput(Application app){
		String stopprice=app.input("#1").get("value");
		
		if (stopprice.indexOf(".")>0)
		{
		stopprice=stopprice.substring(0, stopprice.indexOf("."));
		}
		if (stopprice.contains(","))
		{
			stopprice=stopprice.replaceAll( "[^\\d]", "" );
		}
		BigDecimal stopentero= new BigDecimal(new Float(stopprice));
		stopentero=stopentero.add(new BigDecimal(10));
		stopprice=stopentero.toString();
		return stopprice;
	}	
	
	//get limit quantity value in Stop&Limit
	public String getLimitQuantityInput(Application app){
		String limitquantity=app.input("#4").get("value");
		
		long limitquantityentero= Long.parseLong(limitquantity);
		limitquantity=Long.toString(limitquantityentero-2);
		return limitquantity;
	}
	
	//get limit price value in Stop&Limit
	public String getLimitPriceInput(Application app){
		String limitprice=app.input("#3").get("value");
	
		if (limitprice.indexOf(".")>0)
		{
			limitprice=limitprice.substring(0, limitprice.indexOf("."));
		}
		
		if (limitprice.contains(","))
		{
			limitprice=limitprice.replaceAll( "[^\\d]", "" );
		}
		BigDecimal limitentero= new BigDecimal(new Float(limitprice));
		if (limitentero.longValue()<=1)
		{
			limitentero=limitentero.subtract(new BigDecimal(0.1));
		}
		else
		{
			limitentero=limitentero.subtract(new BigDecimal(10));
		}
		limitprice=limitentero.toString();
		return limitprice;
	}
	
	//get quantity in order
	public String getQuantityOrder(Application app){
		String quantity=app.label("#9").get("value", new Mods.Builder().thinktime(5000).build());
		int num=11;
		while(!quantity.contains(">")|| quantity.isEmpty())
		{
			quantity=app.label("#"+num).get();
			num=num+1;
		}
		
		if (quantity.contains("."))
		{
			quantity=quantity.substring(3, quantity.indexOf("."));
		}
		else
		{
			quantity=quantity.substring(3);
		}
		if (quantity.contains(","))
		{
			quantity=quantity.replaceAll( "[^\\d]", "" );
		}
		long quantityentero= Long.parseLong(quantity);
		quantity=Long.toString(quantityentero+10);
		return quantity;
	}
	
	//get price in order
	public String getPriceOrder(Application app){
		String price=app.label("#13").get("value");
		int num=15;
		while(!price.contains("< "))
		{
			price=app.label("#"+num).get();
			num=num+1;
		}
		if (price.indexOf(".")>0)
		{
			price=price.substring(0, price.indexOf("."));
		}
		
		if (price.contains(","))
		{
			price=price.replaceAll( "[^\\d]", "" );
		}
		BigDecimal priceentero= new BigDecimal(new Float(price));
		if (priceentero.longValue()<=1)
		{
			priceentero=priceentero.subtract(new BigDecimal(0.1));
		}
		else
		{
			priceentero=priceentero.subtract(new BigDecimal(10));
		}
		price=priceentero.toString();
		return price;
	}
	
	//get Time in Order
	public String getTime(Application app){
		String time=app.datePicker("_dpGoodUntil").get("value");
		String timeDay=time.substring(8, 10);
		long timeNum= Long.parseLong(timeDay);
		timeNum=timeNum+1;
		timeDay=Long.toString(timeNum);
		time=time.substring(0,8)+timeDay+time.substring(10);
		return time;
	}
	
	//get Quantity in Order to verify position of confirmation ticket
	public String getQty(Application app){
		String qty=app.label("#9").get(new Mods.Builder().thinktime(5000).build());
		int num=9;
		while(!qty.contains(">"))
		{
			num=num+1;
			qty=app.label("#"+num).get();	
		}
		return Integer.toString(num);
	}
	
	//Get quantity input value
	public String getQuantityInputOrder(Application app){
		String quantity=app.input("1").get();
		if (quantity.contains("."))
		{
			quantity=quantity.substring(0, quantity.indexOf("."));
		}
		if (quantity.contains(","))
		{
			quantity=quantity.replaceAll( "[^\\d]", "" );
		}
		long quantityentero= Long.parseLong(quantity);
		quantity=Long.toString(quantityentero+2);
		return quantity;
	}
	
	//Get Price input value
	public String getPriceInputOrder(Application app){
		String price=app.input("2").get();
		if (price.contains("."))
		{
			price=price.substring(0, price.indexOf("."));
		}
		if (price.contains(","))
		{
			price=price.replaceAll( "[^\\d]", "" );
		}
		BigDecimal priceentero= new BigDecimal(new Float(price));
		if (priceentero.longValue()<=1)
		{
			priceentero=priceentero.subtract(new BigDecimal(0.1));
		}
		else
		{
			priceentero=priceentero.subtract(new BigDecimal(10));
		}
		price=priceentero.toString();
		return price;
	}


}
