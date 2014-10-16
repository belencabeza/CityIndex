package common;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;


public class CheckElement{
		String results;
		
		public String checkLabel(Application app, String label){
			try 
			{
				app.label(label).verify();
				return results;
				
			}
			catch(MonkeyTalkFailure ex)
			{
				results= results + ", " + label;
				return results;
			}
		}
		
		public String checkButton(Application app, String button){
			try 
			{
				app.label(button).verify();
				return results;
				
			}
			catch(MonkeyTalkFailure ex)
			{
				results= results + ", " + button;
				return results;
			}
		}
		
		public String checkNotLabel(Application app, String label){
			try 
			{
				app.label(label).verifyNot();
				return results;
				
			}
			catch(MonkeyTalkFailure ex)
			{
				results= results + ", " + label;
				return results;
			}
		}

}