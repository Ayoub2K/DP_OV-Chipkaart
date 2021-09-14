package ovchip.OVChipkaart;

import java.util.Date;

public class OvChipkaart {
     public int kaart_nummer;
     public Date geldig_tot;
     public int klasse;
     public int saldo;
     public int reiziger_id;

    public OvChipkaart(int kaart_nummer, Date geldig_tot, int klasse, int saldo, int reiziger_id) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger_id = reiziger_id;
    }

    @Override
    public String toString() {
        return "OVChipkaart{" +
                "kaart_nummer=" + kaart_nummer +
                ", geldig_tot=" + geldig_tot +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                ", reiziger_id=" + reiziger_id +
                '}';
    }
}
