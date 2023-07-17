package kr.ac.uos.ai.behavior.communication;

import kr.ac.uos.ai.behavior.BehaviorInterface;
import kr.ac.uos.ai.behavior.communication.adaptor.ServerSocketAdaptor;
import kr.ac.uos.ai.behavior.communication.adaptor.SocketAdaptor;
import kr.ac.uos.ai.behavior.communication.message.robot.RobotMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.AckEndMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.AckMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.RobotStatusMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.request.MoveToPosition;
import kr.ac.uos.ai.behavior.communication.message.robot.request.MoveToTray;
import kr.ac.uos.ai.behavior.communication.message.value.RobotID;

public class RobotCommunication extends Communication{

	private RobotMessage waitingResponse;
	private RobotID robotID;
	private Thread checkRobotStatus;
	
	public RobotCommunication(BehaviorInterface bi, String robotID, int port) {
		super(bi);
		adaptor = new ServerSocketAdaptor(this, port);
		this.robotID = RobotID.valueOf(robotID);
		checkRobotStatus = new CheckRobotStatus();
	}
	
	public void onMessage(String message) {
		AckMessage parsedMessage = parseMessage(message);
		if (parsedMessage instanceof RobotStatusMessage) {
			RobotStatusMessage m = (RobotStatusMessage) parsedMessage;
			behaviorInterface.onMessage(m);
			return;
		}
		
		if (this.waitingResponse != null) {
			this.waitingResponse.setResponse(parsedMessage);
			behaviorInterface.send(waitingResponse.getSender(), waitingResponse.getResponse());
		}
		
		if (parsedMessage instanceof AckEndMessage) {
			this.waitingResponse = null;
		}
	}
	
	private AckMessage parseMessage(String message) {
		AckMessage result = null;
		String[] parsedMessage = message.split(",");
		int len = parsedMessage.length;
		if (parsedMessage[len-1].equals("1")) {
			result = new AckMessage(Integer.parseInt(parsedMessage[0]), Integer.parseInt(parsedMessage[len-1]));
		} else if (parsedMessage[len-1].equals("3")){
			result = new AckEndMessage(Integer.parseInt(parsedMessage[0]), Integer.parseInt(parsedMessage[len-1]));
		} else if (parsedMessage[0].equals("92")) {
			 result = new RobotStatusMessage(Integer.parseInt(parsedMessage[0]),Integer.parseInt(parsedMessage[1]),Integer.parseInt(parsedMessage[2]),Integer.parseInt(parsedMessage[3]));
		} else System.out.println("wrong socket message : " + message); 
		return result;
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
		
	public void startCheckRobotStatus() {
		this.checkRobotStatus.start();
	}
	
	public RobotID getRobotID() {
		return robotID;
	}
	
	private class CheckRobotStatus extends Thread {
		@Override
		public void run() {
			while(true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				adaptor.send("91,0,0," + robotID.getValue());
			}
		}
	}
	
}
