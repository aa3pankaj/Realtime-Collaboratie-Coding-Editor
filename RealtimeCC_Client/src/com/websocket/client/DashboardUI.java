package com.websocket.client;
import java.awt.BorderLayout;

import org.glassfish.grizzly.CloseReason;

import org.glassfish.tyrus.client.ClientManager;

import com.google.gson.Gson;
import com.hackerearth.heapi.sdk.responses.CompileResponse;
import com.hackerearth.heapi.sdk.responses.RunResponse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Element;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;

import org.glassfish.tyrus.client.ClientManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

public class DashboardUI implements ActionListener {

	String cameFrom;
	JPanel utilPanel;

	public static void setTextArea(String txt) {

		textArea.setText(txt);
	}

	JButton connectButton;
	JButton compileButton;
	JButton runButton;
	JButton createButton;

	JButton logoutButton;

	private JScrollPane scrollPane = null;
	static JTextArea textArea = null;
	static JTextArea userInputArea;
	static JTextArea outputArea;
	private JTextArea lineNumberBar;
	private JTextField cursorStatusBar;

	private boolean savedStatus = false;
	private String fileName = "";
	private String fileDir = "";

	static JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;

	private JTextField textField;

	JMenuBar menubar;
	JMenu fileMenu;
	JMenu editMenu;
	JMenu findMenu;

	JMenu aboutMenu;

	WsClient client;
	JMenuItem newMenuItem;

	JMenuItem saveMenuItem;
	JMenuItem openMenuItem;
	JMenuItem exitMenuItem;

	JMenuItem cutMenuItem;

	JMenuItem copyMenuItem;

	JMenuItem pasteMenuItem;

	JDialog dialog;
String username;
	public DashboardUI() {
		
		if(RegisterUI.isHere)
		{	RegisterUI.jframe.setVisible(false);
		username=RegisterUI.userText.getText();}
		else
			if(Login.isHere)
			{	Login.jframe.setVisible(false);
		username=Login.userText.getText();
			}
		
	
		

		prepareGUI();

		dialog = new JDialog();
		JLabel label = new JLabel("awesome code!");
		dialog.setLocationRelativeTo(null);
		dialog.setTitle("Please Wait...");
		dialog.add(label);
		dialog.pack();
		dialog.setSize(new Dimension(50, 60));

		try {
			client = new WsClient();
			try {
				
					client.createConnection("ws://localhost:8080/RoshanServer/websocketendpoint",username);
				
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException s) {
			// TODO Auto-generated catch block
			s.printStackTrace();
		}

		/*
		 * createButton.addActionListener(this);
		 * connectButton.addActionListener(new ActionListener(){
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { // TODO
		 * Auto-generated method stu // latch = new CountDownLatch(1); WsClient
		 * cll = new WsClient();
		 * 
		 * ClientManager client = ClientManager.createClient(); try { try {
		 * client.connectToServer(WsClient.class, new
		 * URI("ws://localhost:8080/WsServer/websocketendpoint")); } catch
		 * (IOException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } // latch.await();
		 * 
		 * } catch (DeploymentException | URISyntaxException g) { throw new
		 * RuntimeException(g); }
		 * 
		 * 
		 * 
		 * 
		 * }
		 * 
		 * 
		 * 
		 * } );
		 */
		logoutButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == createButton) {

			System.out.println("create button clicl");
			String create = JOptionPane.showInputDialog("Enter username to connect: ");

			Message message = new Message();
			message.setSender(username);
			message.setReciever(create);
			message.setUserConnect("1");
			message.setContent(username+":hii! Join me bro..");

			try {
				client.session.getBasicRemote().sendObject(message);
				WsClient.userConnected = create;
			} catch (IOException | EncodeException e1) {
				System.out.println("not yet connected");
			}

		}

		// new ConnectCCoding();

		if (e.getSource() == connectButton) {

			// new ConnectCCoding();

		}

		if (e.getSource() == compileButton) {

			// new ConnectCCoding();
			dialog.setVisible(true);
			CompilerConnection cc = new CompilerConnection();

			CompileResponse compileResponse = cc.compile();
			outputArea.setText(compileResponse.getCompileStatus());
			dialog.setVisible(false);

		}

		if (e.getSource() == runButton) {

			// new ConnectCCoding();

			dialog.setVisible(true);

			CompilerConnection cc = new CompilerConnection();

			RunResponse runResponse = cc.run();

			outputArea.setText(runResponse.getRunStatus().getOutput());
			dialog.setVisible(false);
		}

		if (e.getSource() == logoutButton) {

			// new ConnectCCoding();

		}

	}

