package test;

import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.behavior.Configuration;

public class EpsonTest extends ArbiAgent {

	
	public static void main(String[] args) {
		
		ArbiAgent test = new EpsonTest();
		
		ArbiAgentExecutor.execute(Configuration.SERVER_ADDRESS, Configuration.SERVER_PORT_EPSON, "agent://www.arbi.com/TaskManager", test, Configuration.BROKER_TYPE);
		
		String response = "";
		String request = "";
		String receiver = Configuration.BEHAVIOR_INTERFACE_ADDRESS;
		
		
		request = "(MoveToPosition \"test1\" \"EpsonHome\")";
		request = "(MoveToTray \"test1\" \"PCB\" 1 1)";
		request = "(Grasp \"test1\" \"PCB\")";
		request = "(Release \"test1\" \"PCB\")";
		request = "(Rotate \"test1\" \"On\")";
		response = test.request(receiver, request);
	
		
		System.out.println("request\t : " + request);
		System.out.println("response\t : " + response);
	
	}
}
