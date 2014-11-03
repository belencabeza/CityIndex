package tradePackage;


import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.ConnectClass;
import common.GetConfirmationTicket;
import common.ValueRetriever;
import fit.ColumnFixture;

//CIPH-398:Trade in Options Markets.
public class TradeOptionMarketsClass extends ColumnFixture{
	
	public String tradeOptionMarkets() throws MonkeyTalkFailure
	{
		Application app=new ConnectClass().connect();
		  
		try
		{
			app.tabBar().select("Markets");
			app.button("Search").tap();
			app.button("802").tap(new Mods.Builder().thinktime(5000).build());
			app.table("Empty list").select("Options");
			app.table("Empty list").select("All Options");
			
			app.button("Trade(3)").tap(new Mods.Builder().thinktime(5000).build());
			app.view("#56").verify();
			app.button("Sell").tap(new Mods.Builder().thinktime(5000).build());
			
			String quantity=new ValueRetriever().getQuantityTrade(app);
			app.input("Quantity").enterText(quantity);
			app.view("#56").verify();
			app.button("Trade").tap();
			
			//Get trade confirmation ticket depending on iPhone used
			String label=new GetConfirmationTicket().getConfirmationTicket(app);
			if (label==null)
			{
				app.label("Cancel Trade").tap(new Mods.Builder().thinktime(5000).build());
				return "The label was not found";
			}
			else if((!(label.contains("Trade Confirmation") && label.contains("Direction:  Sell")) || (label==null)))
			{
				app.label("Cancel Trade").tap(new Mods.Builder().thinktime(5000).build());
				return label;
			}
				else
				{
					return "Pass";
				}
		}
		catch(MonkeyTalkFailure e)
		{
			String failure=e.toString();
			return failure;
		}
	}
}
