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
		

		request = "(MoveToPosition \"test1\" \"EpsonHome\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToTray \"test1\" \"PCB\" 1 1)";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(Grasp \"test1\" \"PCB\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToTray \"test1\" \"Housing\" 1 1)";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(Grasp \"test1\" \"Housing\")";
		test.testMessage(request);
		sc.nextLine();
		

		request = "(MoveToPosition \"test1\" \"HousingLabel\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToPosition \"test1\" \"EpsonPutWait\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToPosition \"test1\" \"HousingPut\")";
		test.testMessage(request);
		sc.nextLine();

		request = "(Release \"test1\" \"Housing\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToPosition \"test1\" \"EpsonPutWait\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(Rotate \"test1\" \"On\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToPosition \"test1\" \"PCBPut\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(Release \"test1\" \"PCB\")";
		test.testMessage(request);
		sc.nextLine();
		
		request = "(MoveToPosition \"test1\" \"EpsonPutWait\")";
		test.testMessage(request);
		sc.nextLine(); 

		request = "(Rotate \"test1\" \"Off\")";
		test.testMessage(request);
		sc.nextLine();
	
		request = "(MoveToPosition \"test1\" \"EpsonHome\")";
		test.testMessage(request);
		sc.nextLine();
		
	}
	
	@Override
	public void onData(String sender, String data) {
		System.out.println("OnDat sender \t: " + sender);
		System.out.println("OnDat data \t: " + data);
	}
	
	public void testMessage(String request) {
		String response = this.request( Configuration.BEHAVIOR_INTERFACE_ADDRESS, request);
		this.log(request, response);
	}
	
	
	public void log(String request, String response) {

		System.out.println("request\t : " + request);
		System.out.println("response\t : " + response);
	}
}
