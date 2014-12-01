package orderPackage;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.CheckElement;
import common.ConnectClass;
import common.GetConfirmationTicket;
import fit.ColumnFixture;

public class DeleteOrderFromEditClass extends ColumnFixture{
	String results=null;
	public String cfdMarket;
	public String goodUntil;
	
	public String deleteOrderFromEdit() throws MonkeyTalkFailure{
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
			 app.scroller("#4").scroll(0, 60);
			 app.label("Delete Order").tap();
			 app.label("Are you sure you want to delete this order?").verify();
			 app.label("Delete").tap();
			 String label=new GetConfirmationTicket().getDeleteOrderFromEdit(app);
			 app.label("OK").tap();
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
				else if(!(label.contains("Success: Your order has been deleted") || (label==null)))
				{
					return label+results;
				}
					else
					{
						return "Pass"+results;
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
