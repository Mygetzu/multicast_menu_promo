package client;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Client {
    private static final String INETADDR    = "224.0.0.3";
    private static final int    PORT        = 8888;

    public static void main(String args[]) throws Exception {
        InetAddress address = InetAddress.getByName(INETADDR);

        byte[] bufBytes = new byte[20];

        try (MulticastSocket clientSocket = new MulticastSocket(PORT)){
            clientSocket.joinGroup(address);

            while (true) {
                DatagramPacket dgramPacket = new DatagramPacket(bufBytes, bufBytes.length);
                clientSocket.receive(dgramPacket);

                String msg  = new String(bufBytes, 0, bufBytes.length);
                System.out.println("Received msg : " + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
