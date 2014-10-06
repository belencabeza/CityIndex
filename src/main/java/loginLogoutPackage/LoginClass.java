package loginLogoutPackage;
import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;
import common.ConnectClass;

import fit.ColumnFixture;


public class LoginClass extends ColumnFixture {
	
	 public String user;
	 public String password;
	
	 public String login() {
		Application app=new ConnectClass().connect();
				  
		  try
		  {
			  app.label("Markets").verify();
			  app.label("Login").tap();
		  }
		  catch(MonkeyTalkFailure ex)
		  {
			  System.out.println("Login Page");
		  }
		  app.input("701").enterText(user);
		  app.input("702").enterText(password);
		  app.button("703").tap();
		  try {
				app.label("Cancel").verify();
				app.label("Cancel").tap();			
			}
			catch(MonkeyTalkFailure ex){
				try{
					app.label("Not Now").verify();
					app.label("Not Now").tap();
				}
				catch(MonkeyTalkFailure ex2){
				}
			}
			finally{
				try{
					app.button("Continue").verify();
					app.button("Continue").tap();	
				}
				catch(MonkeyTalkFailure ex)
				{	
				}
				try {
					app.label("Close").verify();
					app.label("Close").tap();
				}
				catch(MonkeyTalkFailure ex){
				}
				try{
					app.label("Messages").verify();
					app.label("Done").tap();
				}
				catch(MonkeyTalkFailure ex){
					
				}
			}
		  try
		  {
			  app.label("Login").verify();
			  return "fail";
		  }
		  catch(MonkeyTalkFailure ex2)
		  {
			  return "pass"; 
		  }
	 
	  }
	
}
