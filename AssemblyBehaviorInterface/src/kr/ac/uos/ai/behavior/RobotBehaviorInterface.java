package kr.ac.uos.ai.behavior;

import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.arbi.model.parser.ParseException;
import kr.ac.uos.ai.behavior.communication.GripperCommunication;
import kr.ac.uos.ai.behavior.communication.RobotCommunication;
import kr.ac.uos.ai.behavior.communication.message.BehaviorMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.RobotStatusMessage;
import kr.ac.uos.ai.behavior.communication.message.value.ActionType;
import kr.ac.uos.ai.behavior.communication.message.value.RobotID;

public class RobotBehaviorInterface extends BehaviorInterface{

	private RobotCommunication robotCommunication;
	private GripperCommunication gripperCommunication;
	
	
	public RobotBehaviorInterface(String brokerAddress, int brokerPort, String robotID, String robotIP,int robotPort, String gripperPort) {
		super(brokerAddress, brokerPort);
		
		robotCommunication = new RobotCommunication(this, robotID, robotIP, robotPort);
		gripperCommunication = new GripperCommunication(this, gripperPort);
		
		robotCommunication.connect();
		gripperCommunication.connect();
		
//		assertInitialStatus(robotID);
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}
	
	private void assertInitialStatus(String robotID) {
		String position = "(robotPosition \"" + robotID + "\" \"" + robotID + "Home\")";
		this.dataSource.assertFact(position);
		String state = "(robotState \"" + robotID + "\" \"ready\")";
		this.dataSource.assertFact(state);
	}
	
	@Override
	public String onRequest(String sender, String request) {
		System.out.println("[request]\t: " + request + " timestamp : " + System.currentTimeMillis());
		try {
			GeneralizedList gl = GLFactory.newGLFromGLString(request);
			ActionType actionType = ActionType.valueOf(gl.getName());
			String actionID = gl.getExpression(0).asValue().stringValue();
			
			String response = null;
			String robotCommand = null;
			String item = null;
			String rotation= null;
			
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
				
			case Grasp :
				item = gl.getExpression(1).asValue().stringValue();
				response = gripperCommunication.grasp(sender, actionID, item);
				break;
				
			case Release :
				item = gl.getExpression(1).asValue().stringValue();
				response = gripperCommunication.release(sender, actionID, item);
				break;
				
			case RotateGripper :
				rotation = gl.getExpression(1).asValue().stringValue();
				response = gripperCommunication.rotate(sender, actionID, rotation);
				break;
							
			case InitGripper :
				response = gripperCommunication.initGripper(sender, actionID);
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
		if (message instanceof RobotStatusMessage) {
			RobotStatusMessage m = (RobotStatusMessage) message;
			this.onRobotStatus(m);
		} else System.out.println("wrong message arrived : " + message.toString());
		
	}
	
	private void onRobotStatus(RobotStatusMessage message) {
		String robotID = message.getRobotID().toString();
		String robotState = message.getState().toString();
		String robotPosition = message.getPosition().toString();
		
		this.updateRobotPosition(robotID, robotPosition);
		this.updateRobotState(robotID, robotState);
	}
	
	private void updateRobotPosition(String robotID, String position) {
		String before = "(robotPosition \"" + robotID + "\" $oldPosition)";
		String after = "(robotPosition \"" + robotID + "\" \"" + position + "\")";
		String update = "(update " + before + " " + after + ")";
		this.dataSource.updateFact(update);
	}
	
	private void updateRobotState(String robotID, String status) {
		String before = "(robotState \"" + robotID + "\" $oldStatus)";
		String after = "(robotState \"" + robotID + "\" \"" + status + "\")";
		String update = "(update " + before + " " + after + ")";
		this.dataSource.updateFact(update);
	}
	
	public static void main(String[] args) {
		BehaviorInterface bi = new RobotBehaviorInterface(Configuration.SERVER_ADDRESS, Configuration.SERVER_PORT_EPSON, "Epson", Configuration.EPSON_ARM_ADDRESS,Configuration.EPSON_ARM_PORT, Configuration.EPSON_GRIPPER_PORT);
		
		ArbiAgentExecutor.execute(Configuration.SERVER_ADDRESS, Configuration.SERVER_PORT_EPSON, Configuration.BEHAVIOR_INTERFACE_ADDRESS, bi, Configuration.BROKER_TYPE);
		String response = bi.onRequest("test", "(Grasp \"test1\" \"PCB\")");
		System.out.println("response : " + response);
	}

}
