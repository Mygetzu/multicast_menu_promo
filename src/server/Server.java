package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    private static final String     INETADDR    = "224.0.03";
    private static final int        PORT        = 8888;
    private static InetAddress      inetAddress;
    private static BufferedReader   userEntry;
    private static DatagramSocket   servSocket;
    private static DatagramPacket   dgramPacket;

    public static void main(String args[]) throws Exception {
        try {
            inetAddress = InetAddress.getByName(INETADDR);
            servSocket  = new DatagramSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
        run();
    }

    private static void run() {
        try {
            userEntry   = new BufferedReader(new InputStreamReader(System.in));
            String msg  = "";

            do {
                System.out.print("Input message : ");
                msg = userEntry.readLine();

                dgramPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, inetAddress, PORT);
                servSocket.send(dgramPacket);

                System.out.println("Server packet sent\n");

                Thread.sleep(500);
            } while (true);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Closing Connection");
            servSocket.close();
        }
    }
}
