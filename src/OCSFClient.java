import java.io.IOException;
import java.util.Scanner;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.lloseng.ocsf.client.AbstractClient;

public class OCSFClient extends AbstractClient {

    private String msg;

    public OCSFClient(String host, int port) {
	super(host, port);
    }

    public String getMsg() {
	return msg;
    }

    @Override
    protected void handleMessageFromServer(Object obj) {
	msg = (String) obj;
	System.out.println("---> " + msg);
	autoEval();
    }

    private void autoEval() {
	if (msg.contains("What is ")) {
	    try {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		String formula = msg.substring(8, msg.indexOf("?"));
		String answer = String.valueOf(engine.eval(formula));
		System.out.println("Sending..." + answer);
		this.sendToServer(answer);
	    } catch (ScriptException e) {
		e.printStackTrace();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    @Override
    protected void connectionClosed() {
	super.connectionClosed();
	System.out.println("Disconnected from " + this.getHost() + ":" + this.getPort());
    }

    @Override
    protected void connectionEstablished() {
	super.connectionEstablished();
	System.out.printf("Successfully connected to: %s on PORT: %d\n", this.getHost(), this.getPort());
    }

}
