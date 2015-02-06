package com.ocdsoft.bacta.swg.cu.controller.login.server;

import com.ocdsoft.bacta.soe.GameNetworkMessageController;
import com.ocdsoft.bacta.soe.GameNetworkMessageHandled;
import com.ocdsoft.bacta.soe.RolesAllowed;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.cu.message.login.client.RequestExtendedClusterInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GameNetworkMessageHandled(RequestExtendedClusterInfo.class)
@RolesAllowed({})
public class RequestExtendedClusterInfoController implements GameNetworkMessageController<RequestExtendedClusterInfo> {

    private static Logger logger = LoggerFactory.getLogger(RequestExtendedClusterInfoController.class.getSimpleName());

    @Override
    public void handleIncoming(SoeUdpConnection loginConnection, RequestExtendedClusterInfo message) throws Exception {
        logger.warn("This controller is not implemented");
    }
}

