package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListPromoMenu implements Serializable{
    List<PromoMenu> promoMenuList = new ArrayList<>();

    public ListPromoMenu() {}

    public List<PromoMenu> getPromoMenuList() {
        return promoMenuList;
    }

    public void setPromoMenuList(List<PromoMenu> promoMenuList) {
        this.promoMenuList = promoMenuList;
    }

    public void addPromoMenu(PromoMenu promoMenu) {
        promoMenuList.add(promoMenu);
    }
}
