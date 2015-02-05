package com.ocdsoft.bacta.swg.cu.controller.login.server;

import com.google.inject.Inject;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.security.authenticator.AccountService;
import com.ocdsoft.bacta.soe.SwgController;
import com.ocdsoft.bacta.soe.SwgMessageController;
import com.ocdsoft.bacta.soe.annotation.RolesAllowed;
import com.ocdsoft.bacta.soe.connection.SoeUdpConnection;
import com.ocdsoft.bacta.soe.object.account.SoeAccount;
import com.ocdsoft.bacta.soe.service.ClusterService;
import com.ocdsoft.bacta.swg.cu.message.ErrorMessage;
import com.ocdsoft.bacta.swg.cu.message.login.client.LoginClientId;
import com.ocdsoft.bacta.swg.cu.message.login.server.EnumerateCharacterId;
import com.ocdsoft.bacta.swg.cu.message.login.server.LoginClientToken;
import com.ocdsoft.bacta.swg.cu.message.login.server.LoginClusterStatus;
import com.ocdsoft.bacta.swg.cu.message.login.server.LoginEnumCluster;
import com.ocdsoft.bacta.swg.cu.object.login.ClusterEntry;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SwgController(LoginClientId.class)
@RolesAllowed({})
public class LoginClientIdController implements SwgMessageController<LoginClientId> {

    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    private final int timezone;

    private final ClusterService<ClusterEntry> clusterService;
    private final AccountService<SoeAccount> accountService;

    private final String requiredClientVersion;

    @Inject
    public LoginClientIdController(final BactaConfiguration configuration,
                                   final ClusterService<ClusterEntry> clusterService,
                                   final AccountService<SoeAccount> accountService) {

        this.clusterService = clusterService;
        this.accountService = accountService;
        requiredClientVersion = configuration.getString("Bacta/GameServer", "ClientVersion");
        timezone = DateTimeZone.getDefault().getOffset(null) / 1000;
    }

    @Override
    public void handleIncoming(SoeUdpConnection connection, LoginClientId message) {

        // Validate client version
        if (!isRequiredVersion(message.getClientVersion())) {
            ErrorMessage error = new ErrorMessage("Login Error", "The client you are attempting to connect with does not match that required by the server.", false);
            connection.sendMessage(error);
            return;
        }

        if (message.getPassword().isEmpty()) {
            ErrorMessage error = new ErrorMessage("Login Error", "Please enter a password.", false);
            connection.sendMessage(error);
            return;
        }

        SoeAccount account = null;
        try {

            account = accountService.getAccount(message.getUsername());

        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage("Login Error", "Duplicate Accounts in the database", false);
            connection.sendMessage(error);
            logger.error("Duplicate accounts in database", e);

            return;
        }

        if (account == null) {
            account = accountService.createAccount(message.getUsername(), message.getPassword());
            if (account == null) {
                ErrorMessage error = new ErrorMessage("Login Error", "Unable to create account.", false);
                connection.sendMessage(error);
                return;
            }
        } else if (!accountService.authenticate(account, message.getPassword())) {
            ErrorMessage error = new ErrorMessage("Login Error", "Invalid username or password.", false);
            connection.sendMessage(error);
            return;
        }

        accountService.createAuthToken(account);
        connection.setAccountId(account.getId());
        connection.setAccountUsername(account.getUsername());

        LoginClientToken token = new LoginClientToken(account.getAuthToken(), account.getId(), account.getUsername());
        connection.sendMessage(token);

        LoginEnumCluster cluster = new LoginEnumCluster(clusterService.getClusterEntries(), timezone);
        connection.sendMessage(cluster);

        LoginClusterStatus status = new LoginClusterStatus(clusterService.getClusterEntries());
        connection.sendMessage(status);

        EnumerateCharacterId characters = new EnumerateCharacterId(account);
        connection.sendMessage(characters);
    }

    private boolean isRequiredVersion(String clientVersion) {
        return clientVersion.equals(requiredClientVersion);
    }

}
