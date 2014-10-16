package tradePackage;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.CheckElement;
import common.ConnectClass;
import common.GetConfirmationTicket;
import common.ValueRetriever;
import fit.ColumnFixture;

public class TradeStopLimitClass extends ColumnFixture{
	String results=null;
	
	public String tradeStopLimit() throws MonkeyTalkFailure
	{
		Application app=new ConnectClass().connect();
		try
		{
			app.tabBar().select("Markets");
			app.button("#7").tap(new Mods.Builder().thinktime(5000).build());
			
			String quantity=new ValueRetriever().getQuantityTrade(app);
			
			app.input("Quantity").enterText(quantity);
			app.button("Sell").tap();
			app.image("plusButtonIcon").tap();
			app.label("Stop / Limits").verify();
			app.input("1").enterText(new ValueRetriever().getStopPrice(app));
			app.input("2").enterText(quantity);
			app.input("3").enterText(new ValueRetriever().getLimitPrice(app));
			app.input("4").enterText(quantity);
			app.button("209").tap();
			CheckElement element=new CheckElement();
			results= element.checkLabel(app, "Order Stop Limit 1");
			
			app.button("Trade").tap(new Mods.Builder().thinktime(5000).build());
			
			String label=new GetConfirmationTicket().getConfirmationTicket(app);
			
			app.button("OK").tap(new Mods.Builder().thinktime(5000).build());
			
			app.label("Direction").tap();
			app.button("Closing Orders").tap();
			app.button("Delete").verify();
			
			if(results==null){
				results="";
			}
			else
			{
				results=". Labels not found:" + results.substring(5);
			}
			
			if (label==null)
			{
				return "The label was not found"+results;
			}
			else if((!(label.contains("Trade Confirmation") && label.contains("Direction:  Sell")) || (label==null)))
			{
				return label+results;
			}
				else
				{
					return "Pass"+results;
				}

		}
		catch(MonkeyTalkFailure e)
		{
			String failure=e.toString();
			return failure;
		}
	}
}
