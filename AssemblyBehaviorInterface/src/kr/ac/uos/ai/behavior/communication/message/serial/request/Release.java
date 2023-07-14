package kr.ac.uos.ai.behavior.communication.message.serial.request;

import kr.ac.uos.ai.arbi.model.Expression;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.behavior.communication.message.serial.SerialMessage;
import kr.ac.uos.ai.behavior.communication.message.serial.response.GripperResponseMessage;
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
		if (responseMessage == null) {
			gl = GLFactory.newGL("ok");
		} else if(responseMessage instanceof GripperResponseMessage) {
			actionID = GLFactory.newExpression(GLFactory.newValue(this.getActionID()));
			String[] response = ((GripperResponseMessage) responseMessage).getResponse();
			if (item == Item.PCB && response[2].equals("1")) {
				actionResult = GLFactory.newExpression(GLFactory.newValue("success"));
			} else if (item == Item.Housing && response[5].equals("1")) {
				actionResult = GLFactory.newExpression(GLFactory.newValue("success"));
			} else if (item == Item.Lens && response[2].equals("1")) {
				actionResult = GLFactory.newExpression(GLFactory.newValue("success"));
			} else if (item == Item.Front && response[5].equals("1")) {
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
