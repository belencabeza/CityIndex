package ocoOrderPackage;

import java.math.BigDecimal;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.CheckElement;
import common.ConnectClass;
import common.GetConfirmationTicket;
import common.ValueRetriever;
import fit.ColumnFixture;

public class SetOcoStopLimitClass extends ColumnFixture{
	public String goodUntil;
	 String results=null;
	 public String cfdMarket;
	 String time;
	 String timeYear;
	 String timeSet;
	 
	 public String setOcoStopLimit() throws MonkeyTalkFailure{
			
		 Application app=new ConnectClass().connect();
		 try{
			app.tabBar().select("Markets");
			app.button("Search").tap();
			app.input("_searchField").enterText(cfdMarket, "enter", new Mods.Builder().thinktime(10000).build());
			app.label(cfdMarket+"(2)").tap(new Mods.Builder().thinktime(10000).build());
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
					app.label("Set Time").tap();
					timeSet=new ValueRetriever().getTimeSet(app, timeYear);
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
				app.label(timeSet).verify();
			}
			else
			{
				app.label(goodUntil).verify();
			}
			app.button("Sell").tap();
			app.button("Buy").tap();
			app.label("Add Stop / Limit").tap();
			app.label("Stop / Limits").verify();
			app.input("2").enterText(quantityOCO);
			String stop= new ValueRetriever().getStopPriceOco(app);
			app.input("1").enterText(stop);
			app.input("4").enterText(quantityOCO);
			String limit= new ValueRetriever().getLimitPriceOco(app);
			app.input("3").enterText(limit);
			app.button("209").tap();
			results= element.checkLabel(app, "Order Stop Limit 1");
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
				app.table("Empty list").selectIndex(2, new Mods.Builder().thinktime(5000).build());
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
				 String quantityStopInput=app.input("2").get(new Mods.Builder().thinktime(5000).build());
					if (quantityStopInput.contains("."))
					{
						quantityStopInput=quantityStopInput.substring(0, quantityStopInput.indexOf("."));
					}
					if (quantityStopInput.contains(","))
					{
						quantityStopInput=quantityStopInput.replaceAll( "[^\\d]", "" );
					}
					long quantityStopInputentero= Long.parseLong(quantityStopInput);
					quantityStopInput=Long.toString(quantityStopInputentero);
					
					String priceStopInput=app.input("1").get();
					if (priceStopInput.contains("."))
					{
						priceStopInput=priceStopInput.substring(0, priceStopInput.indexOf("."));
					}
					if (priceStopInput.contains(","))
					{
						priceStopInput=priceStopInput.replaceAll( "[^\\d]", "" );
					}
					BigDecimal priceStopInputentero= new BigDecimal(new Float(priceStopInput));
					priceStopInput=priceStopInputentero.toString();
					
					 String quantityLimitInput=app.input("4").get();
						if (quantityLimitInput.contains("."))
						{
							quantityLimitInput=quantityLimitInput.substring(0, quantityLimitInput.indexOf("."));
						}
						if (quantityLimitInput.contains(","))
						{
							quantityLimitInput=quantityLimitInput.replaceAll( "[^\\d]", "" );
						}
						long quantityLimitInputentero= Long.parseLong(quantityLimitInput);
						quantityLimitInput=Long.toString(quantityLimitInputentero);
						
						String priceLimitInput=app.input("3").get();
						if (priceLimitInput.contains("."))
						{
							priceLimitInput=priceLimitInput.substring(0, priceLimitInput.indexOf("."));
						}
						if (priceLimitInput.contains(","))
						{
							priceLimitInput=priceLimitInput.replaceAll( "[^\\d]", "" );
						}
						BigDecimal priceLimitInputentero= new BigDecimal(new Float(priceLimitInput));
						priceLimitInput=priceLimitInputentero.toString();
					
					if (quantityOCO.equals(quantityStopInput) && stop.equals(priceStopInput) && quantityOCO.equals(quantityLimitInput)&& limit.equals(priceLimitInput))
					{
						return "Pass";
					}
					else
					{
						return "OCO order was not edited correctly";
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
