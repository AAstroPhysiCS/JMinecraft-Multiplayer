package Tools;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class PacketFactory<T> {

    public PacketFactory() {
    }

    public DatagramPacket writeToPacket(T obj, InetAddress address, final int port) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
            byte[] data = byteArrayOutputStream.toByteArray();
            return new DatagramPacket(data, data.length, address, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public T readFromPacket(byte[] data) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data)) {
            ObjectInput objectOutputStream = new ObjectInputStream(byteArrayInputStream);
            T obj = (T) objectOutputStream.readObject();
            objectOutputStream.close();
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
