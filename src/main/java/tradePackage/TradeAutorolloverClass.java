package tradePackage;


import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.ConnectClass;
import common.GetConfirmationTicket;
import common.ValueRetriever;
import fit.ColumnFixture;

//CIPH-145:Create a Trade with AutoRollover
public class TradeAutorolloverClass extends ColumnFixture{
	
	 public String tradeAutorollover() throws MonkeyTalkFailure{
		
		 Application app=new ConnectClass().connect();
		 try{
			app.tabBar().select("Markets");
			app.button("Search").tap();
			app.input("_searchField").enterText("Soybean Oil", "enter", new Mods.Builder().thinktime(5000).build());
			app.label("Trade").tap(new Mods.Builder().thinktime(5000).build());
			app.button("Sell").tap(new Mods.Builder().thinktime(5000).build());
			String title=app.label("_marketNameLabel").get();
			
			String quantity=new ValueRetriever().getQuantityTrade(app);
			app.input("Quantity").enterText(quantity);
			
			app.toggle("12006").on();
			String qty=new ValueRetriever().getQtyTrade(app);
			app.button("Trade").tap();
			
			//Get trade confirmation ticket depending on iPhone used
			String label=new GetConfirmationTicket().getConfirmationTicketAutorollover(app, qty);
			
			app.button("OK").tap(new Mods.Builder().thinktime(5000).build());
			if (label==null)
			{
				return label;
			}
			else if((!(label.contains("Trade Confirmation") && label.contains("Direction:  Sell")) || (label==null)))
			{
				return label;
			}
				
			app.tabBar().select("Positions");
			app.label(title).tap(new Mods.Builder().thinktime(5000).build());
			String prueba=app.toggle("205").get();
			if(prueba=="off")
			{
				return prueba;
			}
			
			app.toggle("205").off();
			app.label("Back").tap(new Mods.Builder().thinktime(5000).build());
			app.label(title).tap(new Mods.Builder().thinktime(5000).build());
			prueba=app.toggle("205").get();
			if(prueba=="on")
			{
				return prueba;
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
