package kr.ac.uos.ai.behavior.communication;

import kr.ac.uos.ai.behavior.BehaviorInterface;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.AckInitMessage;
import kr.ac.uos.ai.behavior.communication.adaptor.ServerSocketAdaptor;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.AckEndMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.AckMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.RobotStatusMessage;

public class EpsonCommunication extends RobotCommunication{

	public EpsonCommunication(BehaviorInterface bi, String robotID, int port) {
		super(bi, robotID, port);
		adaptor = new ServerSocketAdaptor(this, port);
	}
	
	public void onMessage(String message) {
		System.out.println("[EpsonCommunication] onMessage : " + message);
		message = removeEndLineMarker(message);
		messageBuilder.append(message);
		if (messageBuilder.toString().endsWith("1") || messageBuilder.toString().endsWith("3") || messageBuilder.toString().startsWith("92")) {
			AckMessage parsedMessage = parseMessage(messageBuilder.toString());

			System.out.println("[EpsonCommunication] parsed message : " + parsedMessage.toString());
			if (parsedMessage instanceof AckInitMessage) {
				System.out.println("[EpsonCommunication] connected : " + message);
				this.waitingResponse = null;
				return;
			}
			
			if (this.waitingResponse != null) {
				this.waitingResponse.setResponse(parsedMessage);
			}
			
			if (parsedMessage instanceof AckEndMessage) {
				behaviorInterface.send(waitingResponse.getSender(), waitingResponse.getResponse());
				this.waitingResponse = null;
			}
			
			messageBuilder.setLength(0);
		} else if (messageBuilder.toString().contains("EPSON")) {
			System.out.println("[EpsonCommunication] onMessage : Epson connected : " + messageBuilder.toString());
			messageBuilder.setLength(0);
		} else {
			System.out.println("[EpsonCommunication] onMessage : message is not complete : " + messageBuilder.toString());
			return;
		}
		
	}
	
	private AckMessage parseMessage(String message) {
		AckMessage result = null;
		System.out.println("[EpsonCommunication] parseMessage :  " + message);
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
			result = new AckMessage(0, Integer.parseInt(serialMessage[len-1]));
//			result = new AckMessage(Integer.parseInt(serialMessage[0]), Integer.parseInt(serialMessage[len-1]));
			return result;
		} else if (serialMessage[len-1].equals("3")){
			result = new AckMessage(0, Integer.parseInt(serialMessage[len-1]));
//			result = new AckEndMessage(Integer.parseInt(serialMessage[0]), Integer.parseInt(serialMessage[len-1]));
			return result;
		} else {
			System.out.println("[EpsonCommunication] parsing failed : " + message);
		}
		return result;
	}
	
	public static void main(String[] args) {
		EpsonCommunication epson = new EpsonCommunication(null, "Epson", 30000);
		
		epson.connect();
	}
}
