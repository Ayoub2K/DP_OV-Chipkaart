package ovchip.Adres;

import ovchip.Reiziger.Reiziger;

public class Adres {
    public int adres_id;
    public String postcode;
    public String huisnummer;
    public String straat;
    public String woonplaats;
    public Reiziger reiziger;

    public Adres(String postcode, String huisnummer, String straat, String woonplaats, Reiziger reiziger) {
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger = reiziger;
    }

    public Adres(int adres_id, String postcode, String huisnummer, String straat, String woonplaats, Reiziger reiziger) {
        this(postcode, huisnummer, straat, woonplaats, reiziger);
        this.adres_id = adres_id;
    }

    public int getAdres_id() {
        return adres_id;
    }

    @Override
    public String toString() {
        return "Adres{" +
                "adres_id=" + adres_id +
                ", postcode='" + postcode + '\'' +
                ", huisnummer='" + huisnummer + '\'' +
                ", straat='" + straat + '\'' +
                ", woonplaats='" + woonplaats + '\'' +
                ", reiziger=" + reiziger +
                '}';
    }
}
