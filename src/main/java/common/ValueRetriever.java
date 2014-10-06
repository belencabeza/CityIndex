package common;

import java.math.BigDecimal;

import com.gorillalogic.monkeytalk.java.api.Application;

public class ValueRetriever {
	public String getQuantityTrade(Application app){
		String quantity=app.label("#11").get();
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
	
	//get stop price in Stop&Limit
	public String getStopPrice(Application app){
		String stopprice=app.label("#10").get();
	
		stopprice=stopprice.substring(2);
		if (stopprice.contains(","))
		{
			stopprice=stopprice.replaceAll( "[^\\d]", "" );
		}
		BigDecimal stopentero= new BigDecimal(new Float(stopprice));
		stopentero=stopentero.add(new BigDecimal(1));
		stopprice=stopentero.toString();
		return stopprice;
	}
	
	//get limit price in Stop&Limit
	public String getLimitPrice(Application app){
		String limitprice=app.label("#18").get();
	
		limitprice=limitprice.substring(2);
		if (limitprice.contains(","))
		{
			limitprice=limitprice.replaceAll( "[^\\d]", "" );
		}
		BigDecimal limitentero= new BigDecimal(new Float(limitprice));
		if (limitentero.doubleValue()<=1)
		{
			limitentero=limitentero.subtract(new BigDecimal(0.1));
		}
		else
		{
			limitentero=limitentero.subtract(new BigDecimal(1));
		}
		limitprice=limitentero.toString();
		return limitprice;
	}

}
