package tradePackage;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.ConnectClass;
import common.GetConfirmationTicket;
import common.ValueRetriever;
import fit.ColumnFixture;

public class CalculateMarginClass extends ColumnFixture{

	public String calculateMargin() throws MonkeyTalkFailure
	{
		Application app=new ConnectClass().connect();
		String results=null;
		
		try
		{
			app.tabBar().select("Markets");
			app.button("#7").tap(new Mods.Builder().thinktime(5000).build());
			
			String quantity=new ValueRetriever().getQuantityTrade(app);
			
			app.input("Quantity").enterText(quantity);
			app.button("Sell").tap();
			app.button("Calculate Margin").tap(new Mods.Builder().thinktime(5000).build());
			String label=new GetConfirmationTicket().getCalculteMargin(app);
			app.button("OK").tap(new Mods.Builder().thinktime(5000).build());
			if (label==null)
			{
				return "The label was not found";
			}
			else if((!(label.contains("Margin Calculator") && label.contains("Margin Requirement") && label.contains("Pre-trade") && label.contains("Post-trade")) || (label==null)))
			{
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
			return failure+results;
		}
	}
	
}
