package tradePackage;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.ConnectClass;
import common.GetConfirmationTicket;
import fit.ColumnFixture;

//CIPH-293: Delete a closing order
public class DeleteClosingOrderClass extends ColumnFixture{
	public String cfdMarket;
	
	public String deleteClosingOrder() throws MonkeyTalkFailure
	{
		Application app=new ConnectClass().connect();
		try
		{
			TradeStopLimitClass trade=new TradeStopLimitClass();
			trade.cfdMarket=cfdMarket;
			trade.tradeStopLimit();
			app.label("Delete").tap();
			String label= new GetConfirmationTicket().getConfirmationDelete(app);
			if (label==null)
			{
				app.label("OK").tap(new Mods.Builder().thinktime(5000).build());
				return "The label was not found";
			}
			else if(!(label.contains("Are you sure you want to delete this order?") || (label==null)))
			{
				app.label("OK").tap(new Mods.Builder().thinktime(5000).build());
				return label;
			}
			else
			{
				app.label("#32").tap(new Mods.Builder().thinktime(5000).build());
			}
			app.label("Delete").verifyNot(new Mods.Builder().thinktime(5000).build());
			return "Pass";
			
		}
		catch(MonkeyTalkFailure e)
		{
			String failure=e.toString();
			return failure;
		}
	}
}
