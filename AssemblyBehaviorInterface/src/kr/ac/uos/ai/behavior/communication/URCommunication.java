package kr.ac.uos.ai.behavior.communication;

import kr.ac.uos.ai.behavior.BehaviorInterface;
import kr.ac.uos.ai.behavior.communication.adaptor.Adaptor;
import kr.ac.uos.ai.behavior.communication.adaptor.SocketAdaptor;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.AckEndMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.AckMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.RobotStatusMessage;
import kr.ac.uos.ai.behavior.communication.message.value.RobotID;

public class URCommunication extends RobotCommunication{

	private Adaptor connectionCheck;
	
	public URCommunication(BehaviorInterface bi, String robotID, int robotPort, String robotServerIP, int robotServerPort) {
		super(bi, robotID, robotPort);
		connectionCheck = new SocketAdaptor(this, robotServerIP, robotServerPort);
		this.robotID = RobotID.valueOf(robotID);
	}
	
	@Override
	public void connect() {
		connectionCheck.connect();
		super.connect();
	}
	
	public void onMessage(String message) {
		System.out.println("URComm OnMessage \t: " + message);
		message = message.replace("\r\n", "");
		messageBuilder.append(message);
		if (messageBuilder.toString().endsWith("1") || messageBuilder.toString().endsWith("3") || messageBuilder.toString().startsWith("92")) {

			AckMessage parsedMessage = parseMessage(messageBuilder.toString());
			
			System.out.println("parsed message \t: " + parsedMessage.getType());
		
			if (this.waitingResponse != null) {
				this.waitingResponse.setResponse(parsedMessage);
				behaviorInterface.send(waitingResponse.getSender(), waitingResponse.getResponse());
			}
		
			if (parsedMessage instanceof AckEndMessage) {
				this.waitingResponse = null;
			}
			messageBuilder.setLength(0);
		} else {
			System.out.println("URComm onMessage \t: message is not complete :" + messageBuilder.toString());
			return;
		}
	}
	
	private AckMessage parseMessage(String message) {
		AckMessage result = null;
		message = message.replace("\r\n", "");
		System.out.println("parse message \t: " + message);
//		
//		if (message.startsWith("<")) {
//			result = new AckInitMessage(message);
//			return result;
//		}
		
		String[] parsedMessage = message.split(",");
		int len = parsedMessage.length;
		if (parsedMessage[0].equals("92")) {
			 result = new RobotStatusMessage(Integer.parseInt(parsedMessage[0]),Integer.parseInt(parsedMessage[1]),Integer.parseInt(parsedMessage[2]),Integer.parseInt(parsedMessage[3]));
			 return result;
		} else if (parsedMessage[len-1].equals("1")) {
			result = new AckMessage(Integer.parseInt(parsedMessage[0]), Integer.parseInt(parsedMessage[len-1]));
			return result;
		} else if (parsedMessage[len-1].equals("3")){
			result = new AckEndMessage(Integer.parseInt(parsedMessage[0]), Integer.parseInt(parsedMessage[len-1]));
			return result;
		} else System.out.println("wrong message : " + message); 
		return result;
	}
	
}