package kr.ac.uos.ai.behavior.communication.adaptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import kr.ac.uos.ai.behavior.communication.Communication;
import kr.ac.uos.ai.behavior.log.BehaviorLogger;

public class SocketAdaptor extends Adaptor{

	private String ip;
	private int port;
	private Socket socket;
	private PrintWriter printWriter;
	private BufferedReader bufferedReader;
	
	public SocketAdaptor(Communication comm, String ip, int port) {
		super(comm);
		this.ip = ip;
		this.port = port;
	}

	public void connect() {
		while(true) {
			try {
				socket = new Socket(ip, port);

				printWriter = new PrintWriter(socket.getOutputStream());
				bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			    this.start();
				if (this.socket.isConnected()) {
					logger.log("[SocketAdaptor] socket connected : " + ip + ":" + port);
					System.out.println("robot Connected : " + ip + ":" + port);
					break;
				} else {
					logger.log("[SocketAdaptor] wating...");
					System.out.println("wating...");
					Thread.sleep(5000);
					continue;
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				logger.log("[SocketAdaptor] wating...");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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

    					logger.log("[SocketAdaptor] received message : " + message);
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
//		System.out.println("From UR10 Server\t : " + message);
//		robotInterface.onMessage(message);
	}
	
	public void send(String message) {
		logger.log("[SocketAdaptor] received message : " + message);
		printWriter.println(message + "\r\n");
		printWriter.flush();
	}
	
	public static void main(String[] args) {
		SocketAdaptor adaptor = new SocketAdaptor(null,"127.0.0.1", 9777);
		adaptor.connect();
		adaptor.send("test?");
	}

}
