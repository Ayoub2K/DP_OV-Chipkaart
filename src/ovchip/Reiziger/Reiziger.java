package ovchip.Reiziger;

import ovchip.Adres.Adres;
import ovchip.OVChipkaart.OvChipkaart;

import java.util.ArrayList;
import java.util.Date;

public class Reiziger {
    public int reiziger_id;
    public String voorletters;
    public String tussenvoegsel;
    public String achternaam;
    public Date geboortedatum;
    public Adres adres;
    public ArrayList<OvChipkaart> ovChipkaarten;



    public Reiziger(String voorletters, String tussenvoegsel, String achternaam, java.sql.Date geboortedatum) {
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public Reiziger(int reiziger_id, String voorletters, String tussenvoegsel, String achternaam, java.sql.Date geboortedatum) {
        this(voorletters, tussenvoegsel, achternaam, geboortedatum);
        this.reiziger_id = reiziger_id;
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
