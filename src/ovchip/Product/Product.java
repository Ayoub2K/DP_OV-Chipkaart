package ovchip.Product;

import ovchip.OVChipkaart.OvChipkaart;

import java.util.ArrayList;

public class Product {
    public int product_nummer;
    public String naam;
    public String beschijving;
    public double prijs;
    public ArrayList<OvChipkaart> OvChipkaarten;

    public Product( String naam, String beschijving, double prijs) {
        this.naam = naam;
        this.beschijving = beschijving;
        this.prijs = prijs;
    }

    public Product(int product_nummer, String naam, String beschijving, double prijs) {
        this(naam, beschijving, prijs);
        this.product_nummer = product_nummer;
    }

    public void addOvChipkaarten(OvChipkaart ovChipkaart) {
        OvChipkaarten.add(ovChipkaart);
    }

    public void removeOvChipkaarten(OvChipkaart ovChipkaart) {
        OvChipkaarten.remove(ovChipkaart);
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_nummer=" + product_nummer +
                ", naam='" + naam + '\'' +
                ", beschijving='" + beschijving + '\'' +
                ", prijs=" + prijs +
                '}';
    }
}
