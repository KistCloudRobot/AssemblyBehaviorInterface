package test;

import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.behavior.Configuration;

public class URTest extends ArbiAgent{

	
	public static void main(String[] args) {
		
		ArbiAgent test = new URTest();
		
		ArbiAgentExecutor.execute(Configuration.SERVER_ADDRESS, Configuration.SERVER_PORT_UR, "agent://www.arbi.com/TaskManager", test, Configuration.BROKER_TYPE);
		
		String response = "";
		String request = "";
		String receiver = Configuration.BEHAVIOR_INTERFACE_ADDRESS;
		
		
		request = "(MoveToPosition \"test1\" \"URHome\")";
		request = "(MoveToTray \"test1\" \"Housing\" 1 1)";
		request = "(Grasp \"test1\" \"Housing\")";
		request = "(Release \"test1\" \"Housing\")";
		response = test.request(receiver, request);
	
		
		System.out.println("request\t : " + request);
		System.out.println("response\t : " + response);
	
	}
}
