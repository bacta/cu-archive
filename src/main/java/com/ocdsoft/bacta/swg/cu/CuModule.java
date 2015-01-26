package com.ocdsoft.bacta.swg.cu;

import com.google.inject.AbstractModule;
import com.google.inject.Module;


public class CuModule extends AbstractModule implements Module {

	@Override
	protected void configure() {



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
