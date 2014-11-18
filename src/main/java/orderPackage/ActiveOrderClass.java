package orderPackage;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;

import common.CheckElement;
import common.ConnectClass;
import fit.ColumnFixture;

//CIPH-34: Active Orders Screen
public class ActiveOrderClass extends ColumnFixture{
	 String results=null;
	 
	 public String activeOrder() throws MonkeyTalkFailure{
			
		 Application app=new ConnectClass().connect();
		 try{
			 app.tabBar().select("Positions");
			 app.label("Orders").tap();
			 CheckElement element=new CheckElement();
			 results= element.checkLabel(app, "Orders");
			 results= element.checkLabel(app, "P&L");
			 results= element.checkLabel(app, "Margin");
			 results= element.checkLabel(app, "Resource");
			 results= element.checkLabel(app, "Orders");
			 results= element.checkLabel(app, "Order");
			 results= element.checkLabel(app, "Qty");
			 results= element.checkLabel(app, "Order Price");
			 results= element.checkLabel(app, "Current");
			 results= element.checkLabel(app, "Distance Away");
			 if(results==null){
					return "Pass";
				}
				else
				{
					results=". Labels not found:" + results.substring(5);
					return results;
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
