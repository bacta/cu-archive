package com.ocdsoft.bacta.swg.cu.controller.login.server;

import com.ocdsoft.bacta.soe.RolesAllowed;
import com.ocdsoft.bacta.soe.SwgController;
import com.ocdsoft.bacta.soe.SwgMessageController;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.cu.message.login.client.RequestExtendedClusterInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SwgController(RequestExtendedClusterInfo.class)
@RolesAllowed({})
public class RequestExtendedClusterInfoController implements SwgMessageController<RequestExtendedClusterInfo> {

    private static Logger logger = LoggerFactory.getLogger(RequestExtendedClusterInfoController.class.getSimpleName());

    @Override
    public void handleIncoming(SoeUdpConnection loginConnection, RequestExtendedClusterInfo message) throws Exception {
        logger.warn("This controller is not implemented");
    }
}

