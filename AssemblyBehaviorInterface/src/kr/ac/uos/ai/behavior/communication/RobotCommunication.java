package kr.ac.uos.ai.behavior.communication;

import kr.ac.uos.ai.behavior.BehaviorInterface;
import kr.ac.uos.ai.behavior.communication.adaptor.ServerSocketAdaptor;
import kr.ac.uos.ai.behavior.communication.adaptor.SocketAdaptor;
import kr.ac.uos.ai.behavior.communication.message.robot.RobotMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.AckInitMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.AckEndMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.AckMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.RobotStatusMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.request.CheckRobotReady;
import kr.ac.uos.ai.behavior.communication.message.robot.request.MoveToPosition;
import kr.ac.uos.ai.behavior.communication.message.robot.request.MoveToTray;
import kr.ac.uos.ai.behavior.communication.message.value.RobotID;

public abstract class RobotCommunication extends Communication{

	protected RobotMessage waitingResponse;
	protected RobotID robotID;
	protected StringBuilder messageBuilder;
	
	public RobotCommunication(BehaviorInterface bi, String robotID, int port) {
		super(bi);
		adaptor = new ServerSocketAdaptor(this, port);
		this.robotID = RobotID.valueOf(robotID);
		messageBuilder = new StringBuilder();
	}
	
	public String moveToTray(String sender, String actionID, String command, int x, int y) {
		if (this.waitingResponse == null) {
			this.waitingResponse = new MoveToTray(sender, actionID, command , x, y);
			String data = waitingResponse.getMessage();
			this.adaptor.send(data);
			return this.waitingResponse.getResponse();
		} else {
			return "(fail \"" + actionID + "\")";
		}
	}
	
	public String moveToPosition(String sender, String actionID, String position) {
		if (this.waitingResponse == null) {
			this.waitingResponse = new MoveToPosition(sender, actionID, position);
			this.adaptor.send(waitingResponse.getMessage());
			return this.waitingResponse.getResponse();
		} else {
			return "(fail \"" + actionID + "\")";
		}
	}
		
	public RobotID getRobotID() {
		return robotID;
	}

	public String checkRobotReady(String sender, String actionID) {
		if (this.waitingResponse == null) {
			this.waitingResponse = new CheckRobotReady(sender, actionID, this.robotID);
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		} else {
			return "(fail \"" + actionID + "\")";
		}
	}

}
