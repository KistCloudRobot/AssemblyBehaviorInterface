package test;

import java.util.Scanner;

import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.behavior.Configuration;

public class TrayTest extends ArbiAgent {
	Scanner sc;

	public TrayTest() {
		sc = new Scanner(System.in);
	}
	
	public static void main(String[] args) {
		
		TrayTest test = new TrayTest();
		Scanner sc = new Scanner(System.in);
		
		ArbiAgentExecutor.execute(Configuration.SERVER_ADDRESS, Configuration.SERVER_PORT_PRODUCTION_FACILITY, "agent://www.arbi.com/TaskManager", test, Configuration.BROKER_TYPE);
		
		String request = "";
		
		sc.nextLine();
		
		request = "(CheckJig \"test1\")";
		test.testMessage(request);
		sc.nextLine();

		
		request = "(RotateToLabelPosition \"test1\")";
		test.testMessage(request);
		sc.nextLine();

		request = "(LiftDown \"test2\")";
		test.testMessage(request);
		sc.nextLine();

		request = "(StartVacuum \"test3\")";
		test.testMessage(request);
		sc.nextLine();

		request = "(LiftUp \"test4\")";
		test.testMessage(request);
		sc.nextLine();

		request = "(RotateToAttachPosition \"test5\")";
		test.testMessage(request);
		sc.nextLine();

		request = "(LiftDown \"test6\")";
		test.testMessage(request);
		sc.nextLine();

		request = "(StopVacuum \"test7\")";
		test.testMessage(request);
		sc.nextLine();

		request = "(LiftUp \"test8\")";
		test.testMessage(request);
		sc.nextLine();
		
		
	}
	
	@Override
	public void onData(String sender, String data) {
		System.out.println("OnData sender \t: " + sender);
		System.out.println("OnData data \t: " + data);
	}
	
	public void testMessage(String request) {
		String response = this.request( Configuration.BEHAVIOR_INTERFACE_ADDRESS, request);
		this.log(request, response);
	}
	
	
	public void log(String request, String response) {

		System.out.println("request\t: " + request);
		System.out.println("response\t: " + response);
	}
}
