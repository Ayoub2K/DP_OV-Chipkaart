package ovchip.Reiziger;

import java.util.ArrayList;
import java.util.Date;

public class Reiziger {
    public int reiziger_id;
    public String voorletters;
    public String tussenvoegsel;
    public String achternaam;
    public Date geboortedatum;
    public int adres_id;
    public ArrayList<Integer> kaart_nummer;


    public Reiziger(int reiziger_id, String voorletters, String tussenvoegsel, String achternaam, java.sql.Date geboortedatum) {
        this.reiziger_id = reiziger_id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    @Override
    public String toString() {
        return "Reiziger{" +
                "reiziger_id=" + reiziger_id +
                ", voorletters='" + voorletters + '\'' +
                ", tussenvoegsel='" + tussenvoegsel + '\'' +
                ", achternaam='" + achternaam + '\'' +
                ", geboortedatum=" + geboortedatum +
                '}';
    }
}
