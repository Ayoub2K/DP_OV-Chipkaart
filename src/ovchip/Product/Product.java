package ovchip.Product;

import ovchip.OVChipkaart.OvChipkaart;

import java.util.ArrayList;

public class Product {
    public int product_nummer;
    public String naam;
    public String beschrijving;
    public double prijs;
    public ArrayList<OvChipkaart> OvChipkaarten;
    public ArrayList<Integer> kaart_nummers;

    public Product( String naam, String beschrijving, double prijs) {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        this.OvChipkaarten = new ArrayList<>();
        this.kaart_nummers = new ArrayList<>();
    }

    public Product(int product_nummer, String naam, String beschrijving, double prijs) {
        this(naam, beschrijving, prijs);
        this.product_nummer = product_nummer;
    }

    public void addOvChipkaarten(int kaart_nummer) {
        kaart_nummers.add(kaart_nummer);
    }

    public ArrayList<OvChipkaart> getOvChipkaarten() {
        return OvChipkaarten;
    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public void removeOvChipkaarten(OvChipkaart ovChipkaart) {
        OvChipkaarten.remove(ovChipkaart);
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_nummer=" + product_nummer +
                ", naam='" + naam + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                ", prijs=" + prijs +
                '}';
    }
}
