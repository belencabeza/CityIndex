package common;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.utils.Mods;

public class GetConfirmationTicket {
	
	public String getConfirmationTicket(Application app, String qty){
		String resolution=app.device().get("resolution");
		String label;
		if (resolution.equals("640x960"))
		{
			int num=Integer.parseInt(qty);
			num=num+4;
			label=app.label("#"+num).get(new Mods.Builder().thinktime(5000).build());
		}
		else
		{
			int num=Integer.parseInt(qty);
			num=num+5;
			label=app.label("#"+num).get(new Mods.Builder().thinktime(5000).build());
		}
		return label;
	}
	
	public String getConfirmationTicketAutorollover(Application app)
	{
		String resolution=app.device().get("resolution");
		String label;
		if (resolution.equals("640x960"))
		{
			label=app.label("#15").get(new Mods.Builder().thinktime(5000).build());
		}
		else
		{
			label=app.label("#16").get(new Mods.Builder().thinktime(5000).build());
		}
		return label;
	}
	
	public String getConfirmationTicketClose(Application app){
		String resolution=app.device().get("resolution");
		String label;
		if (resolution.equals("640x960"))
		{
			label=app.label("#22").get(new Mods.Builder().thinktime(15000).build());
		}
		else
		{
			label=app.label("#23").get(new Mods.Builder().thinktime(5000).build());
		}
		return label;
	}
	
	public String getConfirmationTicketClosingOrder(Application app){
		String resolution=app.device().get("resolution");
		String label;
		if (resolution.equals("640x960"))
		{
			label=app.label("#24").get(new Mods.Builder().thinktime(5000).build());
		}
		else
		{
			label=app.label("#25").get(new Mods.Builder().thinktime(5000).build());
		}
		return label;
	}
	
	public String getConfirmationDelete(Application app){
		String resolution=app.device().get("resolution");
		String label;
		if (resolution.equals("640x960"))
		{
			label=app.label("#31").get(new Mods.Builder().thinktime(5000).build());
		}
		else
		{
			label=app.label("#32").get(new Mods.Builder().thinktime(5000).build());
		}
		return label;
	}
	
	public String getCalculteMargin(Application app, String qty){
		String resolution=app.device().get("resolution");
		String label;
		if (resolution.equals("640x960"))
		{
			int num=Integer.parseInt(qty);
			num=num+1;
			label=app.label("#"+num).get(new Mods.Builder().thinktime(5000).build());
		}
		else
		{
			int num=Integer.parseInt(qty);
			num=num+2;
			label=app.label("#"+num).get(new Mods.Builder().thinktime(5000).build());
		}
		return label;
	}
	
	public String getConfirmationOrder(Application app, String goodUntil, String qty){
		String resolution=app.device().get("resolution");
		String label;
		if (resolution.equals("640x960"))
		{
			if (goodUntil.contains("Time"))
			{
				
				int num=Integer.parseInt(qty);
				num=num+13;
				label=app.label("#"+num).get(new Mods.Builder().thinktime(5000).build());
				
			}
			else
			{
					int num=Integer.parseInt(qty);
					num=num+14;
					label=app.label("#"+num).get(new Mods.Builder().thinktime(5000).build());
			}
		}
		else
		{
			if (goodUntil.contains("Time"))
			{
				label=app.label("#25").get(new Mods.Builder().thinktime(5000).build());
			}
			else
			{
				label=app.label("#24").get(new Mods.Builder().thinktime(5000).build());
			}
		}
		return label;
	}
	public String getDeleteOrder(Application app){
		String resolution=app.device().get("resolution");
		String label;
		if (resolution.equals("640x960"))
		{
			label=app.label("#63").get(new Mods.Builder().thinktime(5000).build());
		}
		else
		{
			label=app.label("#62").get(new Mods.Builder().thinktime(5000).build());
		}
		return label;
	}
	
	public String getDeleteOrderFromEdit(Application app){
		String resolution=app.device().get("resolution");
		String label;
		if (resolution.equals("640x960"))
		{
			label=app.label("#20").get(new Mods.Builder().thinktime(5000).build());
		}
		else
		{
			label=app.label("#19").get(new Mods.Builder().thinktime(5000).build());
		}
		return label;
	}
}
