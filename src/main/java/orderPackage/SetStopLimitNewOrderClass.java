package orderPackage;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.CheckElement;
import common.ConnectClass;
import common.GetConfirmationTicket;
import common.ValueRetriever;
import fit.ColumnFixture;

public class SetStopLimitNewOrderClass extends ColumnFixture{
	public String goodUntil;
	 String results=null;
	 public String cfdMarket;
	 String timeYear;
	 String qtyString;
	 
	 public String setStopLimitNewOrder() throws MonkeyTalkFailure{
			
		 Application app=new ConnectClass().connect();
		 try{
			app.tabBar().select("Markets");
			app.button("Search").tap();
			app.input("_searchField").enterText(cfdMarket, "enter", new Mods.Builder().thinktime(5000).build());
			app.label(cfdMarket+"(2)").tap(new Mods.Builder().thinktime(5000).build());
			app.button("Order").tap(new Mods.Builder().thinktime(5000).build());
			String quantity= new ValueRetriever().getQuantityOrder(app);
			String price= new ValueRetriever().getPriceOrder(app);
			CheckElement element=new CheckElement();
			results= element.checkLabel(app, "Order");
			results= element.checkLabel(app, cfdMarket);
			results= element.checkButton(app, "Add OCO");
			results= element.checkButton(app, "Place Order");
			results= element.checkLabel(app, "Add Stop / Limit");
			results= element.checkNotLabel(app, "Bet Per");
			app.button("Sell").tap();
			app.input("1").enterText(quantity);
			app.input("2").enterText(price);
			app.label(goodUntil).tap();
			if (goodUntil.contains("Time"))
			{
				String time=new ValueRetriever().getTime(app);
				app.datePicker("_dpGoodUntil").enterDateAndTime(time);
				app.label("Set Time").tap();
				try
				{
					app.label("Error").verifyNot();
				}
				catch(MonkeyTalkFailure e)
				{
					app.label("OK").tap();
					//Day
					String timeDay=time.substring(8, 10);
					long timeNum= Long.parseLong(timeDay);
					//Month
					String timeMonth=time.substring(5,7);
					long timeMonthNum=Long.parseLong(timeMonth);
					//Year
					timeYear=time.substring(0,4);
					long timeYearNum=Long.parseLong(timeYear);
					timeNum=timeNum+2;
					if(timeNum>=31)
					{
						timeNum=2;
						timeMonthNum=timeMonthNum+1;
						if (timeMonthNum>12)
						{
							timeMonthNum=1;
							timeYearNum=timeYearNum+1;
							
						}
					}
					
					timeDay=Long.toString(timeNum);
					timeMonth=Long.toString(timeMonthNum);
					timeYear=Long.toString(timeYearNum);
					time=timeYear+"-"+timeMonth+"-"+timeDay+time.substring(10);
					app.datePicker("_dpGoodUntil").enterDateAndTime(time);
					return time;
				}
			}
			app.button("Buy").tap();
			app.button("Sell").tap();
			String qty=new ValueRetriever().getQuantityOrder(app);
			app.label("Add Stop / Limit").tap();
			app.label("Stop / Limits").verify();
			app.input("2").enterText(quantity);
			String stop= new ValueRetriever().getStopPrice(app);
			app.input("1").enterText(stop);
			app.input("4").enterText(quantity);
			String limit= new ValueRetriever().getLimitPrice(app);
			app.input("3").enterText(limit);
			app.button("209").tap();
			app.scroller("#4").scroll(0, 60, new Mods.Builder().thinktime(5000).build() );
			results= element.checkLabel(app, "Order Stop Limit 1");
			app.button("211").tap();
			long quantityentero= Long.parseLong(qty);
			long add=quantityentero-9;
			if (add==0)
			{
				qtyString=Long.toString(quantityentero-5);
			}
			else
			{
				qtyString=Long.toString(quantityentero-5+add-1);
			}
			
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
					app.tabBar().select("Positions");
					app.label("Orders").tap();
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
