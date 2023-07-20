package test;

import java.util.Scanner;

import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.behavior.Configuration;

public class URTest extends ArbiAgent{

	
	public static void main(String[] args) {
		
		URTest test = new URTest();
		Scanner sc = new Scanner(System.in);
		
		ArbiAgentExecutor.execute(Configuration.SERVER_ADDRESS, Configuration.SERVER_PORT_UR, "agent://www.arbi.com/TaskManager", test, Configuration.BROKER_TYPE);
		
		String response = "";
		String request = "";
		String receiver = Configuration.BEHAVIOR_INTERFACE_ADDRESS;

		sc.nextLine();
		
		request = "(InitGripper \"test2\")";
		test.testMessage(request);
		sc.nextLine();

		request = "(CheckRobotReady \"test1\")";
		test.testMessage(request);
		sc.nextLine();

		request = "(MoveToPosition \"test1\" \"URHome\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToTray \"test1\" \"Lens\" 1 1)";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(Grasp \"test1\" \"Lens\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToTray \"test1\" \"Front\" 1 1)";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(Grasp \"test1\" \"Front\")";
		test.testMessage(request);
		sc.nextLine();
		
		
		request = "(MoveToPosition \"test1\" \"URPutWait\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToPosition \"test1\" \"LensPut\")";
		test.testMessage(request);
		sc.nextLine();

		request = "(Release \"test1\" \"Lens\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToPosition \"test1\" \"URPutWait\")";
		test.testMessage(request);
		sc.nextLine();
		
				
		request = "(Release \"test1\" \"Front\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToPosition \"test1\" \"URPutWait\")";
		test.testMessage(request);
		sc.nextLine(); 

		request = "(MoveToPosition \"test1\" \"URHome\")";
		test.testMessage(request);
		sc.nextLine();
	
	}
	
	@Override
	public void onData(String sender, String data) {
		System.out.println("OnDat sender \t: " + sender);
		System.out.println("OnDat data \t: " + data);
	}
	
	
	public void testMessage(String request) {
		String response = this.request(Configuration.BEHAVIOR_INTERFACE_ADDRESS, request);
		this.log(request, response);
	}
	
	
	public void log(String request, String response) {

		System.out.println("request\t : " + request);
		System.out.println("response\t : " + response);
	}
}
