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
		
		String request = "";

		sc.nextLine();
		
		request = "(InitGripper \"test2\")";
		test.testMessage(request);
		sc.nextLine();
//
//		request = "(CheckRobotReady \"test1\")";
//		test.testMessage(request);
//		sc.nextLine();
//
//		request = "(MoveToPosition \"test2\" \"URHome\")";
//		test.testMessage(request);
//		sc.nextLine();

		request = "(MoveToTray \"test5\" \"Front\" 1 1)";
		test.testMessage(request);
		sc.nextLine();
		request = "(Perceive \"test21\" \"Front\")";
		test.testMessage(request);
		sc.nextLine();
		request = "(Grasp \"test6\" \"Front\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToTray \"test3\" \"Lens\" 1 1)";
		test.testMessage(request);
		sc.nextLine();
		request = "(Perceive \"test20\" \"Lens\")";
		test.testMessage(request);
		sc.nextLine();
		request = "(Grasp \"test4\" \"Lens\")";
		test.testMessage(request);
		sc.nextLine();
		
		
		request = "(MoveToPosition \"test7\" \"URPutWait\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToPosition \"test8\" \"LensPut\")";
		test.testMessage(request);
		sc.nextLine();

		request = "(Release \"test9\" \"Lens\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToPosition \"test10\" \"URPutWait\")";
		test.testMessage(request);
		sc.nextLine();

		request = "(MoveToPosition \"test11\" \"FrontPut\")";
		test.testMessage(request);
		sc.nextLine();
				
		request = "(Release \"test12\" \"Front\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToPosition \"test13\" \"URPutWait\")";
		test.testMessage(request);
		sc.nextLine(); 

		request = "(MoveToPosition \"test14\" \"URHome\")";
		test.testMessage(request);
		sc.nextLine();
	
	}
	
	@Override
	public void onData(String sender, String data) {
		System.out.println("OnData sender \t: " + sender);
		System.out.println("OnData data \t: " + data);
	}
	
	
	public void testMessage(String request) {
		String response = this.request(Configuration.BEHAVIOR_INTERFACE_ADDRESS, request);
		this.log(request, response);
	}
	
	
	public void log(String request, String response) {

		System.out.println("request\t: " + request);
		System.out.println("response\t: " + response);
	}
}
