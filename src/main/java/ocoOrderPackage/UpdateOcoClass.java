package ocoOrderPackage;

import java.math.BigDecimal;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.ConnectClass;
import common.GetConfirmationTicket;
import common.ValueRetriever;
import fit.ColumnFixture;

public class UpdateOcoClass extends ColumnFixture {
	 public String goodUntil;
	 String results=null;
	 public String cfdMarket;
	 String time;
	 String flag;
	 String qty;
	 
	 public String updateOco() throws MonkeyTalkFailure{
			
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
			 String quantity= new ValueRetriever().getQuantityInputOco(app);
			 String price= new ValueRetriever().getPriceInputOco(app);
			 app.input("1").enterText(quantity);
			 app.input("2").enterText(price);
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
					
					if (quantity.equals(quantityInput) && price.equals(priceInput))
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
