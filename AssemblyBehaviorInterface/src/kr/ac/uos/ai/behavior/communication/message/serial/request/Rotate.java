package kr.ac.uos.ai.behavior.communication.message.serial.request;

import kr.ac.uos.ai.arbi.model.Expression;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.behavior.communication.message.serial.SerialMessage;
import kr.ac.uos.ai.behavior.communication.message.serial.response.GripperAckMessage;
import kr.ac.uos.ai.behavior.communication.message.serial.response.GripperStatusMessage;
import kr.ac.uos.ai.behavior.communication.message.value.ActionType;
import kr.ac.uos.ai.behavior.communication.message.value.GripperRotation;
import kr.ac.uos.ai.behavior.communication.message.value.Item;

public class Rotate extends SerialMessage {

	private GripperRotation rotation;
	
	public Rotate(String sender, String actionID, String action) {
		this(sender, actionID, GripperRotation.valueOf(action));
	}
	private Rotate(String sender, String actionID, GripperRotation rotation) {
		super(sender, ActionType.Rotate, actionID);
		this.rotation = rotation;
	}

	public String getMessage() {
		String message = null;
		if(rotation == GripperRotation.On) {
			message = "<OUT03=1/>";
		} else if (rotation == GripperRotation.Off) {
			message = "<OUT03=0/>";
		}
		return message;
	}

	@Override
	public String makeResponse() {
		GeneralizedList gl = null;
		Expression actionID = null;
		Expression actionResult = null;
		if (responseMessage instanceof GripperAckMessage) {
			GripperAckMessage m = (GripperAckMessage) responseMessage;
			if (rotation == GripperRotation.On && m.getResponse().equals("<OUT03=1_ACK/>")) {
				gl = GLFactory.newGL("ok");
			} else if (rotation == GripperRotation.Off && m.getResponse().equals("<OUT03=0_ACK/>")) {
				gl = GLFactory.newGL("ok");
			}
		} else if(responseMessage instanceof GripperStatusMessage) {
			actionID = GLFactory.newExpression(GLFactory.newValue(this.getActionID()));
			String[] response = ((GripperStatusMessage) responseMessage).getResponse();
			if (this.rotation == GripperRotation.On && response[7].equals("1")) {
				actionResult = GLFactory.newExpression(GLFactory.newValue("success"));
			} else if (this.rotation == GripperRotation.Off && response[6].equals("1")) {
				actionResult = GLFactory.newExpression(GLFactory.newValue("success"));
			}
			gl = GLFactory.newGL("ActionResult", actionID, actionResult);
		} else {
			System.out.println("something is wrong");
			gl = GLFactory.newGL("fail");
		}
		return gl.toString();
	}

}
