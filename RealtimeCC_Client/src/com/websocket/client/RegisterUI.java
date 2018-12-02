package com.websocket.client;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.websocket.EncodeException;

public class RegisterUI implements ActionListener {
	WsClientLogin client;
	JButton lbutton;
	JButton rbutton;
	static boolean isHere=false;
	JButton backButton;
	static JFrame jframe;
	static JTextArea userText;
	JPasswordField passwordValue;
	final JComboBox<String> cb;

	public boolean loginResult() {

		User user = new User();
		user.setUsername(userText.getText());
	//	String hash= sha256((String.valueOf(passwordValue.getPassword())));
		user.setPassword(String.valueOf(passwordValue.getPassword()));
		user.setType(String.valueOf(cb.getSelectedItem()));
		//System.out.println(hash);
		try {
			WsClientLogin.session1.getBasicRemote().sendObject(user);
		} catch (IOException | EncodeException e) {

			e.printStackTrace();
		}
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("register result:"+WsClientLogin.loginResult);
		return WsClientLogin.loginResult;

	}

	public RegisterUI() {
		isHere=true;
		Login.jframe.setVisible(false);

		jframe = new JFrame();

		JLabel demo = new JLabel("Registration for RCC Platform", SwingConstants.CENTER);

		rbutton = new JButton("Register");

		JLabel userLabel = new JLabel();
		userLabel.setText("User Name");
		JLabel passLabel = new JLabel();
		passLabel.setText("Password");

		passwordValue = new JPasswordField();
		userLabel.setText("User Name");

		JLabel userType = new JLabel();
		userType.setText("Type");
		String[] choices = { "Moderator", "Student" };
		cb = new JComboBox<String>(choices);

		cb.setVisible(true);

		userText = new JTextArea();
		userText.setWrapStyleWord(true);
		jframe.setSize(500, 400);

		jframe.setVisible(true);

		JPanel panel = new JPanel();

		panel.setSize(100, 300);
		GridLayout layout = new GridLayout(4, 2);
		layout.setHgap(10);
		layout.setVgap(5);

		panel.setLayout(layout);

		panel.add(userLabel);
		panel.add(userText);
		panel.add(passLabel);
		panel.add(passwordValue);
		panel.add(userType);
		panel.add(cb);
		panel.add(rbutton);
		// rbutton.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JPanel controlPanel = new JPanel();
		JPanel headPanel = new JPanel();
		controlPanel.add(panel);
		jframe.setLayout(new GridLayout(2, 0));
		// jframe.add(headPanel);
		jframe.add(demo);
		jframe.add(controlPanel);
		// jframe.add(rbutton);

		rbutton.addActionListener(this);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void registerUser() throws SQLException {

		/*
		 * DatabaseUser dbuser = new DatabaseUser(); boolean check =
		 * dbuser.newUser(user);
		 */
		try {
			client = new WsClientLogin();
			try {
				client.createConnection("ws://localhost:8080/RoshanServer/websocketendpoint", "login");
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				System.out.println("cant connect to server");
				// e1.printStackTrace();
			}
		} catch (IOException s) {
			// TODO Auto-generated catch block
			s.printStackTrace();
		}

		if (loginResult() == true) {

	

			DashboardUI dashboard = new DashboardUI();

			dashboard.prepareMenubar();
		
			
			
		} else {

			JOptionPane.showMessageDialog(jframe, "New User register failed! Choose different Username and try again!");

		}


	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == rbutton) {

			try {
				registerUser();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			} 

		

	}
	
	

}
