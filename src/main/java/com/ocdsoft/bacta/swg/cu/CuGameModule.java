package com.ocdsoft.bacta.swg.cu;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.conf.ini.IniBactaConfiguration;
import com.ocdsoft.bacta.engine.data.ConnectionDatabaseConnector;
import com.ocdsoft.bacta.engine.security.authenticator.AccountService;
import com.ocdsoft.bacta.engine.security.password.PasswordHash;
import com.ocdsoft.bacta.engine.security.password.Pbkdf2SaltedPasswordHash;
import com.ocdsoft.bacta.engine.serialization.NetworkSerializer;
import com.ocdsoft.bacta.soe.ServerState;
import com.ocdsoft.bacta.soe.connection.ConnectionServerAgent;
import com.ocdsoft.bacta.soe.data.couchbase.CouchbaseAccountService;
import com.ocdsoft.bacta.soe.data.couchbase.CouchbaseConnectionDatabaseConnector;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.soe.io.udp.game.GameTransceiverFactory;
import com.ocdsoft.bacta.soe.io.udp.login.LoginTransceiverFactory;
import com.ocdsoft.bacta.soe.object.account.SoeAccount;
import com.ocdsoft.bacta.soe.service.SessionKeyService;
import com.ocdsoft.bacta.soe.service.impl.SWGSessionKeyService;
import com.ocdsoft.bacta.swg.cu.connection.CuConnectionServerAgent;
import com.ocdsoft.bacta.swg.cu.data.GameObjectSerializer;
import com.ocdsoft.bacta.swg.cu.object.login.ClusterEntry;


public class CuGameModule extends AbstractModule implements Module {

	@Override
	protected void configure() {
        install(new FactoryModuleBuilder().build(GameTransceiverFactory.class));

        install(new FactoryModuleBuilder().build(LoginTransceiverFactory.class));

        bind(BactaConfiguration.class).to(IniBactaConfiguration.class);
        bind(SessionKeyService.class).to(SWGSessionKeyService.class);
        bind(ConnectionDatabaseConnector.class).to(CouchbaseConnectionDatabaseConnector.class);
        bind(NetworkSerializer.class).to(GameObjectSerializer.class);
        bind(new TypeLiteral<AccountService<SoeAccount>>(){}).to(new TypeLiteral<CouchbaseAccountService<SoeAccount>>(){});
        bind(PasswordHash.class).to(Pbkdf2SaltedPasswordHash.class);
        bind(ServerState.class).to(CuGameServerState.class);
        bind(GameServerState.class).to(CuGameServerState.class);
        bind(new TypeLiteral<GameServerState<ClusterEntry>>(){}).to(CuGameServerState.class);
        bind(ConnectionServerAgent.class).to(CuConnectionServerAgent.class);

    }

}
