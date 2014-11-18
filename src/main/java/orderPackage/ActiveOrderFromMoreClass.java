package orderPackage;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import common.CheckElement;
import common.ConnectClass;

import fit.ColumnFixture;

public class ActiveOrderFromMoreClass extends ColumnFixture{
	 String results=null;
	 
	 public String activeOrderFromMore() throws MonkeyTalkFailure{
			
		 Application app=new ConnectClass().connect();
		 try{
			 app.tabBar().select("More");
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
			 app.label("Back").tap();
			 results= element.checkLabel(app, "News");
			 results= element.checkLabel(app, "Messages");
			 results= element.checkLabel(app, "Help");
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

