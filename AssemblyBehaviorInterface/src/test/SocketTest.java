package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import kr.ac.uos.ai.behavior.communication.RobotCommunication;

public class SocketTest extends Thread{

	private static String ip = "127.0.0.1";
	private static int port = 1470;
	private ServerSocket server;
	private Socket socket;
	private PrintWriter printWriter;
	private BufferedReader bufferedReader;
	private RobotCommunication robotInterface;
	
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(port);
		
		while(true) {
			Socket socket = server.accept();

			BufferedReader br = null;
			PrintWriter pw = null;
			
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			pw = new PrintWriter(socket.getOutputStream());

			String str = br.readLine();
			if (str.startsWith("12")) {
				System.out.println("send ack");
				pw.println("12,1,1,0,0,0,1\r\n");
				pw.flush();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("send ack end");
				pw.println("12,1,1,0,0,0,3\r\n");
				pw.flush();
			}
//			System.out.println(br.readLine());
			
//			pw.println("<I>1:1:1:0:1:0:1:0:0:0:0:0:0:0:0:0</I>\r\n");
//			pw.flush();
		}
		
	}
	

	public SocketTest() throws IOException {

		server = new ServerSocket(port);
		while(true) {
			Socket socket;
			socket = server.accept();			
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
			printWriter = new PrintWriter(socket.getOutputStream());

//			System.out.println(br.readLine());
			
//			pw.println("<I>1:1:1:0:1:0:1:0:0:0:0:0:0:0:0:0</I>\r\n");
//			pw.flush();
		}
	}
	
	public void run() {
		while(true) {
			try {
				String message = bufferedReader.readLine();
				if(message != null) {
	
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
	}
	
}
