package tradePackage;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.ConnectClass;
import common.GetConfirmationTicket;
import common.ValueRetriever;
import fit.ColumnFixture;

//CIPH-379: Edit a Closing order
public class EditClosingOrderClass extends ColumnFixture {
	
	public String cfdMarket;
	
	public String editClosingOrder() throws MonkeyTalkFailure
	{
		Application app=new ConnectClass().connect();
		try
		{
			TradeStopLimitClass trade=new TradeStopLimitClass();
			trade.cfdMarket=cfdMarket;
			trade.tradeStopLimit();
			
			app.table("Empty list").selectIndex(1, new Mods.Builder().thinktime(10000).build());
			
			app.label("Stop / Limits").verify(new Mods.Builder().thinktime(5000).build());
			String stopPriceInput= new ValueRetriever().getStopPriceInput(app);
			String stopQuantityInput= new ValueRetriever().getStopQuantityInput(app);
			String limitPriceInput= new ValueRetriever().getLimitPriceInput(app);
			String limitQuantityInput= new ValueRetriever().getLimitQuantityInput(app);
			app.input("Stop").enterText(stopPriceInput);
			app.input("Quantity").enterText(stopQuantityInput);
			app.input("Limit").enterText(limitPriceInput);
			app.input("Quantity(2)").enterText(limitQuantityInput);
			app.button("Set Stop & Limit").tap();	
			
			String label=new GetConfirmationTicket().getConfirmationTicketClosingOrder(app);
			if (label==null)
			{
				app.label("OK").tap(new Mods.Builder().thinktime(5000).build());
				return "The label was not found";
			}
			else if(!(label.contains("Success: Your order has been saved") || (label==null)))
			{
				app.label("OK").tap(new Mods.Builder().thinktime(5000).build());
				return label;
			}
			else
			{
				app.button("OK").tap(new Mods.Builder().thinktime(5000).build());
			}
			
			app.label("Orders On Position").verify(new Mods.Builder().thinktime(5000).build());
			String stopPrice= app.label("#21").get();
			if (stopPrice.indexOf(".")>0)
			{
				stopPrice=stopPrice.substring(0, stopPrice.indexOf("."));
			}
			
			if (stopPrice.contains(","))
			{
				stopPrice=stopPrice.replaceAll( "[^\\d]", "" );
			}
			String stopQuantity= app.label("#23").get();
			String limitPrice= app.label("#28").get();
			if (limitPrice.indexOf(".")>0)
			{
				limitPrice=limitPrice.substring(0, limitPrice.indexOf("."));
			}
			
			if (limitPrice.contains(","))
			{
				limitPrice=limitPrice.replaceAll( "[^\\d]", "" );
			}
			String limitQuantity= app.label("#30").get();
			System.out.println(stopPrice);
			System.out.println(stopQuantity);
			System.out.println(limitPrice);
			System.out.println(limitQuantity);
			if (stopPrice.equals(stopPriceInput) && stopQuantity.equals(stopQuantityInput) && limitPrice.equals(limitPriceInput) && limitQuantity.equals(limitQuantityInput))
			{
				return "Pass";
			}
			else
			{
				return "Closing orders were not saved correctly";
			}
			
			
		}
		catch(MonkeyTalkFailure e)
		{
			String failure=e.toString();
			return failure;
		}
	}
}
