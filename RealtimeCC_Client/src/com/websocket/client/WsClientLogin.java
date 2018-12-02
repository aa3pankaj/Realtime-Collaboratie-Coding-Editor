package com.websocket.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.glassfish.grizzly.CloseReason;

import org.glassfish.tyrus.client.ClientManager;

/*
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;


	
	@ClientEndpoint
	public class WsClient  {
	    private static Object waitLock = new Object();
	@OnMessage
	    public void onMessage(String message) {
	//the new USD rate arrives from the websocket server side.
	       System.out.println("Received msg: "+message);        
	    }
	
	   
	
	 private static void  wait4TerminateSignal()
	 {
	  synchronized(waitLock)
	  {
		  try {
	    waitLock.wait();
	   } catch (InterruptedException e) {    
	   }
       }
	 }
	public static void main(String[] args) {
	WebSocketContainer container=null;//
	     Session session=null;
	  try{
	   //Tyrus is plugged via ServiceLoader API. See notes above
	   container = ContainerProvider.getWebSocketContainer(); 
	//WS1 is the context-root of my web.app 
	//ratesrv is the  path given in the ServerEndPoint annotation on server implementation
	session=container.connectToServer(WsClient.class, URI.create("ws://localhost:8080/WsServer/websocketendpoint"));
	session.getBasicRemote().sendText("hello");
	   wait4TerminateSignal();
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	  finally{
	   if(session!=null){
	    try {
	 session.close();
	    } catch (Exception e) {     
	     e.printStackTrace();
	    }
	   }         
	  } 
	 
	}
	}

	
	
	
	
*/

/*
import java.net.URI;
import java.net.URISyntaxException;

public class WsClient {

    public static void main(String[] args) {
        try {
            // open websocket
            final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("wss://real.okcoin.cn:10440/websocket/okcoinapi"));

            // add listener
            clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {
                    System.out.println(message);
                }
            });

            // send message to websocket
            clientEndPoint.sendMessage("{'event':'addChannel','channel':'ok_btccny_ticker'}");

            // wait 5 seconds for messages from websocket
            Thread.sleep(5000);

        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
    }
}
	
*/

@ClientEndpoint(encoders = { LoginEncoder.class })
public class WsClientLogin {

	static String username;
	private static CountDownLatch latch;
	static Session session1;
	static boolean loginResult;
	// private static Logger logger =
	// Logger.getLogger(this.getClass().getName());

	@OnOpen
	public void onOpen(Session session) throws IOException {
		System.out.println("session established");
		this.session1 = session;

	}

	@OnMessage
	public void onMessage(String user) {

		if (user.equals("true"))
			loginResult = true;
		else
			loginResult = false;

		
	}

	public void createConnection(String endpoint, String dir) throws IOException, InterruptedException {

		ClientManager client = ClientManager.createClient();
		try {
			
			client.connectToServer(WsClientLogin.class, new URI(endpoint + "/" + dir));
			// latch.await();

		} catch (DeploymentException | URISyntaxException e) {
			throw new RuntimeException(e);
		}

	}

}
