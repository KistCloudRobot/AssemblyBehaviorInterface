package kr.ac.uos.ai.behavior;

import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.arbi.model.parser.ParseException;
import kr.ac.uos.ai.behavior.communication.GripperCommunication;
import kr.ac.uos.ai.behavior.communication.URCommunication;
import kr.ac.uos.ai.behavior.communication.message.BehaviorMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.RobotStatusMessage;
import kr.ac.uos.ai.behavior.communication.message.value.ActionType;

public class URBehaviorInterface extends BehaviorInterface{

	private URCommunication robotCommunication;
	private GripperCommunication gripperCommunication;
	
	
	public URBehaviorInterface(String brokerAddress, int brokerPort, String robotID,int robotPort, String robotServerIP, int robotServerPort, String gripperPort) {
		super(brokerAddress, brokerPort);
		
		robotCommunication = new URCommunication(this, robotID, robotPort, robotServerIP, robotServerPort);
		gripperCommunication = new GripperCommunication(this, gripperPort);
		
		robotCommunication.connect();
		gripperCommunication.connect();
		
//		assertInitialStatus(robotID);
	}

//	private void assertInitialStatus(String robotID) {
//		String position = "(robotPosition \"" + robotID + "\" \"" + robotID + "Home\")";
//		this.dataSource.assertFact(position);
//		String state = "(robotState \"" + robotID + "\" \"ready\")";
//		this.dataSource.assertFact(state);
//	}
	
	@Override
	public String onRequest(String sender, String request) {
		logger.log("[request]\t: " + request + " timestamp : " + System.currentTimeMillis());
		try {
			GeneralizedList gl = GLFactory.newGLFromGLString(request);
			ActionType actionType = ActionType.valueOf(gl.getName());
			String actionID = gl.getExpression(0).asValue().stringValue();
			
			String response = null;
			String robotCommand = null;
			String item = null;
			
			switch(actionType) {
			case MoveToPosition :
				robotCommand = gl.getExpression(1).asValue().stringValue();
				response = robotCommunication.moveToPosition(sender, actionID, robotCommand);
				break;
				
			case MoveToTray :
				robotCommand = gl.getExpression(1).asValue().stringValue();
				int x = gl.getExpression(2).asValue().intValue();
				int y = gl.getExpression(3).asValue().intValue();
				response = robotCommunication.moveToTray(sender, actionID, robotCommand, x, y);
				break;

			case Perceive :
				item = gl.getExpression(1).asValue().stringValue();
				response = gripperCommunication.perceive(sender, actionID, item);
				break;
				
			case Grasp :
				item = gl.getExpression(1).asValue().stringValue();
				response = gripperCommunication.grasp(sender, actionID, item);
				break;
				
			case Release :
				item = gl.getExpression(1).asValue().stringValue();
				response = gripperCommunication.release(sender, actionID, item);
				break;
							
			case InitGripper :
				response = gripperCommunication.initGripper(sender, actionID);
				break;
				
			default :
				response = "(wrongRequest)";
				break;
			}
			logger.log("[request] response : " + response);
			return response;
		} catch (ParseException e) {
			e.printStackTrace();
			return "(fail)";
		}
	}


//	@Override
//	public void onMessage(BehaviorMessage message) {
//		if (message instanceof RobotStatusMessage) {
//			RobotStatusMessage m = (RobotStatusMessage) message;
//			this.onRobotStatus(m);
//		} else 
//		System.out.println("Behavior onMessage\t: " + message.toString());
//		
//	}
//	private void onRobotStatus(RobotStatusMessage message) {
//		String robotID = message.getRobotID().toString();
//		String robotState = message.getState().toString();
//		String robotPosition = message.getPosition().toString();
//		
//		this.updateRobotPosition(robotID, robotPosition);
//		this.updateRobotState(robotID, robotState);
//	}
//	
//	private void updateRobotPosition(String robotID, String position) {
//		String before = "(robotPosition \"" + robotID + "\" $oldPosition)";
//		String after = "(robotPosition \"" + robotID + "\" \"" + position + "\")";
//		String update = "(update " + before + " " + after + ")";
//		this.dataSource.updateFact(update);
//	}
//	
//	private void updateRobotState(String robotID, String status) {
//		String before = "(robotState \"" + robotID + "\" $oldStatus)";
//		String after = "(robotState \"" + robotID + "\" \"" + status + "\")";
//		String update = "(update " + before + " " + after + ")";
//		this.dataSource.updateFact(update);
//	}

}
