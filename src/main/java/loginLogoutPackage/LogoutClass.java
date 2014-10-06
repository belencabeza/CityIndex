package loginLogoutPackage;
import com.gorillalogic.monkeytalk.java.MonkeyTalkDriver;
import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import common.ConnectClass;

import fit.ColumnFixture;


public class LogoutClass extends ColumnFixture  {
	public MonkeyTalkDriver mt;
	 public Application app;
	
	 
	public String logout() {
		Application app=new ConnectClass().connect();
		  try{
				app.label("More").verify();
				app.tabBar().select("More");
				app.view("Log Off").tap();
			}
			catch(MonkeyTalkFailure ex){
				app.button("Back").tap();
				logout();
			}
		  
	 
		try
		  {
			  app.label("Login").verify();
			  return "pass";
		  }
		  catch(MonkeyTalkFailure ex)
		  {
			  return "fail";
		  }
	 }
}
