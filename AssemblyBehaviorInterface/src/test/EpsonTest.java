package test;

import java.util.Scanner;

import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.behavior.Configuration;

public class EpsonTest extends ArbiAgent {
	Scanner sc;

	public EpsonTest() {
		sc = new Scanner(System.in);
	}
	
	public static void main(String[] args) {
		
		EpsonTest test = new EpsonTest();
		Scanner sc = new Scanner(System.in);
		
		ArbiAgentExecutor.execute(Configuration.SERVER_ADDRESS, Configuration.SERVER_PORT_EPSON, "agent://www.arbi.com/TaskManager", test, Configuration.BROKER_TYPE);
		
		String request = "";
		
		sc.nextLine();
		
		request = "(InitGripper \"test2\")";
		test.testMessage(request);
		sc.nextLine();

		request = "(MoveToPosition \"test1\" \"EpsonHome\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToTray \"test2\" \"PCBGet\" 1 1)";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(Perceive \"test20\" \"PCB\")";
		test.testMessage(request);
		sc.nextLine();
		
		
		request = "(Grasp \"test3\" \"PCB\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToPosition \"test6\" \"PCBScan\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToTray \"test4\" \"HousingGet\" 1 1)";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(Perceive \"test21\" \"Housing\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(Grasp \"test5\" \"Housing\")";
		test.testMessage(request);
		sc.nextLine();
		

		request = "(MoveToPosition \"test6\" \"HousingLabel\")";
		test.testMessage(request);
		sc.nextLine();
//		
//		request = "(MoveToPosition \"test7\" \"EpsonPutWait\")";
//		test.testMessage(request);
//		sc.nextLine();
		
		request = "(MoveToPosition \"test8\" \"HousingPut\")";
		test.testMessage(request);
		sc.nextLine();

		request = "(Release \"test9\" \"Housing\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToPosition \"test10\" \"EpsonPutWait\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(Rotate \"test11\" \"On\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToPosition \"test12\" \"PCBPut\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(Release \"test13\" \"PCB\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToPosition \"test14\" \"EpsonPutWait\")";
		test.testMessage(request);
		sc.nextLine(); 

		request = "(Rotate \"test15\" \"Off\")";
		test.testMessage(request);
		sc.nextLine();
	
		request = "(MoveToPosition \"test16\" \"EpsonHome\")";
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
