package com.ocdsoft.bacta.swg.cu;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.soe.io.udp.login.LoginServer;

/**
 * Created by kburkhardt on 12/29/14.
 */
public final class CuServer {

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new CuModule());
        BactaConfiguration configuration = injector.getInstance(BactaConfiguration.class);

        boolean runLogin = configuration.getBoolean("Bacta/LoginServer", "Enabled");
        boolean runGame = configuration.getBoolean("Bacta/GameServer", "Enabled");

        if(runLogin) {
            Injector loginInjector = injector.createChildInjector(new CuLoginModule());
            LoginServer loginServer = loginInjector.getInstance(LoginServer.class);
            Thread loginThread = new Thread(loginServer);
            loginThread.setName("LoginThread");
            loginThread.start();
        }

//        if(runGame) {
//            Injector gameInjector = injector.createChildInjector(new CuGameModule());
//            GameServer gameServer = gameInjector.getInstance(GameServer.class);
//            Thread gameThread = new Thread(gameServer);
//            gameThread.setName("GameThread");
//            gameThread.start();
//        }
    }
}