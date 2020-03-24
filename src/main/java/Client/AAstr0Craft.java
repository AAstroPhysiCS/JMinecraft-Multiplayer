package Client;

import Client.Engine.Attributes.AntiAliasing;
import Client.Engine.Engine;
import Client.Engine.Screen.Screen;
import Client.Network.Client;

import java.net.InetAddress;
import java.net.UnknownHostException;

public final class AAstr0Craft {

    /**
     * @author Okan Güclü (Github: https://github.com/AAstroPhysiCS)
     * @since 02.01.2020
     **/

    public static void main(String[] args) {
        //insert here the name of the host pc
        Engine engine = new Engine(new Screen(1280, 720, "AAstr0Craft - Multiplayer"), true, AntiAliasing.ANTI_ALIASING_ON);
        try {
            new Client(InetAddress.getByName("OneTrueGod"), 8000, engine);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        engine.start();
    }
}
