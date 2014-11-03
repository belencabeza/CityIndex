package orderPackage;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.CheckElement;
import common.ConnectClass;
import fit.ColumnFixture;

//CIPH-397
public class OrderOptionMarketsClass extends ColumnFixture {
	 String results=null;
	 public String optionMarket;
	 
	 public String orderOptionMarkets() throws MonkeyTalkFailure{
		 Application app=new ConnectClass().connect();
		 try{
			app.tabBar().select("Markets");
			app.button("Search").tap();
			app.input("_searchField").enterText(optionMarket, "enter", new Mods.Builder().thinktime(5000).build());
			app.label(optionMarket+"(2)").tap(new Mods.Builder().thinktime(5000).build());
			app.button("Order").tap(new Mods.Builder().thinktime(5000).build());
			CheckElement element=new CheckElement();
			results= element.checkNotLabel(app, "Good Until");
			results= element.checkNotLabel(app, "Place Order");
			results= element.checkNotLabel(app, "Add OCO");
			results= element.checkLabel(app, "Trade");
			if(results==null){
				return "Pass";
			}
			else
			{
				return results="Labels not found:" + results.substring(5);
			}
		 }
		 catch(MonkeyTalkFailure e)
		 {
			 String failure=e.toString();
			 return failure+results;
		 }
		
	 }
}
