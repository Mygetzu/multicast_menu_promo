package server;

import model.ListMenuMakan;
import model.ListPromoMenu;
import model.MenuMakan;
import model.PromoMenu;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final String     INETADDR    = "224.0.0.3";
    private static final int        PORT        = 8888;
    private static InetAddress      inetAddress;
    private static BufferedReader   userEntry   = new BufferedReader(new InputStreamReader(System.in));;
    private static DatagramSocket   servSocket;
    private static DatagramPacket   dgramPacketSend;
    private static DatagramPacket   dgramPacketReceive;
    private static byte[] dataPacket            = null;

    private static ObjectOutputStream      oos  = null;
    private static ByteArrayOutputStream   baos = null;
    private static ObjectInputStream       ois  = null;

    private static List<MenuMakan>  menuMakanList = new ArrayList<>();
    private static ListMenuMakan listMenuMakan    = new ListMenuMakan();
    private static ListMenuMakan listNewMenuMakan = new ListMenuMakan();

    public static void main(String args[]) throws Exception {
        try {
            if (menuMakanList == null || menuMakanList.size() == 0) {
                for (int i = 0; i < 5; i++) {
                    menuMakanList.add(new MenuMakan(
                            i + 1,
                            "Menu" + (i + 1),
                            10000 + (i * 1000),
                            20
                    ));
                }
            }
            listMenuMakan.setMenuMakanList(menuMakanList);
            listNewMenuMakan.setMenuMakanList(menuMakanList);

            baos        = new ByteArrayOutputStream();
            oos         = new ObjectOutputStream(baos);

            inetAddress = InetAddress.getByName(INETADDR);
            servSocket  = new DatagramSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
        run(listMenuMakan);
    }

    private static void run(ListMenuMakan listMenuMakan) {
        try {
            int option;
            userEntry   = new BufferedReader(new InputStreamReader(System.in));

            do {
                do {
                    System.out.print("\nMenu : \n1. Kirim Menu \n2. Buat Promo \nPilih opsi: ");
                    option = Integer.parseInt(userEntry.readLine());
                } while (option > 2 || option < 1);

                if (option == 1) {
                    oos.writeObject(listMenuMakan);
                    dataPacket = baos.toByteArray();
                    dgramPacketSend = new DatagramPacket(dataPacket, dataPacket.length, inetAddress, PORT);
                    servSocket.send(dgramPacketSend);
                } else {
                    showMenuMakan();

                    ListPromoMenu listPromoMenu = inputPromo(listMenuMakan);
                    setPromoToNewMenu(listPromoMenu);

                    oos.writeObject(listNewMenuMakan);
//                    oos.writeObject(listPromoMenu);
                    dataPacket = baos.toByteArray();
                    dgramPacketSend = new DatagramPacket(dataPacket, dataPacket.length, inetAddress, PORT);
                    servSocket.send(dgramPacketSend);
                }

                System.out.println("Packet data successfully sent\n");
                oos.flush();

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

    private static ListPromoMenu inputPromo(ListMenuMakan listMenuMakan) {
        String answer;
        int promoID;
        double promoDiskon;
        ListPromoMenu listPromo = new ListPromoMenu();

        try {
            do {
                do {
                    System.out.print("\nPilih Menu (ID)  : ");
                    promoID = Integer.parseInt(userEntry.readLine());
                } while (promoID > listMenuMakan.getMenuMakanList().size() || promoID < 1);

                System.out.print("Promo diskon (%) : ");
                promoDiskon = Double.parseDouble(userEntry.readLine());

                listPromo.addPromoMenu(new PromoMenu(promoID, promoDiskon));

                System.out.print("Tambah promo lagi ? : ");
                answer  = userEntry.readLine();
            } while (answer.equals("Y") || answer.equals("y"));

            return listPromo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void setPromoToNewMenu(ListPromoMenu listPromoMenu) {
        for (int i = 0; i < listPromoMenu.getPromoMenuList().size(); i++) {
            double newprice = 0, oldprice, diskon;

            diskon      = listPromoMenu.getPromoMenuList().get(i).getDiskon();
            oldprice    = listMenuMakan.getMenuMakanList().get(listPromoMenu.getPromoMenuList().get(i).getId_promo() - 1).getHarga();
            newprice    =  oldprice - (oldprice * diskon / 100);

            listNewMenuMakan.getMenuMakanList().get(listPromoMenu.getPromoMenuList().get(i).getId_promo() - 1).setHarga(newprice);

        }

    }

    public static ListMenuMakan getListMenuMakan() {
        return listMenuMakan;
    }

    public static void setListNewMenuMakan(ListMenuMakan listNewMenuMakan) {
        Server.listNewMenuMakan = listNewMenuMakan;
    }

    public static ListMenuMakan getListNewMenuMakan() {
        return listNewMenuMakan;
    }

    public static void showMenuMakan() {
        System.out.println(
                "\nMenu :\nID. \tMenu \tHarga\n"
        );
        for (int i = 0; i < listMenuMakan.getMenuMakanList().size(); i++) {
            System.out.println(
                    (i + 1) + ". \t\t" +
                            listMenuMakan.getMenuMakanList().get(i).getNama_menu() + " \t" +
                            listMenuMakan.getMenuMakanList().get(i).getHarga()
            );
        }
    }
}
