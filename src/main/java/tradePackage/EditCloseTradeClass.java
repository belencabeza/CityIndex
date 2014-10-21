package tradePackage;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.ConnectClass;
import common.GetConfirmationTicket;
import fit.ColumnFixture;

//CIPH-14: Edit Trade before to close it
public class EditCloseTradeClass extends ColumnFixture{
	
	public String editCloseTrade() throws MonkeyTalkFailure{
			
		Application app=new ConnectClass().connect();
		try 
		{
			new NormalTradeClass().normalTrade();
			app.label("Back").tap();
			app.label("Back").tap();
			app.tabBar().select("Positions");
			app.button("listView white").tap(new Mods.Builder().thinktime(5000).build());
			app.button("Close").tap(new Mods.Builder().thinktime(5000).build());
			app.label("Single Position").tap();
			String quantity= app.input("#1").get("value");
			long quantityentero= Long.parseLong(quantity);
			quantity=Long.toString(quantityentero-2);
			app.input("Quantity").enterText(quantity);
			app.button("Buy").tap();
			String label=new GetConfirmationTicket().getConfirmationTicketClose(app);
			app.button("OK").tap(new Mods.Builder().thinktime(5000).build());
			app.label("Single Positions").verify(new Mods.Builder().thinktime(5000).build());
			app.button("boxView white").tap();
			if (label==null)
			{
				app.label("OK").tap(new Mods.Builder().thinktime(5000).build());
				return "The label was not found";
			}
			else if((!(label.contains("Trade Confirmation") && label.contains("Direction:  Buy")) && label.contains("Quantity:  " + quantity) || (label==null)))
			{
				app.label("OK").tap(new Mods.Builder().thinktime(5000).build());
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
