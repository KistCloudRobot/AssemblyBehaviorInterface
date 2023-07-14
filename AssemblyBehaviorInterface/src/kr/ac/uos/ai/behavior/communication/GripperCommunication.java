package kr.ac.uos.ai.behavior.communication;

import kr.ac.uos.ai.behavior.BehaviorInterface;
import kr.ac.uos.ai.behavior.communication.message.serial.SerialMessage;
import kr.ac.uos.ai.behavior.communication.message.serial.request.Grasp;
import kr.ac.uos.ai.behavior.communication.message.serial.request.Release;
import kr.ac.uos.ai.behavior.communication.message.serial.request.Rotate;
import kr.ac.uos.ai.behavior.communication.message.serial.response.GripperResponseMessage;
import kr.ac.uos.ai.behavior.communication.message.value.ActionType;

public class GripperCommunication extends SerialCommunication {

	private ActionType currentAction;
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
			this.currentAction = null;
		} else System.out.println("wrong message from gripper : " + message);
	}
	
	private GripperResponseMessage parseMessage(String message) {
		message = message.replace("<I>","").replace("</I>","");
		String[] parsedMessage = message.split(":");
		GripperResponseMessage result = new GripperResponseMessage(parsedMessage);
		return result;
	}
	
	
	public String grasp(String sender, String actionID, String object) {
		if(waitingResponse == null && currentAction == null) {
			this.waitingResponse = new Grasp(sender, actionID, object);
			this.currentAction = waitingResponse.getActionType();
			this.adaptor.send(waitingResponse.getMessage());
			this.check.start();
			return waitingResponse.getResponse();
		}
		return null;
	}
	
	public String release(String sender, String actionID, String object) {
		if(waitingResponse == null && currentAction == null) {
			this.waitingResponse = new Release(sender, actionID, object);
			this.currentAction = waitingResponse.getActionType();
			this.adaptor.send(waitingResponse.getMessage());
			this.check.start();
			return waitingResponse.getResponse();
		}
		return null;
	}

	public String rotate(String sender, String actionID, String rotation) {
		if(waitingResponse == null && currentAction == null) {
			this.waitingResponse = new Rotate(sender, actionID, rotation);
			this.currentAction = waitingResponse.getActionType();
			this.adaptor.send(waitingResponse.getMessage());
			this.check.start();
			return waitingResponse.getResponse();
		}
		return null;
	}
	
	private class Check extends Thread {
		@Override
		public void run() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			adaptor.send("<I>CHECK</I>");
		}
	}
	
}
