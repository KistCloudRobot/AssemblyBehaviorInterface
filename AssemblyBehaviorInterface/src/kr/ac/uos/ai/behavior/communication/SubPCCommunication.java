package kr.ac.uos.ai.behavior.communication;

import kr.ac.uos.ai.behavior.BehaviorInterface;
import kr.ac.uos.ai.behavior.communication.message.serial.SerialMessage;
import kr.ac.uos.ai.behavior.communication.message.serial.request.CheckLabel;
import kr.ac.uos.ai.behavior.communication.message.serial.request.RiseGuideJig;
import kr.ac.uos.ai.behavior.communication.message.serial.request.VisionInspect;
import kr.ac.uos.ai.behavior.communication.message.serial.response.TrayResponseMessage;
import kr.ac.uos.ai.behavior.communication.message.value.ActionType;

public class SubPCCommunication extends SerialCommunication {

	private ActionType currentAction;
	private SerialMessage waitingResponse;
	
	public SubPCCommunication(BehaviorInterface bi, String portName) {
		super(bi, portName);
	}

	@Override
	public void onMessage(String message) {
		if (this.waitingResponse != null && message.startsWith("<")) {
			TrayResponseMessage m = parseMessage(message);
			this.waitingResponse.setResponse(m);
			behaviorInterface.sendMessage(waitingResponse.getSender(), waitingResponse.getResponse());
			
			this.waitingResponse = null;
			this.currentAction = null;
		} else System.out.println("wrong message from tray or sub : " + message);
	}
	
	private TrayResponseMessage parseMessage(String message) {
		TrayResponseMessage result = new TrayResponseMessage(message);
		return result;
	}
	
	public String riseGuideJig(String sender, String actionID) {
		if(waitingResponse == null && currentAction == null) {
			this.waitingResponse = new RiseGuideJig(sender, actionID);
			this.currentAction = waitingResponse.getActionType();
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}

	public String visionInspect(String sender, String actionID) {
		if(waitingResponse == null && currentAction == null) {
			this.waitingResponse = new VisionInspect(sender, actionID);
			this.currentAction = waitingResponse.getActionType();
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}

	public String checkLabel(String sender, String actionID) {
		if(waitingResponse == null && currentAction == null) {
			this.waitingResponse = new CheckLabel(sender, actionID);
			this.currentAction = waitingResponse.getActionType();
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}
	}
