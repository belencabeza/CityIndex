package tradePackage;



import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.CheckElement;
import common.ConnectClass;
import common.GetConfirmationTicket;
import common.ValueRetriever;
import fit.ColumnFixture;

public class NormalTradeClass extends ColumnFixture{
	
	String results=null;
	String results2;
	String resultado=null;
	  String msg=null;
	public String normalTrade() throws MonkeyTalkFailure{
		
		Application app=new ConnectClass().connect();
		String label = null;
		String failure=null;
		try 
		{
			app.tabBar().select("Markets");
			app.label("Wall Street CFD").tap();
			app.button("Trade").tap(new Mods.Builder().thinktime(5000).build());
			
			String quantity=new ValueRetriever().getQuantityTrade(app);
			
			CheckElement element=new CheckElement();
			results= element.checkLabel(app, "Trade");
			
			app.input("Quantity").enterText(quantity);
			app.button("Sell").tap();
			app.button("Trade").tap();
			
			//Get trade confirmation ticket depending on iPhone used
			label=new GetConfirmationTicket().getConfirmationTicket(app);
			
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
				label=label+results;
			}
			else if((!(label.contains("Trade Confirmation") && label.contains("Direction:  Sell")) || (label==null)))
			{
				label=label+results;
			}
				else
				{
					label="Pass"+results;
					msg="bien";
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
			failure=e.toString();
			   msg = e.getMessage();
			label=failure+results;
		}
		return label; 
			 
		
	}

}
