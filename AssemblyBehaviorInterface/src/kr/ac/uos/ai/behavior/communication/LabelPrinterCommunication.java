package kr.ac.uos.ai.behavior.communication;

import kr.ac.uos.ai.behavior.BehaviorInterface;
import kr.ac.uos.ai.behavior.communication.message.serial.SerialMessage;
import kr.ac.uos.ai.behavior.communication.message.serial.request.LiftDown;
import kr.ac.uos.ai.behavior.communication.message.serial.request.LiftUp;
import kr.ac.uos.ai.behavior.communication.message.serial.request.RotateToAttachPosition;
import kr.ac.uos.ai.behavior.communication.message.serial.request.RotateToLabelPosition;
import kr.ac.uos.ai.behavior.communication.message.serial.request.StartVacuum;
import kr.ac.uos.ai.behavior.communication.message.serial.request.StopVacuum;
import kr.ac.uos.ai.behavior.communication.message.serial.response.SubPCResponseMessage;
import kr.ac.uos.ai.behavior.communication.message.serial.response.TrayResponseMessage;

public class LabelPrinterCommunication extends SerialCommunication {

	private SerialMessage waitingResponse;
	
	public LabelPrinterCommunication(BehaviorInterface bi, String portName) {
		super(bi, portName);
	}

	@Override
	public void onMessage(String message) {
		System.out.println("[LabelPrinterCommunication] onMessage : " + message);
//		message = removeEndLineMarker(message);
		message = message.replace("\r", "");
		message = message.replace("\n", "");
		if (this.waitingResponse != null && message.startsWith("<") && message.endsWith(">")) {
			TrayResponseMessage m = parseMessage(message);
			this.waitingResponse.setResponse(m);
			behaviorInterface.sendMessage(waitingResponse.getSender(), waitingResponse.getResponse());
			
			this.waitingResponse = null;
		} else System.out.println("[LabelPrinterCommunication] wrong message from printer : " + message);
	}
	
	private TrayResponseMessage parseMessage(String message) {
		if (message.startsWith("<") && message.endsWith(">")) {
			TrayResponseMessage result = new TrayResponseMessage(message);
			return result;
		} else System.out.println("[SubPCCommunication] parsing failed : " + message);
		return null;

	}
	
	public String rotateToLabelPosition(String sender, String actionID) {
		if(waitingResponse == null) {
			this.waitingResponse = new RotateToLabelPosition(sender, actionID);
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}

	public String rotateToAttachPosition(String sender, String actionID) {
		if(waitingResponse == null) {
			this.waitingResponse = new RotateToAttachPosition(sender, actionID);
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}

	public String startVacuum(String sender, String actionID) {
		if(waitingResponse == null) {
			this.waitingResponse = new StartVacuum(sender, actionID);
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}
	
	public String stopVacuum(String sender, String actionID) {
		if(waitingResponse == null) {
			this.waitingResponse = new StopVacuum(sender, actionID);
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}
	
	public String liftUp(String sender, String actionID) {
		if(waitingResponse == null) {
			this.waitingResponse = new LiftUp(sender, actionID);
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}
	
	public String liftDown(String sender, String actionID) {
		if(waitingResponse == null) {
			this.waitingResponse = new LiftDown(sender, actionID);
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		return "(fail)";
	}
}
