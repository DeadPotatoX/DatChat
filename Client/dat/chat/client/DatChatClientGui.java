package dat.chat.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DatChatClientGui extends JFrame {
	private static final long serialVersionUID = 1L;

	private DatChatClient client;
	
	public DatChatClientGui(DatChatClient client) {
		super("DatChat - Client");
		this.client = client;
		init();
	}
	
	// Center (centre)
	private JTextArea textBox = new JTextArea("Started DatChat Version 1.0\n", 25, 30);
	private JScrollPane scroll = new JScrollPane(textBox);
	
	// North (top)
	private JLabel usernameLabel = new JLabel("Your Username:");
	private JTextField usernameF =  new JTextField("Anonymous");
	private JButton joinButton = new JButton("Enter Chatroom!");
	
	// South (bottom)
	private JTextField textBar = new JTextField("Enter your message here...", 26);
	private JButton submit = new JButton("Send");
	
	private void init() {
		joinButton.addActionListener(new DatActionListener());
		usernameF.addActionListener(new DatActionListener());
		submit.addActionListener(new DatActionListener());
		textBar.addActionListener(new DatActionListener());
		
		textBox.setEditable(false);
		textBar.setEditable(false);
		submit.setEnabled(false);
		
		JPanel center = center();
		JPanel north = north();
		JPanel east = east();
		JPanel south = south();
		JPanel west = west();
		
		setLayout(new BorderLayout());
		
		add(center, BorderLayout.CENTER);
		add(north, BorderLayout.NORTH);
		add(east, BorderLayout.EAST);
		add(south, BorderLayout.SOUTH);
		add(west, BorderLayout.WEST);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);		
	}
	
	private JPanel center() {
		JPanel center = new JPanel();
		center.add(scroll);
		return center;
	}
	private JPanel north() {
		JPanel north = new JPanel();
		
		north.setLayout(new BorderLayout());
		
		JPanel nWest = new JPanel();
		nWest.add(usernameLabel);
		JPanel nCenter = new JPanel();
		nCenter.add(usernameF);
		JPanel nEast = new JPanel();
		nEast.add(joinButton);
		
		north.add(nWest, BorderLayout.WEST);
		north.add(nCenter);
		north.add(nEast, BorderLayout.EAST);
		return north;
	}
	private JPanel east() {
		JPanel east = new JPanel();
		
		return east;
	}
	private JPanel south() {
		JPanel south = new JPanel();
		
		south.setLayout(new BorderLayout());
		
		JPanel sWest = new JPanel();
		sWest.add(textBar);
		JPanel sEast = new JPanel();
		sEast.add(submit);
		
		south.add(sWest, BorderLayout.WEST);
		south.add(sEast, BorderLayout.EAST);
		
		return south;
	}
	private JPanel west() {
		JPanel west = new JPanel();
		return west;
	}
	
	private void join(String name) {
		addMessage("Connecting to '" + client.getServer() + "' via port " + client.getPort() + ".");
		usernameF.setEditable(false);
		joinButton.setEnabled(false);

		textBar.setEditable(true);
		submit.setEnabled(true);
		client.join(name);
	}
	
	public void addMessage(String text) {
		String message = client.formatMessage(text);
		textBox.append(message);
	}
	
	public void addRawMessage(String message) {
		textBox.append(message);
	}
	
	class DatActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(joinButton) || e.getSource().equals(usernameF) && !client.getJoined()) {
				join(usernameF.getText());
			} else if (e.getSource().equals(submit) || e.getSource().equals(textBar)) {
				client.sendMessage(textBar.getText());
				textBar.setText("");
			}
		}
	}
}