	public void prepareGUI() {

		/*JLabel userType=null;
		final JComboBox<String> cb;
		userType.setText("Type");
		String[] choices = { "Moderator", "Student" };
		cb = new JComboBox<String>(choices);
		*/
		
		mainFrame = new JFrame("Realtime Collaborative Text Editor: " + username);
		mainFrame.setSize(400, 400);
		mainFrame.setLayout(new GridLayout(2, 1));

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		controlPanel = new JPanel();
		// controlPanel.setLayout(new GridLayout(1,3));
		controlPanel.setLayout(new BorderLayout());

		scrollPane = textAreaUI();

		// connect buuton
		connectButton = new JButton();

		connectButton.setText("Connect");
		connectButton.setSize(100, 100);

		// logout button
		logoutButton = new JButton();

		logoutButton.setText("Logout  ");
		logoutButton.setSize(100, 100);

		// create connection button
		createButton = new JButton();

		createButton.setText("Create  ");
		createButton.setSize(100, 100);

		// connect buuton
		compileButton = new JButton();

		compileButton.setText("Compile");
		compileButton.setSize(100, 100);

		// connect buuton
		runButton = new JButton();

		runButton.setText("Run  ");
		runButton.setSize(100, 100);

		// panel for utilities (buttons)
		utilPanel = new JPanel();
		utilPanel.setSize(200, 800);
		utilPanel.setLayout(new BoxLayout(utilPanel, BoxLayout.Y_AXIS));
		// utilPanel.setLayout(new FlowLayout());
		utilPanel.add(logoutButton);
		utilPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		utilPanel.add(connectButton);
		utilPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		utilPanel.add(createButton);
		utilPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		utilPanel.add(compileButton);
		utilPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		utilPanel.add(runButton);
		// line number text
		JTextArea lineNumber = new JTextArea("\n1");
		lineNumber.setEditable(false);
		lineNumber.setSize(new Dimension(50, 800));

		// lineNumber.setPreferredSize(new Dimentions(1,600));

		// Panel for console and compile and run response

		JPanel consolePanel = new JPanel();

		// consolePanel.setLayout( new BorderLayout());
		// consolePanel.setSize(new Dimension(700,200));

		userInputArea = new JTextArea(20, 60);
		JScrollPane scrollPaneUserInput = new JScrollPane();
		scrollPaneUserInput = new JScrollPane(userInputArea);
		// userInputArea=new JTextArea();
		// outputArea=new JTextArea("pankaj");

		outputArea = new JTextArea(20, 60);
		outputArea.setText("ENTER INPUT VALUES AND PRESS RUN BUTTON TO FETCH OUTPUT");
		outputArea.setEditable(false);
		/*
		 * consolePanel.add( scrollPaneUserInput,BorderLayout.EAST);
		 * consolePanel.add(outputArea,BorderLayout.WEST);
		 * consolePanel.setVisible(true);
		 */

		consolePanel.add(scrollPaneUserInput);
		outputArea.setEditable(false);
		consolePanel.add(outputArea);

		//////////////////////////////////////////////

		controlPanel.add(lineNumber, BorderLayout.WEST);
		controlPanel.add(scrollPane, BorderLayout.CENTER);
		controlPanel.add(utilPanel, BorderLayout.EAST);
		// controlPanel.add(consolePanel,BorderLayout.SOUTH);

		mainFrame.add(controlPanel);
		mainFrame.add(consolePanel);
		mainFrame.pack();
		compileButton.addActionListener(this);
		runButton.addActionListener(this);
		createButton.addActionListener(this);
		/*
		 * textArea.getDocument().addDocumentListener(new DocumentListener() {
		 * 
		 * 
		 * public void changedUpdate(DocumentEvent e) {
		 * 
		 * 
		 * 
		 * 
		 * }
		 * 
		 * public void insertUpdate(DocumentEvent e) { Message message = new
		 * Message(); message.setContent(textArea.getText());
		 * message.setReciever("pankaj"); message.setSender("roshan"); /* try {
		 * // WsClient.session.getBasicRemote().sendText(textArea.getText());
		 * System.out.println("chu"); } catch (IOException e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); }
		 * 
		 * // try { // WsClient.sendMassage(message); // } catch (IOException
		 * e1) { // TODO Auto-generated catch block // e1.printStackTrace(); //
		 * } System.out.println(message.getContent());
		 * 
		 * }
		 * 
		 * public void removeUpdate(DocumentEvent e) {
		 * System.out.println(textArea.getText());
		 * 
		 * 
		 * } });
		 * 
		 * 
		 * 
		 * 
		 */

		// textAreaUI();

		textArea.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

				if (WsClient.userConnected != null) {

					Message message = new Message();
					message.setContent(textArea.getText().toString());
					message.setSender(username);

					message.setReciever(WsClient.userConnected);

					message.setUserConnect("0");

					// message="{ \"sender\":\"John\", \"reciever\":\"pankaj\",
					// \"content\":\"New York\" }";
					try {
						try {
							WsClient.session.getBasicRemote().sendObject(message);
						} catch (EncodeException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				/*
				 * Map<String,String> message=new HashMap<>();
				 * message.put("content",textArea.getText().toString());
				 * message.put("sender","roshan");
				 * message.put("reciever","pankaj");
				 * 
				 * 
				 * 
				 * 
				 * Gson gson = new Gson();
				 */

				// 1. Java object to JSON, and save into a file

				// 2. Java object to JSON, and assign to a String
				/*
				 * String messageJson = gson.toJson(message);
				 * System.out.println(messageJson); try {
				 * WsClient.session.getBasicRemote().sendText(messageJson); }
				 * catch (IOException e1) { // TODO Auto-generated catch block
				 * e1.printStackTrace(); }
				 * 
				 */

			}
		});

	}

