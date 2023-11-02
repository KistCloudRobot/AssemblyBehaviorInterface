package kr.ac.uos.ai.behavior.communication;

import kr.ac.uos.ai.behavior.BehaviorInterface;
import kr.ac.uos.ai.behavior.communication.adaptor.Adaptor;
import kr.ac.uos.ai.behavior.communication.adaptor.ServerSocketAdaptor;
import kr.ac.uos.ai.behavior.communication.adaptor.SocketAdaptor;
import kr.ac.uos.ai.behavior.communication.adaptor.URServerSocketAdaptor;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.AckEndMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.AckMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.RobotStatusMessage;
import kr.ac.uos.ai.behavior.communication.message.value.RobotID;

public class URCommunication extends RobotCommunication{

	private Adaptor connectionCheck;
	
	public URCommunication(BehaviorInterface bi, String robotID, int robotPort, String robotServerIP, int robotServerPort) {
		super(bi, robotID, robotPort);
		adaptor = new URServerSocketAdaptor(this, robotPort);
		connectionCheck = new SocketAdaptor(this, robotServerIP, robotServerPort);
		this.robotID = RobotID.valueOf(robotID);
	}
	
	@Override
	public void connect() {
		adaptor.start();
		connectionCheck.connect();
		connectionCheck.send("stop");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connectionCheck.send("play");
	}
	
	public void stopUR() {
		
	}
	
	public void startUR() {
		
	}
	
	
	public void onMessage(String message) {

		System.out.println("[URCommunication] onMessage : " + message);
		message = message.replace("\n", "");
		message = message.replace("\r", "");
		messageBuilder.append(message);
		if (messageBuilder.toString().endsWith("1") || messageBuilder.toString().endsWith("3") || messageBuilder.toString().startsWith("92")) {

			AckMessage parsedMessage = parseMessage(messageBuilder.toString());

			System.out.println("[URCommunication] parsed message : " + parsedMessage.getClass());
		
			if (this.waitingResponse != null) {
				this.waitingResponse.setResponse(parsedMessage);
			}
		
			if (parsedMessage instanceof AckEndMessage) {
				behaviorInterface.send(waitingResponse.getSender(), waitingResponse.getResponse());
				this.waitingResponse = null;
			}
			messageBuilder.setLength(0);
		} else if (messageBuilder.toString().contains("UR10")) {
			System.out.println("[URCommunication] UR Connected : " + messageBuilder.toString());

			messageBuilder.setLength(0);
		} else {
			System.out.println("[URCommunication] message is not complete : " + messageBuilder.toString());
			return;
		}
	}
	
	private AckMessage parseMessage(String message) {
		AckMessage result = null;
		message = message.replace("\r\n", "");

		System.out.println("[URCommunication] parseMessage : " + message);
		
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
		} else System.out.println("[URCommunication] parsing failed : " + message);
		return result;
	}
	
}
