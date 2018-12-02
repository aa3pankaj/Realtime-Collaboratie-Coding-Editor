package com.websocket.server;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.EncodeException;
import javax.websocket.Session;

public class Room {

	static Map<String, Session> sessionList = new HashMap<>();

	public static void addSession(String username, Session session) {

		sessionList.put(username, session);

	}

	public static void removeSession(String username) {

		sessionList.remove(username);

	}

	public static void handleMessage(Message message) throws IOException, EncodeException {

		if (sessionList.get(message.getReciever()) != null)
			sessionList.get(message.getReciever()).getBasicRemote().sendObject(message);
	}

	static public void handleText(String message) throws IOException, EncodeException {

		for (Map.Entry<String, Session> entry : sessionList.entrySet()) {
			if (!entry.getKey().equals("roshan")) {

				entry.getValue().getBasicRemote().sendText(message);

			}
		}

	}

}
