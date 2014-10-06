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

}
