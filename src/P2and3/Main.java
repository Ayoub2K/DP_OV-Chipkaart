package P2and3;
import java.sql.*;
import java.text.ParseException;
import java.util.List;

public class Main {
    private static Connection conn = null;

    public static void main(String[] args) throws SQLException, ParseException {
        ReizigerDAOPsql RDsql = new ReizigerDAOPsql(getConnection());
        AdresDAOPsql ADsql = new AdresDAOPsql(getConnection());
        testReizigerDAO(RDsql);
        testAdresDAO(ADsql, RDsql);
        closeConnection();
    }

    private static Connection getConnection() throws SQLException{
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "postgres");
            System.out.println("connecting to database");
            return conn;
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return null;
    }

    private static void closeConnection() throws SQLException {
        System.out.println("closing database");
        conn.close();
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException, ParseException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.delete(sietske);
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        System.out.println(rdao.findById(77));
        System.out.println(rdao.findByGbdatum("2002-12-03"));
    }

    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException, ParseException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // findAll
        List<Adres> adresssen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adresssen) {
            System.out.println(a);
        }
        System.out.println();

        //find by reiziger
        Reiziger reiziger = rdao.findById(77);
        System.out.println("find by reiziger = " + adao.findByReiziger(reiziger));

        // Save & Delete & update
        Adres mijnAdres = new Adres(32, "2802HB", "633", "K.w.Weg", "Gouda", 77);
        adao.delete(mijnAdres);
        adresssen = adao.findAll();
        System.out.println(adresssen.size() + " adresssen na delete");
        adao.save(mijnAdres);
        adresssen = adao.findAll();
        System.out.println(adresssen.size() + " adresssen na save");

        Adres newAdres = new Adres(32, "2802HB", "32", "K.w.Weg", "Gouda", 77);
        adao.update(newAdres);
        System.out.println("na update = " + adao.findByReiziger(reiziger));


    }

}
