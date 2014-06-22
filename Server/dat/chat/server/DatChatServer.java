package dat.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class DatChatServer {
	int socketPort = 1024;
	ServerSocket server;
	String serverName = "localhost";
		
	private ArrayList<Client> clients = new ArrayList<Client>();
	
	public DatChatServer(int port) {
		System.out.println("Running...");
		init();
		socketPort = port;
		run();
	}
	
	private void init() {
		try {
			server = new ServerSocket(socketPort, 1000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			// connect
			Client client = new Client(server.accept());
			clients.add(client);
			client.sendMessage("return-username");
			while (true) {
				String message = "";
				for (int i = 0; i < clients.size(); i++) {
					Client c = clients.get(i);
					String cMessage = c.recieve();
					if (cMessage != null && cMessage != "") { 
						message += cMessage;
						System.out.println("Found message " + cMessage + " from client " + i);
					}
				}
				for (int i = 0; i < clients.size(); i++) {
					Client c = clients.get(i);
					c.sendMessage(message);
				}
				if (message != "") System.out.println("Handled message(s) \n" + message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] serverArgs) {
		int portNo = 1024;
		String sPortNo = JOptionPane.showInputDialog(null, "Enter server Port No - Select port to open", "[DatChat] Server Setup", JOptionPane.PLAIN_MESSAGE);
		portNo = Integer.parseInt(sPortNo);
		new DatChatServer(portNo);
	}
	
	class Client {
		BufferedReader in;
		PrintWriter pw;
		
		Client(Socket clientSocket) throws IOException {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			pw = new PrintWriter(clientSocket.getOutputStream(), true);	
		}
		
		public void sendMessage(String message) {
			pw.print(message);
			pw.flush();
		}
		
		public String recieve() throws IOException {
			return in.readLine();
		}
		
		public boolean message() throws IOException {
			if (in.)
			return false;
		}
	}
}
