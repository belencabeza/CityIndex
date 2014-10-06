package tradePackage;

/*import testlink.IConstants;
import testlink.TestClass;
import testlink.api.java.client.TestLinkAPIException;
import testlink.api.java.client.TestLinkAPIResults;
*/
import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.ConnectClass;
import common.GetConfirmationTicket;
import common.ValueRetriever;
import fit.ColumnFixture;

public class TradeFromMarketsClass extends ColumnFixture{

	public String tradeFromMarkets() throws MonkeyTalkFailure
	{
		Application app=new ConnectClass().connect();
		String resultado=null;
		String msg=null;
		  
		try
		{
			app.tabBar().select("Markets");
			app.button("#7").tap(new Mods.Builder().thinktime(5000).build());
			
			String quantity=new ValueRetriever().getQuantityTrade(app);
			
			app.input("Quantity").enterText(quantity);
			app.button("Sell").tap();
			app.button("Trade").tap(new Mods.Builder().thinktime(5000).build());

			//Get trade confirmation ticket depending on iPhone used
			String label=new GetConfirmationTicket().getConfirmationTicket(app);
			
			app.button("OK").tap(new Mods.Builder().thinktime(5000).build());
			if (label==null)
			{
				//resultado=TestLinkAPIResults.TEST_FAILED;
				msg=label;
				return label;
			}
			else if((!(label.contains("Trade Confirmation") && label.contains("Direction:  Sell")) || (label==null)))
			{
				//resultado=TestLinkAPIResults.TEST_FAILED;
				msg=label;
				return label;
			}
				else
				{
					//resultado=TestLinkAPIResults.TEST_PASSED;
					return "Pass";
				}
		}
		catch(MonkeyTalkFailure e)
		{
			//resultado=TestLinkAPIResults.TEST_FAILED;
			msg=e.getMessage();
			String failure=e.toString();
			return failure;
		}
		 finally
		 {
			 
			 //TestClass.updateTestLinkResult(PROJECTNAME, TESTPLANNAME, "CIiPad-87", BUILDNAME, msg, resultado);  
		 }
	}
}
