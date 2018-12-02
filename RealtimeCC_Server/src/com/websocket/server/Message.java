package com.websocket.server;

public class Message {

	private String sender;

	private String reciever;

	private String content;

	private String userConnect;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public void setUserConnect(String userConnect) {
		this.userConnect = userConnect;
	}

	public String getUserConnect() {
		return userConnect;
	}

	public String getReciever() {
		return reciever;
	}

	public void setReciever(String reciever) {
		this.reciever = reciever;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Message(String sender, String reciever, String content) {

		this.sender = sender;
		this.reciever = reciever;
		this.content = content;
	}

	public Message() {
		// TODO Auto-generated constructor stub
	}

}
