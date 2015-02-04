package com.ocdsoft.bacta.swg.cu;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.conf.ini.IniBactaConfiguration;
import com.ocdsoft.bacta.engine.data.ConnectionDatabaseConnector;
import com.ocdsoft.bacta.engine.security.authenticator.AccountService;
import com.ocdsoft.bacta.engine.security.password.PasswordHash;
import com.ocdsoft.bacta.engine.security.password.Pbkdf2SaltedPasswordHash;
import com.ocdsoft.bacta.engine.serialization.NetworkSerializer;
import com.ocdsoft.bacta.soe.ServerState;
import com.ocdsoft.bacta.soe.chat.ChatServerState;
import com.ocdsoft.bacta.soe.data.couchbase.CouchbaseAccountService;
import com.ocdsoft.bacta.soe.data.couchbase.CouchbaseConnectionDatabaseConnector;
import com.ocdsoft.bacta.soe.object.account.SoeAccount;
import com.ocdsoft.bacta.soe.service.SessionKeyService;
import com.ocdsoft.bacta.soe.service.impl.SWGSessionKeyService;
import com.ocdsoft.bacta.swg.cu.data.GameObjectSerializer;

/**
 * Created by crush on 2/1/15.
 */
public class CuChatModule extends AbstractModule implements Module {
    @Override
    protected void configure() {
        bind(BactaConfiguration.class).to(IniBactaConfiguration.class);
        bind(SessionKeyService.class).to(SWGSessionKeyService.class);
        bind(ConnectionDatabaseConnector.class).to(CouchbaseConnectionDatabaseConnector.class);
        bind(NetworkSerializer.class).to(GameObjectSerializer.class);
        bind(new TypeLiteral<AccountService<SoeAccount>>(){}).to(new TypeLiteral<CouchbaseAccountService<SoeAccount>>(){});
        bind(PasswordHash.class).to(Pbkdf2SaltedPasswordHash.class);
        bind(ServerState.class).to(ChatServerState.class);
    }
}
