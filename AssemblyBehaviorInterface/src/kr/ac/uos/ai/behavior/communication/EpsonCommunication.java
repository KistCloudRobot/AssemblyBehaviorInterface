package kr.ac.uos.ai.behavior.communication;

import kr.ac.uos.ai.behavior.BehaviorInterface;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.AckInitMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.AckEndMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.AckMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.RobotStatusMessage;
import kr.ac.uos.ai.behavior.communication.message.value.RobotID;

public class EpsonCommunication extends RobotCommunication{

	public EpsonCommunication(BehaviorInterface bi, String robotID, int port) {
		super(bi, robotID, port);
	}
	
	public void onMessage(String message) {
		System.out.println("EpsonComm onMessage \t: " + message);
		message = message.replace("\r\n", "");
		messageBuilder.append(message);
		if (messageBuilder.toString().endsWith("1") || messageBuilder.toString().endsWith("3") || messageBuilder.toString().startsWith("92")) {
			AckMessage parsedMessage = parseMessage(messageBuilder.toString());

			System.out.println("parsed message \t: " + parsedMessage.getType());
			if (parsedMessage instanceof AckInitMessage) {
				System.out.println("connected\t : " + message);
				this.waitingResponse = null;
				return;
			}
			
			if (this.waitingResponse != null) {
				this.waitingResponse.setResponse(parsedMessage);
				behaviorInterface.send(waitingResponse.getSender(), waitingResponse.getResponse());
			}
			
			if (parsedMessage instanceof AckEndMessage) {
				this.waitingResponse = null;
			}
			
			messageBuilder.setLength(0);
		} else {
			System.out.println("EpsonComm onMessage \t: message is not complete :" + messageBuilder.toString());
			return;
		}
		
	}
	
	private AckMessage parseMessage(String message) {
		AckMessage result = null;
		
		System.out.println("parse message \t: " + message);
		if (message.startsWith("EPSON")) {
			result = new AckInitMessage(message);
			return result;
		}
		
		String[] serialMessage = message.split(",");
		int len = serialMessage.length;
		if (serialMessage[0].equals("92")) {
			 result = new RobotStatusMessage(Integer.parseInt(serialMessage[0]),Integer.parseInt(serialMessage[1]),Integer.parseInt(serialMessage[2]),Integer.parseInt(serialMessage[3]));
			 return result;
		} else if (serialMessage[len-1].equals("1")) {
			result = new AckMessage(Integer.parseInt(serialMessage[0]), Integer.parseInt(serialMessage[len-1]));
		} else if (serialMessage[len-1].equals("3")){
			result = new AckEndMessage(Integer.parseInt(serialMessage[0]), Integer.parseInt(serialMessage[len-1]));
		} else System.out.println("wrong socket message : " + message); 
		return result;
	}


	public RobotID getRobotID() {
		return robotID;
	}

	
	public static void main(String[] args) {
		EpsonCommunication epson = new EpsonCommunication(null, "Epson", 30000);
		
		epson.connect();
	}
}
