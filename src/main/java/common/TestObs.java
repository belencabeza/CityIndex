package common;

import com.gorillalogic.monkeytalk.java.api.Application;
import com.gorillalogic.monkeytalk.java.error.MonkeyTalkFailure;

public class TestObs implements Runnable {
	Application app = new ConnectClass().connect();

	public void run() {
		while (true) {
			try {
				app.label("Latency Warning").verify();
				app.label("OK").tap();
			} catch (MonkeyTalkFailure e) {
				try{
					app.label("Connection Problem").verify();
					app.label("OK").tap();
				}catch (MonkeyTalkFailure b) {
					try{
						app.label("Messaging Error").verify();
						app.label("OK").tap();
					}catch (MonkeyTalkFailure a) {
						try{
							app.label("Login Failed").verify();
							app.label("OK").tap();
						}catch (MonkeyTalkFailure d) {
							
						}
					}
				}
			}
		}
	}
}