	public JScrollPane textAreaUI() {
		textArea = new JTextArea(100, 100);
		textArea.setFont(new Font("Serif", Font.PLAIN, 14));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		JScrollPane scrollPane = new JScrollPane();

		scrollPane.getViewport().add(textArea);
		scrollPane.setRowHeaderView(lineNumberBar);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(BorderFactory
				.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(111)),
						BorderFactory.createEmptyBorder(6, 6, 6, 6)), scrollPane.getBorder()));

		return scrollPane;

	}

	public JTextArea textAreaUIOutput() {
		JTextArea output = new JTextArea(100, 100);
		output.setFont(new Font("Serif", Font.PLAIN, 14));
		output.setLineWrap(true);
		output.setWrapStyleWord(true);

		output.setEditable(false);
		output.setVisible(true);

		return textArea;

	}

	public void prepareMenubar() {

		menubar = new JMenuBar();

		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");
		findMenu = new JMenu("Find");

		aboutMenu = new JMenu("About");

		newMenuItem = new JMenuItem("New");

		saveMenuItem = new JMenuItem("Save");
		openMenuItem = new JMenuItem("Open");
		exitMenuItem = new JMenuItem("Exit");

		cutMenuItem = new JMenuItem("Cut");
		cutMenuItem.setActionCommand("Cut");

		copyMenuItem = new JMenuItem("Copy");
		copyMenuItem.setActionCommand("Copy");

		pasteMenuItem = new JMenuItem("Paste");
		pasteMenuItem.setActionCommand("Paste");

		fileMenu.add(newMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(exitMenuItem);

		editMenu.add(cutMenuItem);
		editMenu.add(copyMenuItem);
		editMenu.add(pasteMenuItem);

		// add menu to menubar
		menubar.add(fileMenu);
		menubar.add(editMenu);
		menubar.add(findMenu);
		menubar.add(aboutMenu);

		// MenuItemListener menuItemListener = new MenuItemListener();

		newMenuItem.addActionListener(this);
		// openMenuItem.addActionListener(this);
		// saveMenuItem.addActionListener(this);
		exitMenuItem.addActionListener(this);
		cutMenuItem.addActionListener(this);
		copyMenuItem.addActionListener(this);
		pasteMenuItem.addActionListener(this);

		// add menubar to the frame
		mainFrame.setJMenuBar(menubar);
	
		mainFrame.setVisible(true);
		openMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					openFile();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
		saveMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					saveFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});
	}

	public void prepareGui_Find() {

		JTextArea area1;
		JButton search = new JButton("search");
		JLabel l1;
		l1 = new JLabel();
		l1.setBounds(50, 25, 100, 30);
		area1 = new JTextArea();
		area1.setBounds(20, 75, 250, 200);
		JFrame f = new JFrame();
		f.add(l1);
		f.add(area1);
		f.add(search);
		f.setSize(450, 450);
		f.setLayout(new GridLayout(3, 1));
		f.setVisible(true);
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String str = area1.getText();
				HightLight highLight = new HightLight();
				highLight.highlight(textField, str);
			}

		});
	}

	public void openFile() throws FileNotFoundException {
		JFileChooser openFile = new JFileChooser();
		int result = openFile.showOpenDialog(null);
		if (openFile.APPROVE_OPTION == result) {
			File selectedFile = openFile.getSelectedFile();
			FileReader readFile = new FileReader(selectedFile);
			BufferedReader data = new BufferedReader(readFile);

			String line;
			try {
				line = data.readLine();
				while (data.read() != -1) {
					line += data.readLine() + '\n';

				}
				textArea.setText(line);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void saveFile() throws IOException {
		JFileChooser saveFile = new JFileChooser();
		File targetFile = null;
		int result = saveFile.showSaveDialog(null);
		if (result == saveFile.APPROVE_OPTION)
			targetFile = saveFile.getSelectedFile();
		if (!targetFile.exists()) {
			targetFile.createNewFile();
		}
		FileWriter fw = new FileWriter(targetFile);
		fw.write(textArea.getText());
		fw.close();
	}

}
