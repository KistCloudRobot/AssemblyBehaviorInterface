package test;

import java.util.Scanner;

import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.behavior.Configuration;

public class ReplaceTrayTest extends ArbiAgent {
	Scanner sc;

	public ReplaceTrayTest() {
		sc = new Scanner(System.in);
	}
	
	public static void main(String[] args) {
		
		ReplaceTrayTest test = new ReplaceTrayTest();
		Scanner sc = new Scanner(System.in);
		
		ArbiAgentExecutor.execute(Configuration.SERVER_ADDRESS, Configuration.SERVER_PORT_PRODUCTION_FACILITY, "agent://www.arbi.com/TaskManager", test, Configuration.BROKER_TYPE);
		
		String request = "";
		
		sc.nextLine();
		
		request = "(CheckDockingPlateStatus \"test1\" \"Housing\")";
		test.testMessage(request);
		sc.nextLine();
		request = "(RaiseDockingPlate \"test2\" \"Housing\")";
		test.testMessage(request);
		sc.nextLine();
		request = "(MoveDockingPlateToDischargeArea \"test3\" \"Housing\")";
		test.testMessage(request);
		sc.nextLine();
		request = "(LowerDockingPlate \"test4\" \"Housing\")";
		test.testMessage(request);
		sc.nextLine();
		request = "(GraspDockingPlateInLoadingBay \"test5\" \"Housing\")";
		test.testMessage(request);
		sc.nextLine();
		request = "(DetachDockingPlateSideFixturesInLoadingBay \"test6\" \"Housing\")";
		test.testMessage(request);
		sc.nextLine();
		request = "(LowerDockingPlateInLoadingBay \"test7\" \"Housing\")";
		test.testMessage(request);
		sc.nextLine();
		request = "(HoldDockingPlateSideFixturesInLoadingBay \"test8\" \"Housing\")";
		test.testMessage(request);
		sc.nextLine();
		request = "(DetachDockingPlateCenterInLoadingBay \"test9\" \"Housing\")";
		test.testMessage(request);
		sc.nextLine();
		request = "(RaiseDockingHoleFixturesInLoadingBay \"test10\" \"Housing\")";
		test.testMessage(request);
		sc.nextLine();
		request = "(MoveDockingPlateToTheFront \"test11\" \"Housing\")";
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
