package ovchip.OVChipkaart;

import ovchip.Product.Product;
import ovchip.Reiziger.Reiziger;

import java.util.ArrayList;
import java.util.Date;

public class OvChipkaart {
     public int kaart_nummer;
     public Date geldig_tot;
     public int klasse;
     public int saldo;
     public Reiziger reiziger;
     public ArrayList<Product> producten;

    public OvChipkaart(int kaart_nummer, Date geldig_tot, int klasse, int saldo, Reiziger reiziger) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public void addProduct(Product product) {
        producten.add(product);
    }

    public void removeProduct(Product product) {
        producten.remove(product);
    }

    @Override
    public String toString() {
        return "OVChipkaart{" +
                "kaart_nummer=" + kaart_nummer +
                ", geldig_tot=" + geldig_tot +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                ", reiziger=" + reiziger +
                '}';
    }
}
