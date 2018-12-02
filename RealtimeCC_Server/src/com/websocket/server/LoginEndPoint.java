package com.websocket.server;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.EncodeException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

/*
public class ChatEndPoint extends Endpoint {

	
	
	@Override
  	public void onOpen(Session session, EndpointConfig arg1) {
		// TODO Auto-generated method stub
		RemoteEndpoint.Basic remoteEndPointBasic=session.getBasicRemote();
		session.addMessageHandler(new EchoMessageHandler(remoteEndPointBasic));;
	}
	
	private static class EchoMessageHandler implements MessageHandler.Whole<String>{

		
		RemoteEndpoint.Basic remoteEndPointBasic;
		public EchoMessageHandler(RemoteEndpoint.Basic remoteEndPointBasic){
			
			this.remoteEndPointBasic=remoteEndPointBasic;
		}
		
		@Override
		public void onMessage(String message) {
			// TODO Auto-generated method stub
			
			try {
				remoteEndPointBasic.sendText(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		
		
		
		
		
	}
	
}
*/
/*
@ServerEndpoint("/websocket/chat")
public class ChatEndPoint {
	
	
	List<ChatEndPoint> clients =new CopyOnWriteArrayList<>();
	
	Session session;
@OnMessage
	public String onMessage(String message) {
		// TODO Auto-generated method stub
		System.out.println(message);
			broadcast(message);
			return message;
	}
	@OnOpen
	public void onOpen(Session session,EndpointConfig _ ){
		this.session=session;
		clients.add(this);
	}
	@OnClose
	
	public void onClose(){
		
		clients.remove(this);
		
	}
	public void broadcast(String message){
		
		for(ChatEndPoint chatEndPoint: clients){
			
			try {
				chatEndPoint.session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				clients.remove(this);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
		}
		

	}
	
	
	
	
}



*/

import javax.websocket.OnClose;

import javax.websocket.OnError;

import javax.websocket.OnMessage;

import javax.websocket.OnOpen;

import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocketendpoint/login", encoders = LoginEncoder.class, decoders = LoginDecoder.class

)

public class LoginEndPoint {

	Connection con = null;

	@OnOpen
	public void onOpen(Session session) {
		// session.getUserProperties().putIfAbsent("userName", userName);

		try {
			con = DBUtil.getConnection();
			if (con != null)
				System.out.println("connected db from login");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Open Connection for login ...");

	}

	@OnClose

	public void onClose(Session session) {

		System.out.println("Close Connection for login ...");

	}

	@OnMessage
	public String onMessage(User msg, Session session) throws IOException, SQLException {
		if (msg.getType().equals("0")) {
			try {
				if (DatabaseUser.checkUser(msg, con))
					return "true";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			session.close();
			return "false";
		} else 
			if (DatabaseUser.newUser(msg, con))
				return "true";

		
		return "false";
	}

	@OnError

	public void onError(Throwable e) {

		e.printStackTrace();

	}

}
