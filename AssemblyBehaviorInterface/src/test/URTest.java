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
		
		request = "(StartURController \"test1\")";
		request = "(InitURGripper \"test2\")";
		request = "(MoveToPosition \"test1\" \"URHome\")";
		request = "(MoveToTray \"test1\" \"Housing\" 1 1)";
		request = "(Grasp \"test1\" \"Housing\")";
		request = "(Release \"test1\" \"Housing\")";
		response = test.request(receiver, request);
	
		
		request = "(StartURController \"test1\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(InitGripper \"test2\")";
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
	
	
	public void testMessage(String request) {
		String response = this.request(Configuration.BEHAVIOR_INTERFACE_ADDRESS, request);
		this.log(request, response);
	}
	
	
	public void log(String request, String response) {

		System.out.println("request\t : " + request);
		System.out.println("response\t : " + response);
	}
}
