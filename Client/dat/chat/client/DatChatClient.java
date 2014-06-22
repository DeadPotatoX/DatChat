package dat.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class DatChatClient implements Runnable{
	private int port;
	private String server;
	private Socket socket;
	private String username = "Client";
	private DatChatClientGui gui = null;
	private boolean joined = false;
	private PrintWriter pw;
	private BufferedReader in;
	
	public DatChatClient(int port, String server) {
		this.port = port;
		this.server = server;
		init();
	}
	
	public DatChatClient(String serverDets) {
		this.port = Integer.parseInt(serverDets.substring(serverDets.indexOf(':') + 1));
		this.server = serverDets.substring(0, serverDets.indexOf(':'));
		init();
	}
	private void init() {
		
	}
	
	public int getPort() { return port; }
	public String getServer() { return server; }
	public boolean getJoined() { return joined; }
	public String getUsername() { return username; }
	public void setGui(DatChatClientGui aGui) { gui = aGui; }
	
	public void join(String username) {
		try {
			gui.addMessage("Setting up streams...");
			socket = new Socket(server, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(socket.getOutputStream(), true);
			gui.addMessage("Connected");
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.username = "Server";
		sendMessage("User " + username + " entered the chatroom!");
		joined = true;
		this.username = username;
	}
	
	public void recieveMessage(String message) {
		gui.addRawMessage(message);
	}
	
	public void sendMessage(String text) {
		String message = formatMessage(text);
		// send message
		System.out.print("Sending message: " + message);
		pw.print(message);
		pw.flush();
	}

	public static void main(String[] arguments) {
		// Get server details
		String serverDets = "localhost:1024";
		String selectedServerDets = JOptionPane.showInputDialog(null, "Enter the server address and port!  Use format: server:port", "[DatChat] Client Setup", JOptionPane.PLAIN_MESSAGE);
		if (!selectedServerDets.equals("") && !selectedServerDets.equals(null)) 
			serverDets = selectedServerDets;
		DatChatClient client = new DatChatClient(serverDets);
		DatChatClientGui gui = new DatChatClientGui(client);
		client.setGui(gui);
	}

	public void run() {
		try {
			while (true) {
				recieveMessage(in.readLine());
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public String formatMessage(String rawMessage) {
		int hour = Integer.parseInt(new SimpleDateFormat("HH").format(new Date()));
		String minute = new SimpleDateFormat("mm").format(new Date());
		String hourS;
		if (hour > 12) {
			hour-=12;
			hourS = hour + ":" + minute + " PM";
		} else 
			hourS = hour + ":" + minute + " AM";
		String message = "[" + hourS + "][" + username + "] " + rawMessage + "\n";
		return message;
	}
}
