package orderPackage;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.CheckElement;
import common.ConnectClass;
import common.GetConfirmationTicket;
import common.ValueRetriever;
import fit.ColumnFixture;

public class EditOrderClass extends ColumnFixture{
	String results=null;
	public String cfdMarket;
	public String goodUntil;
	public String goodUntilNew;
	
	 public String editOrder() throws MonkeyTalkFailure{
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
			 String quantity= new ValueRetriever().getQuantityInputOrder(app);
			 String price= new ValueRetriever().getPriceInputOrder(app);
			 app.input("1").enterText(quantity);
			 app.input("2").enterText(price);
			 app.label(goodUntilNew).tap();
			 app.label("Update Order").tap();
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
