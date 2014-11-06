package orderPackage;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import com.gorillalogic.monkeytalk.java.utils.Mods;

import common.CheckElement;
import common.ConnectClass;
import common.GetConfirmationTicket;
import fit.ColumnFixture;

public class DeleteOrderClass extends ColumnFixture {
	String results=null;
	public String cfdMarket;
	public String goodUntil;
	
	 public String deleteOrder() throws MonkeyTalkFailure{
		 Application app=new ConnectClass().connect();
		 try{
			 EntryNewOrderClass order=new EntryNewOrderClass();
			 order.cfdMarket=cfdMarket;
			 order.goodUntil=goodUntil;
			 order.entryNewOrder();
			 app.label("Delete").tap(new Mods.Builder().thinktime(5000).build());
			 app.label("Are you sure you want to delete this order?").verify();
			 CheckElement element=new CheckElement();
			 results= element.checkLabel(app, "Order");
			 app.label("Delete(7)").tap();
			 String label=new GetConfirmationTicket().getDeleteOrder(app);
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
