package model;

import java.util.ArrayList;
import java.util.List;

public class ListPromoMenu {
    List<PromoMenu> promoMenuList = new ArrayList<>();

    public ListPromoMenu(List<PromoMenu> promoMenuList) {
        this.promoMenuList = promoMenuList;
    }

    public List<PromoMenu> getPromoMenuList() {
        return promoMenuList;
    }

    public void setPromoMenuList(List<PromoMenu> promoMenuList) {
        this.promoMenuList = promoMenuList;
    }
}
