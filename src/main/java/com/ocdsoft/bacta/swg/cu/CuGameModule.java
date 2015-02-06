package com.ocdsoft.bacta.swg.cu;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.conf.ini.IniBactaConfiguration;
import com.ocdsoft.bacta.engine.data.ConnectionDatabaseConnector;
import com.ocdsoft.bacta.engine.data.GameDatabaseConnector;
import com.ocdsoft.bacta.engine.object.NetworkIdGenerator;
import com.ocdsoft.bacta.engine.security.authenticator.AccountService;
import com.ocdsoft.bacta.engine.security.password.PasswordHash;
import com.ocdsoft.bacta.engine.security.password.Pbkdf2SaltedPasswordHash;
import com.ocdsoft.bacta.engine.serialization.NetworkSerializer;
import com.ocdsoft.bacta.engine.service.object.ObjectService;
import com.ocdsoft.bacta.engine.service.objectfactory.NetworkObjectFactory;
import com.ocdsoft.bacta.engine.service.objectfactory.impl.GuiceNetworkObjectFactory;
import com.ocdsoft.bacta.soe.ServerState;
import com.ocdsoft.bacta.soe.connection.ConnectionServerAgent;
import com.ocdsoft.bacta.soe.data.couchbase.CouchbaseAccountService;
import com.ocdsoft.bacta.soe.data.couchbase.CouchbaseConnectionDatabaseConnector;
import com.ocdsoft.bacta.soe.data.couchbase.CouchbaseGameDatabaseConnector;
import com.ocdsoft.bacta.soe.data.couchbase.CouchbaseNetworkIdGenerator;
import com.ocdsoft.bacta.soe.io.udp.game.GameServer;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.soe.object.account.SoeAccount;
import com.ocdsoft.bacta.soe.service.OutgoingConnectionService;
import com.ocdsoft.bacta.soe.service.SessionKeyService;
import com.ocdsoft.bacta.soe.service.impl.SWGSessionKeyService;
import com.ocdsoft.bacta.swg.cu.connection.CuConnectionServerAgent;
import com.ocdsoft.bacta.swg.cu.data.GameObjectSerializer;
import com.ocdsoft.bacta.swg.cu.object.CuObjectTemplateService;
import com.ocdsoft.bacta.swg.cu.object.CuSetupSharedFile;
import com.ocdsoft.bacta.swg.cu.object.SceneObjectService;
import com.ocdsoft.bacta.swg.cu.object.game.SceneObject;
import com.ocdsoft.bacta.swg.cu.object.login.ClusterEntry;
import com.ocdsoft.bacta.swg.data.ObjectTemplateService;
import com.ocdsoft.bacta.swg.data.SetupSharedFile;
import com.ocdsoft.bacta.swg.data.SharedFileService;
import com.ocdsoft.bacta.swg.name.DefaultNameService;
import com.ocdsoft.bacta.swg.name.NameService;
import com.ocdsoft.bacta.tre.TreeFile;


public class CuGameModule extends AbstractModule implements Module {

	@Override
	protected void configure() {
        bind(TreeFile.class).asEagerSingleton();
        bind(SetupSharedFile.class).to(CuSetupSharedFile.class).asEagerSingleton();
        bind(new TypeLiteral<ObjectTemplateService<SceneObject>>() {
        }).to(CuObjectTemplateService.class);
        bind(OutgoingConnectionService.class).to(GameServer.CuOutgoingConnectionService.class);
        bind(new TypeLiteral<ObjectService<SceneObject>>() {
        }).to(SceneObjectService.class);
        bind(ObjectService.class).to(SceneObjectService.class);
        bind(BactaConfiguration.class).to(IniBactaConfiguration.class);
        bind(SharedFileService.class).asEagerSingleton();
        bind(SessionKeyService.class).to(SWGSessionKeyService.class);
        bind(ConnectionDatabaseConnector.class).to(CouchbaseConnectionDatabaseConnector.class);
        bind(GameDatabaseConnector.class).to(CouchbaseGameDatabaseConnector.class);
        bind(NetworkSerializer.class).to(GameObjectSerializer.class);
        bind(NetworkIdGenerator.class).to(CouchbaseNetworkIdGenerator.class);
        bind(NetworkObjectFactory.class).to(GuiceNetworkObjectFactory.class);
        bind(NameService.class).to(DefaultNameService.class);
        bind(new TypeLiteral<AccountService<SoeAccount>>() {}).to(new TypeLiteral<CouchbaseAccountService<SoeAccount>>(){});
        bind(PasswordHash.class).to(Pbkdf2SaltedPasswordHash.class);
        bind(ServerState.class).to(CuGameServerState.class);
        bind(GameServerState.class).to(CuGameServerState.class);
        bind(new TypeLiteral<GameServerState<ClusterEntry>>(){}).to(CuGameServerState.class);
        bind(ConnectionServerAgent.class).to(CuConnectionServerAgent.class);
    }
}
