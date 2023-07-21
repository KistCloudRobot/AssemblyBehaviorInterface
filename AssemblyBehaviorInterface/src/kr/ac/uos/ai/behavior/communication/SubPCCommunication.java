package kr.ac.uos.ai.behavior.communication;

import kr.ac.uos.ai.behavior.BehaviorInterface;
import kr.ac.uos.ai.behavior.communication.message.serial.SerialMessage;
import kr.ac.uos.ai.behavior.communication.message.serial.request.CheckLabel;
import kr.ac.uos.ai.behavior.communication.message.serial.request.CheckSubPC;
import kr.ac.uos.ai.behavior.communication.message.serial.request.RiseGuideJig;
import kr.ac.uos.ai.behavior.communication.message.serial.request.VisionInspect;
import kr.ac.uos.ai.behavior.communication.message.serial.response.SubPCResponseMessage;

public class SubPCCommunication extends SerialCommunication {

	private SerialMessage waitingResponse;
	
	public SubPCCommunication(BehaviorInterface bi, String portName) {
		super(bi, portName);
	}

	@Override
	public void onMessage(String message) {
		message = removeEndLineMarker(message);
		if (this.waitingResponse != null && message.startsWith("<")) {
			SubPCResponseMessage m = parseMessage(message);
			this.waitingResponse.setResponse(m);
			behaviorInterface.sendMessage(waitingResponse.getSender(), waitingResponse.getResponse());
			
			this.waitingResponse = null;
		} else logger.warning("[SubPCCommunication] wrong message : " + message);
	}
	
	private SubPCResponseMessage parseMessage(String message) {
		if (message.startsWith("<CHK>")) {
			return new SubPCResponseMessage(message);	
		} else logger.warning("[SubPCCommunication] parsing failed : " + message);
		return null;
	}
	
	public String checkSubPC(String sender, String actionID) {
		if(waitingResponse == null) {
			this.waitingResponse = new CheckSubPC(sender, actionID);
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}

	
	public String riseGuideJig(String sender, String actionID) {
		if(waitingResponse == null) {
			this.waitingResponse = new RiseGuideJig(sender, actionID);
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}

	public String visionInspect(String sender, String actionID) {
		if(waitingResponse == null) {
			this.waitingResponse = new VisionInspect(sender, actionID);
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}

	public String checkLabel(String sender, String actionID) {
		if(waitingResponse == null) {
			this.waitingResponse = new CheckLabel(sender, actionID);
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}
}
