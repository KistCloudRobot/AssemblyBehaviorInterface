package kr.ac.uos.ai.behavior.communication.adaptor;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import kr.ac.uos.ai.behavior.communication.Communication;
import kr.ac.uos.ai.behavior.log.BehaviorLogger;

public class ServerSocketAdaptor extends Adaptor {
	
	private BehaviorLogger logger;
	private ServerSocket server;
	private Socket socket;
	private PrintWriter printWriter;
	private DataInputStream dataInputStream;
	
	public ServerSocketAdaptor(Communication rc, int port) {
		super(rc);
	
		try {
			this.server = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void connect() {
		try {
			
			System.out.println("start connect...");
			socket = server.accept();
			printWriter = new PrintWriter(socket.getOutputStream());
			dataInputStream = new DataInputStream(socket.getInputStream());
		    this.start();
		    System.out.println("connected");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void run() {
		try {

			byte[] buffer;
			int bytesRead;
			
			while (true) {
			    buffer = new byte[1024];
			    bytesRead = dataInputStream.read(buffer);
			    if (bytesRead == -1) {
			    	logger.log("[ServerSocketAdaptor] connection has been terminated?");
			        break;
			    }

			    String message = new String(buffer, 0, bytesRead);
			    String[] messages = message.split("\r\n");
			    for (String msg : messages) {
			    	logger.log("[ServerSocketAdaptor] received message " + msg);
			        handleMessage(msg);
			    }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	private void handleMessage(String message) {
		communication.onMessage(message);
	}

	@Override
	public void send(String message) {
		logger.log("[ServerSocketAdaptor] send message " + message);
		printWriter.println(message + "\r\n");
		printWriter.flush();
	}

	public static void main(String[] args) {
		Adaptor adaptor = new ServerSocketAdaptor(null, 30000);
		adaptor.connect();
	}
	
}
