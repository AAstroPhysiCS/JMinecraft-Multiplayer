package Client.Network;

import Client.Engine.Engine;
import Client.Game.Entity.Player;
import Client.Game.World.World;
import Server.ConnectedClient;
import Tools.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

public class Client extends Network {

    private World world;
    private final Engine engine;

    public static long seed;

    public Client(InetAddress address, int PORT, Engine engine) {
        super(address, PORT);
        this.engine = engine;

        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        running = true;
        thread.start();
        System.out.println("Connected to " + address + ", port: " + PORT);
    }

    @Override
    protected Runnable run() {
        return () -> {

            while (running) {
                if (world == null)
                    world = engine.getWorld();
                else {
                    Player player = world.getPlayer();
                    DatagramPacket sendPacket = packetFactory.writeToPacket(new ClientPlayerInfo(player.getCamera().getPosition(), (byte) 1, player.getRemovedBlock(), player.getAddedBlock()), address, PORT);
                    try {
                        socket.send(sendPacket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                    try {
                        socket.receive(receivePacket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Object incomingObj = packetFactory.readFromPacket(receivePacket.getData());
                    if(incomingObj instanceof Long){
                        seed = (Long) incomingObj;
                    } else {
                        List<ConnectedClient> connectedClients = (List<ConnectedClient>) incomingObj;
                        world.setConnectedPlayers(connectedClients);
                    }
                    try {
                        Thread.sleep(1000/144);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}
