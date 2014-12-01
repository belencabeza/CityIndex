package orderPackage;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.CheckElement;
import common.ConnectClass;
import common.GetConfirmationTicket;
import common.ValueRetriever;
import fit.ColumnFixture;

//CIPH-25 : Entry New Order
//CIPH-31 : Day End
//CIPH-32 : Time
public class EntryNewOrderClass extends ColumnFixture {
	 public String goodUntil;
	 String results=null;
	 public String cfdMarket;
	 public String dftMarket;
	 String timeYear;
	 
	 public String entryNewOrder() throws MonkeyTalkFailure{
			
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
			String qty=new ValueRetriever().getQty(app);
			app.button("211").tap();
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
					app.tabBar().select("Positions");
					app.label("Orders").tap();
					app.table("Empty list").selectIndex(1, new Mods.Builder().thinktime(5000).build());
					if (goodUntil.contains("Day End"))
					{
						app.view("#68").verify();
						app.label("Back").tap();
						return "Pass"+results;
					}
					else if(goodUntil.contains("Cancelled"))
					{
						app.view("#67").verify();
						app.label("Back").tap();
						return "Pass"+results;
					}
					else
					{
						app.view("#69").verify();
						app.label("Back").tap();
						return "Pass"+results;
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
