package com.websocket.server;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class MessageEncoder implements Encoder.Text<Message> {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public String encode(Message msg) throws EncodeException {
		return Json.createObjectBuilder().add("content", msg.getContent()).add("sender", msg.getSender())
				.add("receiver", msg.getReciever()).add("userConnect", msg.getUserConnect()).build().toString();

	}

}
