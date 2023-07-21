package kr.ac.uos.ai.behavior.communication.message.serial.request;

import kr.ac.uos.ai.arbi.model.Expression;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.behavior.communication.message.serial.SerialMessage;
import kr.ac.uos.ai.behavior.communication.message.serial.response.GripperAckMessage;
import kr.ac.uos.ai.behavior.communication.message.serial.response.GripperStatusMessage;
import kr.ac.uos.ai.behavior.communication.message.value.ActionType;
import kr.ac.uos.ai.behavior.communication.message.value.Item;

public class Release extends SerialMessage {

	private Item item;
	
	public Release(String sender, String actionID, String item) {
		this(sender, actionID, Item.valueOf(item));
	}
	
	private Release(String sender,  String actionID, Item item) {
		super(sender, ActionType.Release, actionID);
		this.item = item;
	}

	public String getMessage() {
		String message = null;
		if (item == Item.PCB) {
			message = "<OUT01=0/>";
		} else if (item == Item.Housing) {
			message = "<OUT02=0/>";
		} else if (item == Item.Lens) {
			message = "<OUT01=0/>";
		} else if (item == Item.Front) {
			message = "<OUT02=0/>";
		}
		return message;
	}
	
	public Item getItem() {
		return item;
	}

	@Override
	public String makeResponse() {
		GeneralizedList gl = null;
		Expression actionID = null;
		Expression actionResult = null;
		if (responseMessage instanceof GripperAckMessage) {
			GripperAckMessage m = (GripperAckMessage) responseMessage;
			if (item == Item.PCB && m.getResponse().equals("<OUT01=0_ACK/>")) {
				gl = GLFactory.newGL("ok");
			} else if (item == Item.Housing && m.getResponse().equals("<OUT02=0_ACK/>")) {
				gl = GLFactory.newGL("ok");
			} else if (item == Item.Lens && m.getResponse().equals("<OUT01=0_ACK/>")) {
				gl = GLFactory.newGL("ok");
			} else if (item == Item.Front && m.getResponse().equals("<OUT02=0_ACK/>")) {
				gl = GLFactory.newGL("ok");
			}
		} else if(responseMessage instanceof GripperStatusMessage) {
			actionID = GLFactory.newExpression(GLFactory.newValue(this.getActionID()));
			String[] response = ((GripperStatusMessage) responseMessage).getResponse();
			if (item == Item.PCB && response[1].equals("1")) {
				actionResult = GLFactory.newExpression(GLFactory.newValue("success"));
			} else if (item == Item.Housing && response[4].equals("1")) {
				actionResult = GLFactory.newExpression(GLFactory.newValue("success"));
			} else if (item == Item.Lens && response[1].equals("1")) {
				actionResult = GLFactory.newExpression(GLFactory.newValue("success"));
			} else if (item == Item.Front && response[4].equals("1")) {
				actionResult = GLFactory.newExpression(GLFactory.newValue("success"));
			}	
			gl = GLFactory.newGL("ActionResult", actionID, actionResult);
		}  else {
			System.out.println("something is wrong");
			gl = GLFactory.newGL("fail");
		}
		
		return gl.toString();
	}

}
