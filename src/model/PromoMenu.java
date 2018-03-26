package model;

import java.io.Serializable;

public class PromoMenu implements Serializable {
    private int id_promo;
    private double diskon;

    public PromoMenu(int id_promo, double diskon) {
        this.id_promo = id_promo;
        this.diskon = diskon;
    }

    public int getId_promo() {
        return id_promo;
    }

    public void setId_promo(int id_promo) {
        this.id_promo = id_promo;
    }

    public double getDiskon() {
        return diskon;
    }

    public void setDiskon(double diskon) {
        this.diskon = diskon;
    }
}
