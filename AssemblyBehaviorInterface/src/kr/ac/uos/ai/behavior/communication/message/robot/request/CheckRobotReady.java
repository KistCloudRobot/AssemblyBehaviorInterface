package kr.ac.uos.ai.behavior.communication.message.robot.request;

import kr.ac.uos.ai.arbi.model.Expression;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.behavior.communication.message.robot.RobotMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.RobotStatusMessage;
import kr.ac.uos.ai.behavior.communication.message.value.ActionType;
import kr.ac.uos.ai.behavior.communication.message.value.RobotID;
import kr.ac.uos.ai.behavior.communication.message.value.RobotState;

public class CheckRobotReady extends RobotMessage{

	private RobotID id;

	public CheckRobotReady(String sender, String actionID, RobotID id) {
		super(sender ,ActionType.CheckRobotReady, actionID, null);
		this.id = id;
	}

	@Override
	public String getMessage() {
		String result = "91,0,0," + id.getValue();
		return result;
	}
	
	@Override
	public String makeResponse() {
		String res = "(fail)";
		Expression actionID = GLFactory.newExpression(GLFactory.newValue(this.getActionID()));
		Expression actionResult = null;

		if (responseMessage instanceof RobotStatusMessage) {
			RobotState state = ((RobotStatusMessage) responseMessage).getState();
			RobotID responseID = ((RobotStatusMessage) responseMessage).getRobotID();
			if (responseID == id && state == RobotState.ready) {
				actionResult = GLFactory.newExpression(GLFactory.newValue("success"));
			} else {
				System.out.println("robot state : " + state);
				System.out.println("robot responseID : " + responseID);
				actionResult = GLFactory.newExpression(GLFactory.newValue("fail"));
			}
		}
		GeneralizedList gl = GLFactory.newGL("ActionResult", actionID, actionResult);
		res = gl.toString();
		return res;
	}
}
