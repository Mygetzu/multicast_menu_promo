package client;

import model.ListMenuMakan;
import model.ListPromoMenu;
import model.MenuMakan;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private static final String INETADDR    = "224.0.0.3";
    private static final int    PORT        = 8888;
    private static DatagramSocket           clientSocket;
    private static DatagramPacket           dgramPacketSend;
    private static DatagramPacket           dgramPacketReceive;
    private static byte[]                   dataPacket      = null;
    private static byte[]                   data            = null;

    private static ObjectOutputStream       oos             = null;
    private static ByteArrayOutputStream    baos            = null;
    private static ObjectInputStream        ois             = null;
    private static ByteArrayInputStream     bais            = null;

    private static List<MenuMakan>          menuMakanList   = new ArrayList<>();
    private static ListMenuMakan            listMenuMakan   = null;

    public static void main(String args[]) throws Exception {
        InetAddress address = InetAddress.getByName(INETADDR);
        baos        = new ByteArrayOutputStream();
        oos         = new ObjectOutputStream(baos);

        dataPacket  = new byte[1024];

        try (MulticastSocket clientSocket = new MulticastSocket(PORT)){
            clientSocket.joinGroup(address);

            while (true) {
                dgramPacketReceive  = new DatagramPacket(dataPacket, dataPacket.length);
                clientSocket.receive(dgramPacketReceive);

                data    = dgramPacketReceive.getData();
                bais    = new ByteArrayInputStream(data);
                ois     = new ObjectInputStream(bais);

                try {
                    listMenuMakan   = (ListMenuMakan) ois.readObject();

                    System.out.println(
                            "\nDaftar Terbaru Menu :\nID. \tMenu \tHarga \tPromo\n"
                    );

                    double promo;
                    for (int i = 0; i < listMenuMakan.getMenuMakanList().size(); i++) {
                        promo = 0;
                        System.out.println(
                                listMenuMakan.getMenuMakanList().get(i).getId() + ". \t\t" +
                                        listMenuMakan.getMenuMakanList().get(i).getNama_menu() + " \t" +
                                        listMenuMakan.getMenuMakanList().get(i).getHarga() + " \t"
                        );

//                        for (int j = 0; j < listPromoMenu.getPromoMenuList().size(); j++) {
//                            if (listPromoMenu.getPromoMenuList().get(j).getId_promo() ==
//                                    listMenuMakan.getMenuMakanList().get(i).getId()) {
//                                System.out.println(listPromoMenu.getPromoMenuList().get(i).getId_promo());
//                                System.out.println(listMenuMakan.getMenuMakanList().get(i).getId());
//                                promo = listPromoMenu.getPromoMenuList().get(j).getDiskon();
//                                break;
//                            }
//                        }
//                        System.out.println(promo + "%");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
