package com.ocdsoft.bacta.swg.cu.controller.game.server;

import com.ocdsoft.bacta.soe.SwgController;
import com.ocdsoft.bacta.soe.SwgMessageController;
import com.ocdsoft.bacta.soe.annotation.RolesAllowed;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.swg.cu.message.game.client.LagReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SwgController(LagReport.class)
@RolesAllowed({})
public class LagReportController implements SwgMessageController<LagReport> {

    private static final Logger logger = LoggerFactory.getLogger(LagReportController.class.getSimpleName());

    @Override
    public void handleIncoming(SoeUdpConnection connection, LagReport message) throws Exception {


        logger.debug("Lag Report: " + message.getValue1() + " " + message.getValue2());    }
}

