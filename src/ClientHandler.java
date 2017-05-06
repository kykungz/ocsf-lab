import java.io.IOException;
import java.util.Scanner;

public class ClientHandler {
    private OCSFClient client;
    private String ip;
    private int port;
    private String username;
    private static Scanner sc = new Scanner(System.in);
    private static int i = 0;
    private static String jim = "10.2.12.83";

    public static void main(String[] args) {
	for (i = 0; i < 100; i++) {
	    new Thread(() -> {
		new ClientHandler(jim, 5001, "jacky").run();
	    }).start();
	}
	// new ClientHandler("", 5001, "jacky").run();
    }

    public ClientHandler(String ip, int port, String username) {
	this.ip = ip;
	this.port = port;
	this.username = username;
    }

    public void run() {
	while (true) {
	    client = new OCSFClient(ip, port);
	    try {
		client.openConnection();
		client.sendToServer("login " + username);
		while (client.isConnected()) {
		    String input = sc.nextLine();
		    client.sendToServer(input);
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }
}
