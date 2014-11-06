package tradePackage;



import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.CheckElement;
import common.ConnectClass;
import common.GetConfirmationTicket;
import common.ValueRetriever;
import fit.ColumnFixture;

//CIPH-10: Create Trade
public class NormalTradeClass extends ColumnFixture{
	
	String results=null;
	public String cfdMarket;
	public String normalTrade() throws MonkeyTalkFailure{
		
		Application app=new ConnectClass().connect();
		try 
		{
			app.tabBar().select("Markets");
			app.button("Search").tap();
			app.input("_searchField").enterText(cfdMarket, "enter", new Mods.Builder().thinktime(5000).build());
			app.label(cfdMarket+"(2)").tap(new Mods.Builder().thinktime(5000).build());
			app.button("Trade").tap(new Mods.Builder().thinktime(5000).build());
			
			String quantity=new ValueRetriever().getQuantityTrade(app);
			
			CheckElement element=new CheckElement();
			results= element.checkLabel(app, "Trade");
			results= element.checkButton(app, "Calculate Margin");
			results= element.checkButton(app, "Trade");
			results= element.checkLabel(app, "Add Stop / Limit");
			results= element.checkNotLabel(app, "Bet Per");
			
			app.input("Quantity").enterText(quantity);
			app.button("Sell").tap();
			app.button("Trade").tap();
			
			//Get trade confirmation ticket depending on iPhone used
			String label=new GetConfirmationTicket().getConfirmationTicket(app);
			
			app.button("OK").tap(new Mods.Builder().thinktime(5000).build());
		
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
			if(results==null){
				results="";
			}
			else
			{
				results=". Labels not found:" + results.substring(5);
			}
			String failure=e.toString();
			return failure+results;
		}			 
		
	}

}
