package orderPackage;

import internetProblems.InternetConnection;
import internetProblems.Problem;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.CheckElement;
import common.ConnectClass;
import common.GetConfirmationTicket;
import common.ValueRetriever;
import fit.ColumnFixture;

public class SetStopLimitActiveOrderClass extends ColumnFixture{
	
	public String goodUntil;
	 String results=null;
	 public String cfdMarket;
	 String timeYear;
	 
	 public String setStopLimitActiveOrder() throws MonkeyTalkFailure{
			
		 Application app=new ConnectClass().connect();
		 try{
			 EntryNewOrderClass order=new EntryNewOrderClass();
			 order.cfdMarket=cfdMarket;
			 order.goodUntil=goodUntil;
			 order.entryNewOrder();
			 app.label(cfdMarket).tap(new Mods.Builder().thinktime(5000).build());
			 CheckElement element=new CheckElement();
			 results= element.checkLabel(app, "Quantity");
			 results= element.checkLabel(app, "Price");
			 results= element.checkLabel(app, "Good Until");
			 results= element.checkLabel(app, "Update Order");
			 results= element.checkNotLabel(app, "Positions");
			 String qty=new ValueRetriever().getQuantityOrder(app);
			 String quantity= app.input("1").get();
			 app.label("Add Stop / Limit").tap();
			 app.label("Stop / Limits").verify();
			 app.input("2").enterText(quantity);
			 String stop= new ValueRetriever().getStopPrice(app);
			 app.input("1").enterText(stop);
			 app.input("4").enterText(quantity);
			 String limit= new ValueRetriever().getLimitPrice(app);
			 app.input("3").enterText(limit);
			 app.button("209").tap();
			 app.label("OK").tap(new Mods.Builder().thinktime(5000).build());
			 app.scroller("#4").scroll(0, 60, new Mods.Builder().thinktime(5000).build() );
			 results= element.checkLabel(app, "Order Stop Limit 1");
			 app.label("Update Order").tap();
			 long quantityentero= Long.parseLong(qty);
			 String qtyString=Long.toString(quantityentero-5);
			 String label= new GetConfirmationTicket().getConfirmationOrder(app, goodUntil, qtyString);
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
						app.table("Empty list").selectIndex(1, new Mods.Builder().thinktime(5000).build());
						app.scroller("#4").scroll(0, 60, new Mods.Builder().thinktime(5000).build() );
						app.label("Order Stop Limit 1").tap(new Mods.Builder().thinktime(5000).build());
						String quantityStopInput=new ValueRetriever().getStopQtyInputOrder(app);
						String priceStopInput= new ValueRetriever().getStopPriceInputOrder(app);
						String quantityLimitInput= new ValueRetriever().getLimitQtyInputOrder(app);
						String priceLimitInput= new ValueRetriever().getLimitPriceInputOrder(app);
						 
							if (quantity.equals(quantityStopInput) && stop.equals(priceStopInput) && quantity.equals(quantityLimitInput)&& limit.equals(priceLimitInput))
							{
								return "Pass";
							}
							else
							{
								return "Stop & Limit of the Order was not saved correctly";
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
