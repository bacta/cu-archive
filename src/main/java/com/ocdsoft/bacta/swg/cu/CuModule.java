package com.ocdsoft.bacta.swg.cu;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.conf.ini.IniBactaConfiguration;
import com.ocdsoft.bacta.soe.io.udp.game.GameServerState;
import com.ocdsoft.bacta.soe.router.SoeMessageRouter;
import com.ocdsoft.bacta.soe.router.SoeMessageRouterImpl;
import com.ocdsoft.bacta.soe.router.SwgMessageRouter;
import com.ocdsoft.bacta.soe.service.SessionKeyService;
import com.ocdsoft.bacta.soe.service.impl.SWGSessionKeyService;
import com.ocdsoft.bacta.swg.router.SwgDevelopMessageRouter;


public class CuModule extends AbstractModule implements Module {

	@Override
	protected void configure() {

        bind(BactaConfiguration.class).to(IniBactaConfiguration.class);
        bind(SessionKeyService.class).to(SWGSessionKeyService.class);
        bind(SoeMessageRouter.class).to(SoeMessageRouterImpl.class);

        bind(GameServerState.class).to(CuGameServerState.class);
        bind(SwgMessageRouter.class).to(SwgDevelopMessageRouter.class);

        //bind(new TypeLiteral<ClusterService<ClusterEntry>>(){}).asEagerSingleton();

        /*bind(NetworkObjectFactory.class).to(GuiceNetworkObjectFactory.class);
        bind(SchedulerService.class).to(TaskSchedulerService.class);

        bind(DatabaseConnector.class).to(CouchbaseDatabaseConnector.class);
        bind(NetworkIdGenerator.class).to(CouchbaseNetworkIdGenerator.class);
        bind(PasswordHash.class).to(Pbkdf2SaltedPasswordHash.class);
        bind(NetworkSerializer.class).to(GameObjectSerializer.class);
        bind(new TypeLiteral<ObjectService<SceneObject>>() {}).to(SceneObjectService.class);
        bind(ObjectService.class).to(SceneObjectService.class);
        bind(new TypeLiteral<AccountService<SoeAccount>>(){}).to(CouchbaseAccountService.class);
        bind(Account.class).to(SoeAccount.class);
        bind(PasswordHash.class).to(Pbkdf2SaltedPasswordHash.class);
        */

    }

}
