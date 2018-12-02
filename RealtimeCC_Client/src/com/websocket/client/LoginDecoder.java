package com.websocket.client;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class LoginDecoder implements Decoder.Text<User>{

	@Override
	public void destroy() {
		
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		
		
	}

	@Override
	public User decode(String msgJson) throws DecodeException {
		
		System.out.println(msgJson);
		msgJson=	msgJson.toString();
		User message = new User();
		JsonReader jsonReader = Json.createReader(new StringReader(msgJson));
		JsonObject jsonobject = jsonReader.readObject();
		
		message.setUsername(jsonobject.getString("username"));
		message.setPassword(jsonobject.getString("password"));
		message.setType(jsonobject.getString("type"));
		
		return message;
	}

	@Override
	public boolean willDecode(String arg0) {
	
		return true;
	}
	
}
