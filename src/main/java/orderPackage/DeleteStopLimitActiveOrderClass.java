package orderPackage;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.ConnectClass;
import common.GetConfirmationTicket;
import common.ValueRetriever;
import fit.ColumnFixture;

public class DeleteStopLimitActiveOrderClass extends ColumnFixture{
	public String goodUntil;
	 String results=null;
	 public String cfdMarket;
	 String timeYear;
	 
	 public String deleteStopLimitActiveOrder() throws MonkeyTalkFailure{
		 Application app=new ConnectClass().connect();
		 try{
			 SetStopLimitNewOrderClass stoplimit=new SetStopLimitNewOrderClass();
			 stoplimit.cfdMarket=cfdMarket;
			 stoplimit.goodUntil=goodUntil;
			 stoplimit.setStopLimitNewOrder();
			 app.label("Delete").tap();
			 app.label("Delete").tap();
			 app.label("OK").tap(new Mods.Builder().thinktime(3000).build());
			 app.scroller("#4").scroll(0, 60, new Mods.Builder().thinktime(5000).build() );
			 app.label("Order Stop Limit 1").verifyNot();
			 app.scroller("#4").scroll(0, 0, new Mods.Builder().thinktime(5000).build() );
			 String qty=new ValueRetriever().getQuantityOrder(app);
			 long quantityentero= Long.parseLong(qty);
			 
			 qty=Long.toString(quantityentero-1);
			 app.label("Update Order").tap();
			 String label= new GetConfirmationTicket().getConfirmationOrder(app, goodUntil, qty);
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
				else if(!(label.contains("Success: Your order has been saved") || (label==null)))
				{
					return label+results;
				}
					else
					{
						return "Pass";
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
