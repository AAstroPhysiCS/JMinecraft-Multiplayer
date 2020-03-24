package Server;

import Client.Network.ClientPlayerInfo;
import Tools.Network;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Server extends Network {

    private static final long seed = ThreadLocalRandom.current().nextInt(0, 1000000);

    public static void main(String[] args) {
        try {
            new Server(InetAddress.getLocalHost(), 8000);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public Server(InetAddress address, int PORT) {
        super(address, PORT);

        try {
            socket = new DatagramSocket(PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        running = true;
        thread.start();
        System.out.println("Server running on address: " + address + ", port: " + PORT);
    }

    @Override
    public Runnable run() {
        return () -> {

            int oldConnectedClientSize = 1;

            while (running) {
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                try {
                    socket.receive(receivePacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ClientPlayerInfo clientPlayer = (ClientPlayerInfo) packetFactory.readFromPacket(receivePacket.getData());

                if (connectedClients.size() == 0) {
                    connectedClients.add(new ConnectedClient(clientPlayer.getPos(), clientPlayer.getId()));
                } else {
                    int counter = 0;
                    for (int i = 0; i < connectedClients.size(); i++) {
                        if (connectedClients.get(i).getId() != clientPlayer.getId()) {
                            counter++;
                            if (counter == connectedClients.size()) {
                                connectedClients.add(new ConnectedClient(clientPlayer.getPos(), clientPlayer.getId()));
                            }
                        } else {
                            ConnectedClient client = connectedClients.get(i);
                            client.setPos(clientPlayer.getPos());
                            client.setRemovedBlock(clientPlayer.getRemovedBlock());
                            client.setAddedBlock(clientPlayer.getAddedBlock());
                        }
                    }
                }

                List<ConnectedClient> connectedClientsFiltered = connectedClients.stream().filter(client -> client.getId() != clientPlayer.getId()).collect(Collectors.toList());

                if (oldConnectedClientSize != connectedClientsFiltered.size()) {
                    sendDataToClient(seed, receivePacket);
                    oldConnectedClientSize = connectedClientsFiltered.size();
                }
                sendDataToClient(connectedClientsFiltered, receivePacket);
            }
        };
    }

    private <T> void sendDataToClient(T obj, DatagramPacket receivePacket) {
        DatagramPacket sendPacket = packetFactory.writeToPacket(obj, receivePacket.getAddress(), receivePacket.getPort());
        try {
            socket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
