package kr.ac.uos.ai.behavior.communication.adaptor;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import kr.ac.uos.ai.behavior.communication.Communication;

public class URServerSocketAdaptor extends Adaptor {
	
	private ServerSocket server;
	private Socket socket;
	private PrintWriter printWriter;
	private DataInputStream dataInputStream;
	
	public URServerSocketAdaptor(Communication rc, int port) {
		super(rc);
	
		try {
			this.server = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			
			System.out.println("start connect...");
			socket = server.accept();
			printWriter = new PrintWriter(socket.getOutputStream());
			dataInputStream = new DataInputStream(socket.getInputStream());
			System.out.println("connected");
			handleClient(socket);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void handleClient(Socket clientSocket) {

		try {

			byte[] buffer;
			int bytesRead;
			
			while (true) {
			    buffer = new byte[1024];
			    bytesRead = dataInputStream.read(buffer);
			    if (bytesRead == -1) {
			    	System.out.println("[ServerSocketAdaptor] connection has been terminated?");
			        break;
			    }

			    String message = new String(buffer, 0, bytesRead);
			    String[] messages = message.split("\r\n");
			    for (String msg : messages) {
			    	System.out.println("[ServerSocketAdaptor] received message " + msg);
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
		System.out.println("[ServerSocketAdaptor] send message " + message);
		printWriter.println(message + "\r\n");
		printWriter.flush();
	}

	public static void main(String[] args) {
		Adaptor adaptor = new URServerSocketAdaptor(null, 30000);
		adaptor.connect();
	}

	
}
