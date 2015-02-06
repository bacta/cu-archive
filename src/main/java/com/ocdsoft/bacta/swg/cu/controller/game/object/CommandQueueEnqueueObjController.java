package com.ocdsoft.bacta.swg.cu.controller.game.object;

import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.cu.message.game.object.CommandQueueEnqueue;
import com.ocdsoft.bacta.swg.cu.object.game.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.object.ObjController;
import com.ocdsoft.bacta.swg.object.ObjControllerHandled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ObjControllerHandled(CommandQueueEnqueue.class)
public class CommandQueueEnqueueObjController implements ObjController<CommandQueueEnqueue, TangibleObject> {

	private static final Logger logger = LoggerFactory.getLogger(CommandQueueEnqueueObjController.class);
	
	@Override
	public void handleIncoming(final SoeUdpConnection connection, final CommandQueueEnqueue message, final TangibleObject invoker) {

		logger.warn("This object controller is not implemented");
		
	}
}
