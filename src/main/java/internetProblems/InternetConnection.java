package internetProblems;


import com.gorillalogic.monkeytalk.java.api.Application;

import common.ConnectClass;

interface Observer {
    public void update();
}


public class InternetConnection implements Observer {
	Application app=new ConnectClass().connect();
	
	public void update(){
		app.label("OK").tap();
	}

}
