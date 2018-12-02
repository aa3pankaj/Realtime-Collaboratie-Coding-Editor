package com.websocket.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.SQLException;

import javax.swing.*;
import javax.websocket.EncodeException;

public class Login implements ActionListener {

	Dialog dialog;
	JButton lbutton;
	JButton rbutton;
	static boolean isHere = false;
	static JFrame jframe;
	static JTextArea userText;
	JPasswordField passwordValue;

	public boolean loginResult() {

		User user = new User();
		user.setUsername(userText.getText());
		user.setPassword(String.valueOf(passwordValue.getPassword()));
		System.out.println(String.valueOf(passwordValue.getPassword()));
		try {
			WsClientLogin.session1.getBasicRemote().sendObject(user);
		} catch (IOException | EncodeException e) {

			e.printStackTrace();
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(WsClientLogin.loginResult);
		return WsClientLogin.loginResult;

	}

	public Login() {
		isHere = true;

		dialog = new JDialog();
		JLabel label = new JLabel("awesome code!");
		dialog.setLocationRelativeTo(jframe);
		dialog.setTitle("Please Wait...");
		dialog.add(label);
		dialog.pack();
		dialog.setSize(new Dimension(50, 60));

		jframe = new JFrame();

		JLabel demo = new JLabel("RealTime CCP", SwingConstants.CENTER);

		lbutton = new JButton("Login");
		rbutton = new JButton("Register");
		JLabel userLabel = new JLabel();
		userLabel.setText("User Name");
		JLabel passLabel = new JLabel();
		passLabel.setText("Password");
		userLabel.setText("User Name");
		passwordValue = new JPasswordField();
		userLabel.setText("User Name");

		userText = new JTextArea();

		jframe.setSize(500, 400);

		jframe.setVisible(true);

		JPanel panel = new JPanel();

		panel.setSize(100, 150);
		GridLayout layout = new GridLayout(3, 2);
		layout.setHgap(10);
		layout.setVgap(10);

		panel.setLayout(layout);

		panel.add(userLabel);
		panel.add(userText);
		panel.add(passLabel);
		panel.add(passwordValue);
		panel.add(lbutton);
		panel.add(rbutton);
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		controlPanel.add(panel);

		jframe.setLayout(new GridLayout(3, 1));
		jframe.add(demo);
		jframe.add(controlPanel);

		lbutton.addActionListener(this);
		rbutton.addActionListener(this);
		/*
		 * try { WsClient.createConnection(
		 * "ws://localhost:8080/WsServer/websocketendpoint/login"); } catch
		 * (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {

		/*
		 * String words[]=text.split("\\s"); l1.setText("Words: "+words.length);
		 * l2.setText("Characters: "+text.length());
		 * 
		 */

		if (e.getSource() == lbutton) {

			// System.out.println(userText.getText());

			try {
				WsClientLogin client = new WsClientLogin();
				try {
					client.createConnection("ws://localhost:8080/RoshanServer/websocketendpoint", "login");
				} catch (InterruptedException e1) {

					System.out.println("can't connect to server");
					// e1.printStackTrace();
				}
			} catch (IOException s) {
				// TODO Auto-generated catch block
				s.printStackTrace();
			}

			if (loginResult() == true) {

				// JOptionPane.showMessageDialog(jframe, "iwjfe");

				DashboardUI dashboard = new DashboardUI();

				dashboard.prepareMenubar();
				// dashboard.editor();
			} else {

				JOptionPane.showMessageDialog(jframe, "Login Failed! Wrong Username or Password, try again!");

			}

		}

		if (e.getSource() == rbutton) {

			new RegisterUI();

		}

	}

	public static void main(String args[]) {

		new Login();

	}

}
