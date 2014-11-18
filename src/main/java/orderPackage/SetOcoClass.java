package orderPackage;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.CheckElement;
import common.ConnectClass;
import common.GetConfirmationTicket;
import common.ValueRetriever;
import fit.ColumnFixture;

public class SetOcoClass  extends ColumnFixture{
	public String goodUntil;
	 String results=null;
	 public String cfdMarket;
	 String time;
	 
	 public String setOco() throws MonkeyTalkFailure{
			
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
			    time=new ValueRetriever().getTime(app);
				app.datePicker("_dpGoodUntil").enterDateAndTime(time);
				app.label("Set Time").tap();
				try
				{
					app.label("Error").verifyNot();
				}
				catch(MonkeyTalkFailure e)
				{
					app.label("OK").tap();
					long timeNum= Long.parseLong(time);
					timeNum=timeNum+2;
					String timeDay=Long.toString(timeNum);
					time=time.substring(0,8)+timeDay+time.substring(10);
					app.datePicker("_dpGoodUntil").enterDateAndTime(time);
					app.label("Set Time").tap();
				}
			}
			String qty=new ValueRetriever().getQty(app);
			app.label("Add OCO").tap();
			String quantityOCO= new ValueRetriever().getQuantityOCO(app);
			String priceOCO= new ValueRetriever().getPriceOCO(app);
			app.input("1").enterText(quantityOCO);
			app.input("2").enterText(priceOCO);
			if (goodUntil.contains("Time"))
			{
				app.label(time).verify();
			}
			else
			{
				app.label(goodUntil).verify();
			}
			app.label("Confirm OCO").tap();
			app.label("Place Order").tap();
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
