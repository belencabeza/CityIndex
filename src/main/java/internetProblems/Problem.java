package internetProblems;

import java.util.ArrayList;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;

import common.ConnectClass;


interface Subject {
    public void registerObserver(Observer observer);

    public void removeObserver(Observer observer);

    public void notifyObservers();
}


public class Problem implements Subject{
	
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	Application app=new ConnectClass().connect();
	
	public void verifyAlert(){
		try
		{
			String latency=app.label("OK").get();
			if (latency==null)
			{
			}
			else
			{
				notifyObservers();
			}
		}
		catch(MonkeyTalkFailure e)
		{
			
		}
	}
	
    public void registerObserver(Observer observer) {
           observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    
    public void notifyObservers() {
        for (Observer ob : observers) {
        	ob.update();
        }
    }


}
