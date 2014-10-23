package common;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.utils.Mods;

public class GetConfirmationTicket {
	
	public String getConfirmationTicket(Application app){
		String resolution=app.device().get("resolution");
		String label;
		if (resolution.equals("640x960"))
		{
			label=app.label("#14").get(new Mods.Builder().thinktime(5000).build());
		}
		else
		{
			label=app.label("#15").get(new Mods.Builder().thinktime(5000).build());
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
			label=app.label("#22").get(new Mods.Builder().thinktime(5000).build());
		}
		else
		{
			label=app.label("#15").get(new Mods.Builder().thinktime(5000).build());
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
			label=app.label("#15").get(new Mods.Builder().thinktime(5000).build());
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
			label=app.label("#15").get(new Mods.Builder().thinktime(5000).build());
		}
		return label;
	}
	
	public String getCalculteMargin(Application app){
		String resolution=app.device().get("resolution");
		String label;
		if (resolution.equals("640x960"))
		{
			label=app.label("#12").get(new Mods.Builder().thinktime(5000).build());
		}
		else
		{
			label=app.label("#15").get(new Mods.Builder().thinktime(5000).build());
		}
		return label;
	}
	
	public String getConfirmationOrder(Application app){
		String resolution=app.device().get("resolution");
		String label;
		if (resolution.equals("640x960"))
		{
			label=app.label("#23").get(new Mods.Builder().thinktime(5000).build());
		}
		else
		{
			label=app.label("#15").get(new Mods.Builder().thinktime(5000).build());
		}
		return label;
	}
}
