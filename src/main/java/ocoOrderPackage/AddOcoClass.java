package ocoOrderPackage;

import java.math.BigDecimal;

import orderPackage.EntryNewOrderClass;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.CheckElement;
import common.ConnectClass;
import common.GetConfirmationTicket;
import common.ValueRetriever;
import fit.ColumnFixture;

public class AddOcoClass extends ColumnFixture{
	public String goodUntil;
	 String results=null;
	 public String cfdMarket;
	 String time;
	 
	 public String addOco() throws MonkeyTalkFailure{
			
		 Application app=new ConnectClass().connect();
		 try{
			 EntryNewOrderClass order=new EntryNewOrderClass();
			 order.cfdMarket=cfdMarket;
			 order.goodUntil=goodUntil;
			 order.entryNewOrder();
			 app.label(cfdMarket).tap(new Mods.Builder().thinktime(5000).build());
			 CheckElement element=new CheckElement();
			 results= element.checkLabel(app, "Add OCO");
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
			 String label= new GetConfirmationTicket().getConfirmationAddOcoOrder(app, goodUntil, qty);
			 app.button("OK").tap(new Mods.Builder().thinktime(5000).build());
			 app.label("Back").tap(new Mods.Builder().thinktime(5000).build());
			 
			 if(results==null)
			 {
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
					 app.label("Cancel OCO").verify();
				 }
				 catch(MonkeyTalkFailure e)
				 {
					 app.label("View OCO").tap();
				 }	
				String quantityInput=app.input("1").get();
				if (quantityInput.contains("."))
				{
					quantityInput=quantityInput.substring(0, quantityInput.indexOf("."));
				}
				if (quantityInput.contains(","))
				{
					quantityInput=quantityInput.replaceAll( "[^\\d]", "" );
				}
				long quantityInputentero= Long.parseLong(quantityInput);
				quantityInput=Long.toString(quantityInputentero);
				
				String priceInput=app.input("2").get();
				if (priceInput.contains("."))
				{
					priceInput=priceInput.substring(0, priceInput.indexOf("."));
				}
				if (priceInput.contains(","))
				{
					priceInput=priceInput.replaceAll( "[^\\d]", "" );
				}
				BigDecimal priceInputentero= new BigDecimal(new Float(priceInput));
				priceInput=priceInputentero.toString();
					
				if (quantityOCO.equals(quantityInput) && priceOCO.equals(priceInput))
				{
					return "Pass";
				}
				else
				{
					return "OCO order was not saved correctly";
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
