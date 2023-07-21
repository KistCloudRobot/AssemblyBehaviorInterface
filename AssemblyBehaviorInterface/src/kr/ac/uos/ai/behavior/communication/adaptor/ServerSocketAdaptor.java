package kr.ac.uos.ai.behavior.communication.adaptor;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import kr.ac.uos.ai.behavior.communication.Communication;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.AckEndMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.AckInitMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.AckMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.RobotStatusMessage;

public class ServerSocketAdaptor extends Thread implements Adaptor {
	
	private ServerSocket server;
	private Socket socket;
	private PrintWriter printWriter;
	private BufferedReader bufferedReader;
	private DataInputStream dataInputStream;
	private InputStream	inputStream;
	private ByteArrayOutputStream messageBuffer;
	private Communication robotCommunication;
	
	public ServerSocketAdaptor(Communication rc, int port) {
		this.robotCommunication = rc;
	
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
			inputStream = socket.getInputStream();
			messageBuffer = new ByteArrayOutputStream();
//			dataInputStream = new DataInputStream(socket.getInputStream());
//			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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

			byte[] buffer = new byte[1024];
			int bytesRead;
			
//			while (true) {
//			    byte[] buffer = new byte[1024];
//			    int bytesRead = dataInputStream.read(buffer);
//			    if (bytesRead == -1) {
//			        break;
//			    }
//
//			    String message = new String(buffer, 0, bytesRead);
//			    String[] messages = message.split("\r\n");
//			    for (String msg : messages) {
//			        handleMessage(msg);
//			    }
//			}
//			
			
//			while(true) {
//				String message = dataInputStream.readUTF();
//				System.out.println("dataInputStream \t :" + message);
//				handleMessage(message);
//			}
			
			while ((bytesRead = inputStream.read(buffer)) != -1) {
			    messageBuffer.write(buffer, 0, bytesRead);

			    byte[] messageBytes = messageBuffer.toByteArray();
			    String message = new String(messageBytes, "UTF-8");
			    if (message.endsWith("\r\n")) {
			        handleMessage(message);
			        messageBuffer.reset();  
			    }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	private void handleMessage(String message) {
		System.out.println("handle robotArm Message \t: " + message);
		robotCommunication.onMessage(message);
	}

	@Override
	public void send(String message) {
		System.out.println("send robotArm Message \t: " + message);
		printWriter.println(message + "\r\n");
		printWriter.flush();
	}

	public static void main(String[] args) {
		Adaptor adaptor = new ServerSocketAdaptor(null, 30000);
		adaptor.connect();
	}
	
}
