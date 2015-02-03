package com.ocdsoft.bacta.swg.cu;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ocdsoft.bacta.engine.conf.BactaConfiguration;
import com.ocdsoft.bacta.engine.conf.ini.IniBactaConfiguration;
import com.ocdsoft.bacta.soe.chat.ChatServer;
import com.ocdsoft.bacta.soe.io.udp.game.GameServer;
import com.ocdsoft.bacta.soe.io.udp.login.LoginServer;

import java.io.File;

/**
 * Created by kburkhardt on 12/29/14.
 */
public final class CuServer {

    public static void main(String[] args) {

        BactaConfiguration configuration = new IniBactaConfiguration();

        boolean runLogin = configuration.getBoolean("Bacta/LoginServer", "Enabled");
        boolean runGame = configuration.getBoolean("Bacta/GameServer", "Enabled");
        boolean runChat = configuration.getBoolean("Bacta/ChatServer", "enabled");
        
        String baseFilePath = CuServer.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        File file = new File(baseFilePath);

        System.setProperty("template.classpath", CuServer.class.getPackage().getName());
        System.setProperty("template.filepath", file.getParentFile().getParent());

        if (runChat) {
            Injector injector = Guice.createInjector(new CuChatModule());
            ChatServer chatServer = injector.getInstance(ChatServer.class);

            Thread chatThread = new Thread(chatServer);
            chatThread.setName("ChatThread");
            chatThread.start();
        }

        if (runLogin) {
            Injector loginInjector = Guice.createInjector(new CuLoginModule());
            LoginServer loginServer = loginInjector.getInstance(LoginServer.class);
            Thread loginThread = new Thread(loginServer);
            loginThread.setName("LoginThread");
            loginThread.start();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (runGame) {
            Injector gameInjector = Guice.createInjector(new CuGameModule());
            GameServer gameServer = gameInjector.getInstance(GameServer.class);
            Thread gameThread = new Thread(gameServer);
            gameThread.setName("GameThread");
            gameThread.start();
        }
    }
}
