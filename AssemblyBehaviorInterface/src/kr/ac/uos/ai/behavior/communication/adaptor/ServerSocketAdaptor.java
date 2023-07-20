package kr.ac.uos.ai.behavior.communication.adaptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import kr.ac.uos.ai.behavior.communication.Communication;
import kr.ac.uos.ai.behavior.communication.URCommunication;

public class ServerSocketAdaptor extends Thread implements Adaptor {
	
	private ServerSocket server;
	private Socket socket;
	private PrintWriter printWriter;
	private BufferedReader bufferedReader;
	private Communication robotInterface;
	
	public ServerSocketAdaptor(Communication rc, int port) {
		this.robotInterface = rc;
	
		try {
			this.server = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void connect() {
		try {
			socket = server.accept();
			printWriter = new PrintWriter(socket.getOutputStream());
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    this.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void run() {
		StringBuilder messageBuilder = new StringBuilder();
		while(true) {
			try {
				String line = bufferedReader.readLine();
				if(line != null) {
					if (messageBuilder.length() > 0) {
                        String message = messageBuilder.toString();
                        handleMessage(message);
                        messageBuilder.setLength(0);
                    } else {
                        messageBuilder.append(line);
                    }
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
	}
	
	private void handleMessage(String message) {
//		System.out.println(message);
		robotInterface.onMessage(message);
	}

	@Override
	public void send(String message) {
		printWriter.println(message + "\r\n");
		printWriter.flush();
	}

}
