package com.ocdsoft.bacta.swg.cu;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.conf.ini.IniBactaConfiguration;
import com.ocdsoft.bacta.soe.io.udp.game.GameServer;
import com.ocdsoft.bacta.soe.io.udp.login.LoginServer;

/**
 * Created by kburkhardt on 12/29/14.
 */
public final class CuServer {

    public static void main(String[] args) {

        BactaConfiguration configuration = new IniBactaConfiguration();

        boolean runLogin = configuration.getBoolean("Bacta/LoginServer", "Enabled");
        boolean runGame = configuration.getBoolean("Bacta/GameServer", "Enabled");

        if(runLogin) {
            Injector loginInjector = Guice.createInjector(new CuLoginModule());
            LoginServer loginServer = loginInjector.getInstance(LoginServer.class);
            Thread loginThread = new Thread(loginServer);
            loginThread.setName("LoginThread");
            loginThread.start();
        }

        if(runGame) {
            Injector gameInjector = Guice.createInjector(new CuGameModule());
            GameServer gameServer = gameInjector.getInstance(GameServer.class);
            Thread gameThread = new Thread(gameServer);
            gameThread.setName("GameThread");
            gameThread.start();
        }
    }
}
