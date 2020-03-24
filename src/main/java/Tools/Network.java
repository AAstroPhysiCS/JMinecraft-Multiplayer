package Tools;

import Server.ConnectedClient;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public abstract class Network {

    protected List<ConnectedClient> connectedClients = new ArrayList<>();

    protected DatagramSocket socket;
    protected InetAddress address;
    protected int PORT;

    protected Thread thread;
    protected boolean running;

    protected byte[] buffer = new byte[65535]; //max protocol size

    protected PacketFactory<? super Object> packetFactory = new PacketFactory<>();

    public Network(final InetAddress address, final int PORT) {
        this.address = address;
        this.PORT = PORT;

        thread = new Thread(run(), getClass().getName() + " Thread!");
    }

    protected abstract Runnable run();
}
