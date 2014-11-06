package tradePackage;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.CheckElement;
import common.ConnectClass;
import common.GetConfirmationTicket;
import common.ValueRetriever;
import fit.ColumnFixture;

//CIPH-289:Create a Closing order
public class TradeStopLimitClass extends ColumnFixture{
	String results=null;
	public String cfdMarket;
	
	public String tradeStopLimit() throws MonkeyTalkFailure
	{
		Application app=new ConnectClass().connect();
		try
		{
			app.tabBar().select("Markets");
			app.button("Search").tap();
			app.input("_searchField").enterText(cfdMarket, "enter", new Mods.Builder().thinktime(5000).build());
			app.label(cfdMarket+"(2)").tap(new Mods.Builder().thinktime(5000).build());
			app.button("Trade").tap(new Mods.Builder().thinktime(5000).build());
			
			String quantity=new ValueRetriever().getQuantityTrade(app);
			
			app.input("Quantity").enterText(quantity);
			app.button("Sell").tap();
			app.image("plusButtonIcon").tap();
			app.label("Stop / Limits").verify();
			app.input("2").enterText(quantity);
			String stop= new ValueRetriever().getStopPrice(app);
			app.input("1").enterText(stop);
			app.input("4").enterText(quantity);
			String limit= new ValueRetriever().getLimitPrice(app);
			app.input("3").enterText(limit);
			app.button("209").tap();
			CheckElement element=new CheckElement();
			results= element.checkLabel(app, "Order Stop Limit 1");
			
			app.button("Trade").tap(new Mods.Builder().thinktime(5000).build());
			
			String label=new GetConfirmationTicket().getConfirmationTicket(app);
			
			app.button("OK").tap(new Mods.Builder().thinktime(5000).build());
			
			if (label==null)
			{
				app.label("OK").tap(new Mods.Builder().thinktime(5000).build());
				return "The label was not found";
			}
			else if((!(label.contains("Trade Confirmation") && label.contains("Direction:  Sell")) || (label==null)))
			{
				app.label("OK").tap(new Mods.Builder().thinktime(5000).build());
				return label;
			}

			app.tabBar().select("Positions");
			app.label("Direction").tap(new Mods.Builder().thinktime(5000).build());
			app.button("Closing Orders").tap(new Mods.Builder().thinktime(5000).build());
			app.button("Delete").verify(new Mods.Builder().thinktime(5000).build());
			
			if(results==null){
				results="";
			}
			else
			{
				results=". Labels not found:" + results.substring(5);
			}
			return "Pass";
		}
		catch(MonkeyTalkFailure e)
		{
			String failure=e.toString();
			return failure;
		}
	}
}
