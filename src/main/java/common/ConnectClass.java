package common;
import java.io.File;

import com.gorillalogic.monkeytalk.java.MonkeyTalkDriver;
import com.gorillalogic.monkeytalk.java.api.Application;

import fitlibrary.SetUpFixture;


public class ConnectClass extends SetUpFixture{
	
	public MonkeyTalkDriver mt;
	public Application app;
	
	public Application connect(){
	mt = new MonkeyTalkDriver(new File("."), "iOS", "192.168.7.73");
	mt.setThinktime(500);
	mt.setTimeout(5000);
	app = mt.app();
	return app;
	}

}
