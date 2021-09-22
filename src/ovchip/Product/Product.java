package ovchip.Product;

import ovchip.OVChipkaart.OvChipkaart;

public class Product {
    public int product_nummer;
    public String naam;
    public String beschijving;
    public double prijs;
    public OvChipkaart ovChipkaart;

    public Product( String naam, String beschijving, double prijs) {
        this.naam = naam;
        this.beschijving = beschijving;
        this.prijs = prijs;
    }

    public Product(int product_nummer, String naam, String beschijving, double prijs) {
        this(naam, beschijving, prijs);
        this.product_nummer = product_nummer;
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
