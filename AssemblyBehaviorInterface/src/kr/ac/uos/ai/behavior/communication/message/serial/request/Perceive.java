package kr.ac.uos.ai.behavior.communication.message.serial.request;

import kr.ac.uos.ai.arbi.model.Expression;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.behavior.communication.message.serial.SerialMessage;
import kr.ac.uos.ai.behavior.communication.message.serial.response.GripperStatusMessage;
import kr.ac.uos.ai.behavior.communication.message.value.ActionType;
import kr.ac.uos.ai.behavior.communication.message.value.Item;

public class Perceive extends SerialMessage {

	private Item item;
	
	public Perceive(String sender, String actionID, String item) {
		this(sender, actionID, Item.valueOf(item));
	}
	
	private Perceive(String sender, String actionID, Item item) {
		super(sender, ActionType.Perceive, actionID);
		this.item = item;
	}

	@Override
	public String getMessage() {

		return "<I>CHECK</I>";
	}
	
	@Override
	public String makeResponse() {
		GeneralizedList gl = null;
		Expression actionID = null;
		Expression actionResult = null;
		if (responseMessage == null) {
			gl = GLFactory.newGL("ok");
		} else if(responseMessage instanceof GripperStatusMessage) {
			actionID = GLFactory.newExpression(GLFactory.newValue(this.getActionID()));
			String[] response = ((GripperStatusMessage) responseMessage).getResponse();
			if (item == Item.PCB && response[0].equals("1")) {
				actionResult = GLFactory.newExpression(GLFactory.newValue("success"));
			} else if (item == Item.Housing && response[3].equals("1")) {
				actionResult = GLFactory.newExpression(GLFactory.newValue("success"));
			} else if (item == Item.Lens && response[0].equals("1")) {
				actionResult = GLFactory.newExpression(GLFactory.newValue("success"));
			} else if (item == Item.Front && response[3].equals("1")) {
				actionResult = GLFactory.newExpression(GLFactory.newValue("success"));
			} else {
				actionResult = GLFactory.newExpression(GLFactory.newValue("fail"));
			}
			gl = GLFactory.newGL("ActionResult", actionID, actionResult);
		}  else {
			System.out.println("something is wrong " + responseMessage.toString());
			gl = GLFactory.newGL("fail");
		}
		
		return gl.toString();
	}


}
