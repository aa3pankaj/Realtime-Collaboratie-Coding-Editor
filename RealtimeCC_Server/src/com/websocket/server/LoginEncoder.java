package com.websocket.server;


import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import javax.json.Json; 
import javax.json.JsonObject; 
import javax.json.JsonObjectBuilder; 

public class LoginEncoder implements Encoder.Text<User>{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(User msg) throws EncodeException {
		return Json.createObjectBuilder().add("type", msg.getType())
		.add("username", msg.getUsername())
		.add("password", msg.getPassword()).build().toString();
		
	}

}
