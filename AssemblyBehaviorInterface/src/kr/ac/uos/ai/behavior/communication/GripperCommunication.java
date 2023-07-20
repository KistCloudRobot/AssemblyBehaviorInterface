package kr.ac.uos.ai.behavior.communication;

import kr.ac.uos.ai.behavior.BehaviorInterface;
import kr.ac.uos.ai.behavior.communication.message.serial.SerialMessage;
import kr.ac.uos.ai.behavior.communication.message.serial.request.GripperInitialize;
import kr.ac.uos.ai.behavior.communication.message.serial.request.Perceive;
import kr.ac.uos.ai.behavior.communication.message.serial.request.Grasp;
import kr.ac.uos.ai.behavior.communication.message.serial.request.Release;
import kr.ac.uos.ai.behavior.communication.message.serial.request.Rotate;
import kr.ac.uos.ai.behavior.communication.message.serial.response.GripperInitResponseMessage;
import kr.ac.uos.ai.behavior.communication.message.serial.response.GripperResponseMessage;

public class GripperCommunication extends SerialCommunication {

	private SerialMessage waitingResponse;
	private Thread check;
	
	public GripperCommunication(BehaviorInterface bi, String portName) {
		super(bi, portName);
		check = new Check();
	}

	@Override
	public void onMessage(String message) {
		if (this.waitingResponse != null && message.startsWith("<I>")) {
			GripperResponseMessage m = parseMessage(message);
			this.waitingResponse.setResponse(m);
			behaviorInterface.sendMessage(waitingResponse.getSender(), waitingResponse.getResponse());
			
			this.waitingResponse = null;
		} else if(this.waitingResponse != null && message.startsWith("<O>")) {
			GripperInitResponseMessage m = new GripperInitResponseMessage(message);
			this.waitingResponse.setResponse(m);
			behaviorInterface.sendMessage(waitingResponse.getSender(), waitingResponse.getResponse());
			
			this.waitingResponse = null;
		} else if (this.waitingResponse == null && message.startsWith("<I>")) {
			System.out.println("check? " + message);
		} else System.out.println("wrong message from gripper : " + message);
	}
	
	private GripperResponseMessage parseMessage(String message) {
		message = message.replace("<I>","").replace("</I>","");
		String[] parsedMessage = message.split(":");
		GripperResponseMessage result = new GripperResponseMessage(parsedMessage);
		return result;
	}
	
	public String initGripper(String sender, String actionID) {
		if(waitingResponse == null) {
			this.waitingResponse = new GripperInitialize(sender, actionID);
			this.adaptor.send(waitingResponse.getMessage());
		}
		return "(ok)";
	}
	
	public String grasp(String sender, String actionID, String object) {
		if(waitingResponse == null ) {
			this.waitingResponse = new Grasp(sender, actionID, object);
			this.adaptor.send(waitingResponse.getMessage());
			this.sendCheck();
			return waitingResponse.getResponse();
		}
		return "(fail \""+actionID+ "\")";
	}
	
	public String release(String sender, String actionID, String object) {
		if(waitingResponse == null) {
			this.waitingResponse = new Release(sender, actionID, object);
			this.adaptor.send(waitingResponse.getMessage());
			this.sendCheck();
			return waitingResponse.getResponse();
		}
		return "(fail \""+actionID+ "\")";
	}

	public String rotate(String sender, String actionID, String rotation) {
		if(waitingResponse == null) {
			this.waitingResponse = new Rotate(sender, actionID, rotation);
			this.adaptor.send(waitingResponse.getMessage());
			this.sendCheck();
			return waitingResponse.getResponse();
		}
		return "(fail \""+actionID+ "\")";
	}
	
	public String perceive(String sender, String actionID, String item) {
		if (waitingResponse == null) {
			this.waitingResponse = new Perceive(sender, actionID, item);
			this.sendCheck();
			return "(ok)";
		}
		return "(fail \""+actionID+ "\")";
	}
	
	public void sendCheck() {
		this.check.run();
	}
	
	private class Check extends Thread {
		@Override
		public void run() {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			adaptor.send("<I>CHECK</I>");
		}
	}
	
}
