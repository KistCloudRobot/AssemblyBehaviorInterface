package kr.ac.uos.ai.behavior;

import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.arbi.model.parser.ParseException;
import kr.ac.uos.ai.behavior.communication.SubPCCommunication;
import kr.ac.uos.ai.behavior.communication.TrayCommunication;
import kr.ac.uos.ai.behavior.communication.message.BehaviorMessage;
import kr.ac.uos.ai.behavior.communication.message.value.ActionType;

public class TrayBehaviorInterface extends BehaviorInterface{

	private TrayCommunication trayCommunication;
	private SubPCCommunication subPCCommunication;
	
	
	public TrayBehaviorInterface(String brokerAddress, int brokerPort, String trayPort, String subPCPort) {
		super(brokerAddress, brokerPort);
		
		trayCommunication = new TrayCommunication(this, trayPort);
		subPCCommunication = new SubPCCommunication(this, subPCPort);
		
		trayCommunication.connect();
		subPCCommunication.connect();
	}
	
	
	@Override
	public String onRequest(String sender, String request) {
		System.out.println("[request]\t: " + request + " timestamp : " + System.currentTimeMillis());
		try {
			GeneralizedList gl = GLFactory.newGLFromGLString(request);
			ActionType actionType = ActionType.valueOf(gl.getName());
			String actionID = gl.getExpression(0).asValue().stringValue();
			
			String response = null;
			
			switch(actionType) {
			case RiseGuideJig :
				response = subPCCommunication.riseGuideJig(sender, actionID);
				break;
				
			case VisionInspect :
				response = subPCCommunication.visionInspect(sender, actionID);
				break;
				
			case CheckLabel :
				response = subPCCommunication.checkLabel(sender, actionID);
				break;
				
			case RotateToAttachPosition :
				response = trayCommunication.rotateToAttachPosition(sender, actionID);
				break;
				
			case RotateToLabelPosition :
				response = trayCommunication.rotateToLabelPosition(sender, actionID);
				break;
				
			case StartVacuum :
				response = trayCommunication.startVacuum(sender, actionID);
				break;
				
			case StopVacuum :
				response = trayCommunication.stopVacuum(sender, actionID);
				break;
				
			case LiftUp :
				response = trayCommunication.liftUp(sender, actionID);
				break;
				
			case LiftDown :
				response = trayCommunication.liftDown(sender, actionID);
				break;
				
				
			default :
				response = "(wrongRequest)";
				break;
			}
			System.out.println("before response : " + response);
			return response;
		} catch (ParseException e) {
			e.printStackTrace();
			return "(fail)";
		}
	}


	@Override
	public void onMessage(BehaviorMessage message) {
		System.out.println("wrong message arrived : " + message.toString());
	}
	
	
	
	public static void main(String[] args) {
		BehaviorInterface bi = new TrayBehaviorInterface(Configuration.SERVER_ADDRESS, Configuration.SERVER_PORT_EPSON, Configuration.TRAY_PORT, Configuration.SUB_PC_PORT);
		
		ArbiAgentExecutor.execute(Configuration.SERVER_ADDRESS, Configuration.SERVER_PORT_EPSON, Configuration.BEHAVIOR_INTERFACE_ADDRESS, bi, Configuration.BROKER_TYPE);
		String response = bi.onRequest("test", "(Grasp \"test1\" \"PCB\")");
		System.out.println("response : " + response);
	}

}
