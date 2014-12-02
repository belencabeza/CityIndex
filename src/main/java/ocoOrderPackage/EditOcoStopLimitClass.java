package ocoOrderPackage;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.CheckElement;
import common.ConnectClass;
import common.GetConfirmationTicket;
import common.ValueRetriever;
import fit.ColumnFixture;

public class EditOcoStopLimitClass extends ColumnFixture {
	 public String goodUntil;
	 String results=null;
	 public String cfdMarket;
	 String time;
	 String flag;
	 String qty;
	 
	 public String editOcoStopLimit() throws MonkeyTalkFailure{
			
		 Application app=new ConnectClass().connect();
		 try{
			 SetOcoClass oco=new SetOcoClass();
			 oco.goodUntil=goodUntil;
			 oco.cfdMarket=cfdMarket;
			 oco.setOco();
			 app.table("Empty list").selectIndex(1, new Mods.Builder().thinktime(5000).build());
			 try
			 {
				 app.label("Cancel OCO").verify();
				 flag="yes";
			 }
			 catch(MonkeyTalkFailure e)
			 {
				 app.label("View OCO").tap();
				 flag="no";
			 }
			 String quantityOCO= new ValueRetriever().getQtyUpdateInputOco(app);
			 app.label("Add Stop / Limit").tap(new Mods.Builder().thinktime(5000).build());
			 app.label("Stop / Limits").verify();
			 app.input("2").enterText(quantityOCO);
		     String stop= new ValueRetriever().getStopPriceOco(app);
			 app.input("1").enterText(stop);
			 app.input("4").enterText(quantityOCO);
			 String limit= new ValueRetriever().getLimitPriceOco(app);
			 app.input("3").enterText(limit);
			 app.button("209").tap();
			 app.label("OK").tap(new Mods.Builder().thinktime(5000).build());
			 CheckElement element=new CheckElement();
			 results= element.checkLabel(app, "Order Stop Limit 1");
			 qty=new ValueRetriever().getQty(app);
			 app.label("Update OCO").tap();
			 String label= new GetConfirmationTicket().getConfirmationOcoOrder(app, goodUntil, qty);
			 app.button("OK").tap(new Mods.Builder().thinktime(5000).build());
			 
			 if(flag=="yes")
			 {
			 }
			 else
			 {
				 app.label("Back").tap(new Mods.Builder().thinktime(5000).build());
			 }
			 
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
			 		app.table("Empty list").selectIndex(1, new Mods.Builder().thinktime(5000).build());
			 		try
					 {
						 app.label("Cancel OCO").verify(new Mods.Builder().thinktime(5000).build());
					 }
					 catch(MonkeyTalkFailure e)
					 {
						 app.label("View OCO").tap();
					 }
					 app.scroller("#4").scroll(0, 60, new Mods.Builder().thinktime(5000).build() );
					 app.label("Order Stop Limit 1").tap(new Mods.Builder().thinktime(5000).build());
					 
					 String quantityStopInput=new ValueRetriever().getStopQtyInputOCO(app);
					 String priceStopInput= new ValueRetriever().getStopPriceInputOCO(app);
					 String quantityLimitInput= new ValueRetriever().getLimitQtyInputOCO(app);
					 String priceLimitInput= new ValueRetriever().getLimitPriceInputOCO(app);
					 
						if (quantityOCO.equals(quantityStopInput) && stop.equals(priceStopInput) && quantityOCO.equals(quantityLimitInput)&& limit.equals(priceLimitInput))
						{
							return "Pass";
						}
						else
						{
							return "Stop & Limit of the OCO Order was not saved correctly";
						}
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
