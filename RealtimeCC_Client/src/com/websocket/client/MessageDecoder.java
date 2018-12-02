package com.websocket.client;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message>{

	@Override
	public void destroy() {
		
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		
		
	}

	@Override
	public Message decode(String msgJson) throws DecodeException {
		
		//System.out.println(msgJson);
		msgJson=	msgJson.toString();
		Message message = new Message();
		JsonReader jsonReader = Json.createReader(new StringReader(msgJson));
		JsonObject jsonobject = jsonReader.readObject();
		
		message.setSender(jsonobject.getString("sender"));
		message.setReciever(jsonobject.getString("receiver"));
		message.setContent(jsonobject.getString("content"));
		message.setUserConnect(jsonobject.getString("userConnect"));
		//System.out.println(message.getSender()+message.getReciever()+message.getContent()+message.getUserConnect());
		
		return message;
	}

	@Override
	public boolean willDecode(String arg0) {
	
		return true;
	}
	
}
