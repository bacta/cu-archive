package com.ocdsoft.bacta.swg.cu.controller.game.object;

import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.cu.message.game.object.DataTransform;
import com.ocdsoft.bacta.swg.cu.object.tangible.TangibleObject;
import com.ocdsoft.bacta.swg.object.ObjController;
import com.ocdsoft.bacta.swg.object.ObjControllerHandled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ObjControllerHandled(DataTransform.class)
public class DataTransformObjController implements ObjController<DataTransform, TangibleObject> {

	private static final Logger logger = LoggerFactory.getLogger(DataTransformObjController.class);
	
	@Override
	public void handleIncoming(final SoeUdpConnection connection, final DataTransform message, final TangibleObject invoker) {

		logger.warn("This object controller is not implemented");
		
	}
}
