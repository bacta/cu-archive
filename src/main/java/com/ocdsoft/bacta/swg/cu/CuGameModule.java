package com.ocdsoft.bacta.swg.cu;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.ocdsoft.bacta.soe.ServerState;
import com.ocdsoft.bacta.soe.io.udp.game.GameTransceiverFactory;


public class CuGameModule extends AbstractModule implements Module {

	@Override
	protected void configure() {
        bind(ServerState.class).to(CuGameServerState.class);
        install(new FactoryModuleBuilder().build(GameTransceiverFactory.class));
    }

}
