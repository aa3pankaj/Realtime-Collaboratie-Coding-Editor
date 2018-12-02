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

@ServerEndpoint(value = "/websocketendpoint/{userName}", encoders = { MessageEncoder.class }, decoders = {
		MessageDecoder.class }

)

public class ChatEndPoint {
	
	Connection con = null;
	static Session currentSession = null;

	@OnOpen
	public void onOpen(Session session, @PathParam("userName") String userName) {
		// session.getUserProperties().putIfAbsent("userName", userName);

		try {
			con = DBUtil.getConnection();
			if (con != null)
				System.out.println("connected");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Room.addSession(userName, session);
		this.currentSession = session;
		System.out.println("Open Connection ...");
		System.out.println(userName);

	}

	@OnClose

	public void onClose(Session session, @PathParam("userName") String userName) {

		System.out.println("Close Connection ...");

		Room.removeSession(userName);
	}

	@OnMessage
	public void onMessage(Message message, Session session) {
		// messageJson.replaceAll("\r?\n", "");

		System.out.println(message.getContent());

		/*
		 * Gson gson = new Gson();
		 * 
		 * 
		 * 
		 * 
		 * Map<String,String> message = gson.fromJson(messageJson, Map.class);
		 * // System.out.println(message.get("content").toString());
		 * System.out.println(messageJson);
		 * 
		 * 
		 * System.out.println(message);
		 * 
		 * Message message = new Message(); JsonReader jsonReader =
		 * Json.createReader(new StringReader(messageJson)); JsonObject
		 * jsonobject = jsonReader.readObject();
		 * message.setContent(jsonobject.getString("content"));
		 * message.setSender(jsonobject.getString("sender"));
		 * message.setReciever(jsonobject.getString("receiver"));
		 * 
		 * 
		 */

		try {

			Room.handleMessage(message);

		} catch (IOException | EncodeException e) {

			e.printStackTrace();
		}
	}

	@OnError

	public void onError(Throwable e) {

		e.printStackTrace();

	}

}
