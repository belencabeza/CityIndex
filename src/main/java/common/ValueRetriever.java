package common;

import java.math.BigDecimal;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.utils.Mods;

public class ValueRetriever {
	
	//get quantity in trade
	public String getQuantityTrade(Application app){
		String quantity=app.label("#11").get();
		int num=11;
		
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
	
	//Get Quantity value of a Position
	public String getQtyTrade(Application app){
		String qty=app.label("#11").get(new Mods.Builder().thinktime(5000).build());
		int num=11;
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
	
	//get stop price in Stop&Limit of Closing Orders
	public String getStopPrice(Application app){
		String stopprice=app.label("#10").get();
	
		int num=10;
		do{
			stopprice=app.label("#"+num).get();
			while(stopprice==null)
			{
				num=num+1;
				stopprice=app.label("#"+num).get();
			}
			num=num+1;			
		}while(!stopprice.contains("> "));
		
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
	
	//get limit price in Stop&Limit of Closing Order
	public String getLimitPrice(Application app){
		String limitprice=app.label("#15").get();
	
		int num=15;
		do{
			limitprice=app.label("#"+num).get();
			while(limitprice==null)
			{
				num=num+1;
				limitprice=app.label("#"+num).get();
			}
			num=num+1;			
		}while(!limitprice.contains("< "));
		
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
	
	//get stop quantity value in Stop&Limit of Closing Order
	public String getStopQuantityInput(Application app){
		String stopquantity=app.input("#2").get("value", new Mods.Builder().thinktime(5000).build());
		
		long stopquantityentero= Long.parseLong(stopquantity);
		stopquantity=Long.toString(stopquantityentero-2);
		return stopquantity;
	}
	
	//get stop price value in Stop&Limit of Closing Order
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
	
	//get limit quantity value in Stop&Limit of Closing Order
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
		while(!quantity.contains(">")|| quantity==null)
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
		while(!price.contains("< ") || price==null)
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
	
	//Get the time Set
	public String getTimeSet(Application app, String timeYear){
		String timeSet=app.label("#20").get();
		int num=21;
		while(!timeSet.contains(timeYear) || timeSet==null)
		{
			timeSet=app.label("#"+num).get();
			num=num+1;
		}
		return timeSet;
	}
	
	//get Quantity in Order to verify position of confirmation ticket
	public String getQty(Application app){
		String qty=app.label("#9").get(new Mods.Builder().thinktime(5000).build());
		int num=9;
		while(!qty.contains(">")|| qty==null)
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
	
	//Get Quantity of OCO Order
	public String getQuantityOCO(Application app){
		String quantity=app.label("#11").get("value", new Mods.Builder().thinktime(5000).build());
		int num=11;
		do{
			quantity=app.label("#"+num).get();
			while(quantity==null)
			{
				num=num+1;
				quantity=app.label("#"+num).get();
			}
			num=num+1;			
		}while(!quantity.contains(">"));
		
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
	
	//Get Price of OCO Order
	public String getPriceOCO(Application app){
		String price=app.label("#15").get("value");
		int num=15;
		do{
			price=app.label("#"+num).get();
			while(price==null)
			{
				num=num+1;
				price=app.label("#"+num).get();
			}
			num=num+1;			
		}while(!price.contains("> "));
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
			priceentero=priceentero.add(new BigDecimal(0.1));
		}
		else
		{
			priceentero=priceentero.add(new BigDecimal(10));
		}
		price=priceentero.toString();
		return price;
	}
	
	//Get Quantity in input of the OCO Order
	public String getQuantityInputOco(Application app){
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
	
	//Get Price in input of the OCO Order
	public String getPriceInputOco(Application app){
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
			priceentero=priceentero.add(new BigDecimal(0.1));
		}
		else
		{
			priceentero=priceentero.add(new BigDecimal(10));
		}
		price=priceentero.toString();
		return price;
	}
	
	//get quantity input of OCO Order to verify if the update was correctly
	public String getQtyUpdateInputOco(Application app){
		String quantityInput=app.input("1").get();
		if (quantityInput.contains("."))
		{
			quantityInput=quantityInput.substring(0, quantityInput.indexOf("."));
		}
		if (quantityInput.contains(","))
		{
			quantityInput=quantityInput.replaceAll( "[^\\d]", "" );
		}
		long quantityInputentero= Long.parseLong(quantityInput);
		quantityInput=Long.toString(quantityInputentero);
		return quantityInput;
	}
	
	//get price input of OCO Order to verify if the update was correctly
	public String getPriceUpdateInputOco(Application app){
		String priceInput=app.input("2").get();
		if (priceInput.contains("."))
		{
			priceInput=priceInput.substring(0, priceInput.indexOf("."));
		}
		if (priceInput.contains(","))
		{
			priceInput=priceInput.replaceAll( "[^\\d]", "" );
		}
		BigDecimal priceInputentero= new BigDecimal(new Float(priceInput));
		priceInput=priceInputentero.toString();
		return priceInput;
	}
	
	//get stop price in Stop&Limit in OCO Order
		public String getStopPriceOco(Application app){
			String stopprice=app.label("#10").get();
		
			int num=10;
			do{
				stopprice=app.label("#"+num).get();
				while(stopprice==null)
				{
					num=num+1;
					stopprice=app.label("#"+num).get();
				}
				num=num+1;			
			}while(!stopprice.contains("< "));
			
			stopprice=stopprice.substring(2, stopprice.indexOf("."));
			if (stopprice.contains(","))
			{
				stopprice=stopprice.replaceAll( "[^\\d]", "" );
			}
			BigDecimal stopentero= new BigDecimal(new Float(stopprice));
			stopentero=stopentero.subtract(new BigDecimal(10));
			stopprice=stopentero.toString();
			return stopprice;
		}
		
		//get limit price in Stop&Limit of the OCO order.
		public String getLimitPriceOco(Application app){
			String limitprice=app.label("#15").get();
		
			int num=15;
			do{
				limitprice=app.label("#"+num).get();
				while(limitprice==null)
				{
					num=num+1;
					limitprice=app.label("#"+num).get();
				}
				num=num+1;			
			}while(!limitprice.contains("> "));
			
			limitprice=limitprice.substring(2, limitprice.indexOf("."));
			if (limitprice.contains(","))
			{
				limitprice=limitprice.replaceAll( "[^\\d]", "" );
			}
			BigDecimal limitentero= new BigDecimal(new Float(limitprice));
			if (limitentero.longValue()<=1)
			{
				limitentero=limitentero.add(new BigDecimal(0.1));
			}
			else
			{
				limitentero=limitentero.add(new BigDecimal(10));
			}
			limitprice=limitentero.toString();
			return limitprice;
		}
		
		//Get Stop Quantity Input in Stop&Limit of OCO Order
		public String getStopQtyInputOCO(Application app){
			String quantityStopInput=app.input("2").get(new Mods.Builder().thinktime(5000).build());
			if (quantityStopInput.contains("."))
			{
				quantityStopInput=quantityStopInput.substring(0, quantityStopInput.indexOf("."));
			}
			if (quantityStopInput.contains(","))
			{
				quantityStopInput=quantityStopInput.replaceAll( "[^\\d]", "" );
			}
			long quantityStopInputentero= Long.parseLong(quantityStopInput);
			quantityStopInput=Long.toString(quantityStopInputentero);
			return quantityStopInput;
		}
		
		//Get Stop Price Input in Stop&Limit of OCO Order
		public String getStopPriceInputOCO(Application app){
			String priceStopInput=app.input("1").get();
			if (priceStopInput.contains("."))
			{
				priceStopInput=priceStopInput.substring(0, priceStopInput.indexOf("."));
			}
			if (priceStopInput.contains(","))
			{
				priceStopInput=priceStopInput.replaceAll( "[^\\d]", "" );
			}
			BigDecimal priceStopInputentero= new BigDecimal(new Float(priceStopInput));
			priceStopInput=priceStopInputentero.toString();
			return priceStopInput;
		}
		
		//Get Limit Quantity Input in Stop&Limit of OCO Order
		public String getLimitQtyInputOCO(Application app){
			String quantityLimitInput=app.input("4").get();
			if (quantityLimitInput.contains("."))
			{
				quantityLimitInput=quantityLimitInput.substring(0, quantityLimitInput.indexOf("."));
			}
			if (quantityLimitInput.contains(","))
			{
				quantityLimitInput=quantityLimitInput.replaceAll( "[^\\d]", "" );
			}
			long quantityLimitInputentero= Long.parseLong(quantityLimitInput);
			quantityLimitInput=Long.toString(quantityLimitInputentero);
			return quantityLimitInput;
		}
		
		//Get Limit Price Input in Stop&Limit of OCO Order
		public String getLimitPriceInputOCO(Application app){
			String priceLimitInput=app.input("3").get();
			if (priceLimitInput.contains("."))
			{
				priceLimitInput=priceLimitInput.substring(0, priceLimitInput.indexOf("."));
			}
			if (priceLimitInput.contains(","))
			{
				priceLimitInput=priceLimitInput.replaceAll( "[^\\d]", "" );
			}
			BigDecimal priceLimitInputentero= new BigDecimal(new Float(priceLimitInput));
			priceLimitInput=priceLimitInputentero.toString();
			return priceLimitInput;
		}
		
		
		//Get Stop Quantity Input in Stop&Limit of Order
				public String getStopQtyInputOrder(Application app){
					String quantityStopInput=app.input("2").get(new Mods.Builder().thinktime(5000).build());
					if (quantityStopInput.contains("."))
					{
						quantityStopInput=quantityStopInput.substring(0, quantityStopInput.indexOf("."));
					}
					if (quantityStopInput.contains(","))
					{
						quantityStopInput=quantityStopInput.replaceAll( "[^\\d]", "" );
					}
					long quantityStopInputentero= Long.parseLong(quantityStopInput);
					quantityStopInput=Long.toString(quantityStopInputentero);
					return quantityStopInput;
				}
				
				//Get Stop Price Input in Stop&Limit of OCO Order
				public String getStopPriceInputOrder(Application app){
					String priceStopInput=app.input("1").get();
					if (priceStopInput.contains("."))
					{
						priceStopInput=priceStopInput.substring(0, priceStopInput.indexOf("."));
					}
					if (priceStopInput.contains(","))
					{
						priceStopInput=priceStopInput.replaceAll( "[^\\d]", "" );
					}
					BigDecimal priceStopInputentero= new BigDecimal(new Float(priceStopInput));
					priceStopInput=priceStopInputentero.toString();
					return priceStopInput;
				}
				
				//Get Limit Quantity Input in Stop&Limit of OCO Order
				public String getLimitQtyInputOrder(Application app){
					String quantityLimitInput=app.input("4").get();
					if (quantityLimitInput.contains("."))
					{
						quantityLimitInput=quantityLimitInput.substring(0, quantityLimitInput.indexOf("."));
					}
					if (quantityLimitInput.contains(","))
					{
						quantityLimitInput=quantityLimitInput.replaceAll( "[^\\d]", "" );
					}
					long quantityLimitInputentero= Long.parseLong(quantityLimitInput);
					quantityLimitInput=Long.toString(quantityLimitInputentero);
					return quantityLimitInput;
				}
				
				//Get Limit Price Input in Stop&Limit of OCO Order
				public String getLimitPriceInputOrder(Application app){
					String priceLimitInput=app.input("3").get();
					if (priceLimitInput.contains("."))
					{
						priceLimitInput=priceLimitInput.substring(0, priceLimitInput.indexOf("."));
					}
					if (priceLimitInput.contains(","))
					{
						priceLimitInput=priceLimitInput.replaceAll( "[^\\d]", "" );
					}
					BigDecimal priceLimitInputentero= new BigDecimal(new Float(priceLimitInput));
					priceLimitInput=priceLimitInputentero.toString();
					return priceLimitInput;
				}

}
