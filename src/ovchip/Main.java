package ovchip;
import ovchip.Adres.*;
import ovchip.Product.Product;
import ovchip.Product.ProductDAO;
import ovchip.Product.ProductDAOPsql;
import ovchip.Reiziger.*;
import ovchip.OVChipkaart.*;

import java.sql.*;
import java.text.ParseException;
import java.util.List;

public class Main {
    private static Connection conn = null;

    public static void main(String[] args) throws SQLException, ParseException {
        getConnection();
        ReizigerDAO RDsql = new ReizigerDAOPsql(conn);
        AdresDAO ADsql = new AdresDAOPsql(conn, RDsql);
        OvChipkaartDAO ODsql = new OvChipkaartDAOPsql(conn, RDsql);
        ProductDAO PDsql = new ProductDAOPsql(conn, ODsql);

        //testReizigerDAO(RDsql);
        //testAdresDAO(ADsql, RDsql);
        //testOvChipDAO(ODsql, RDsql);
        testProductDAO(PDsql, ODsql, RDsql);
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
        System.out.println("\n");
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
        for (Adres adres : adresssen) {
            System.out.println("---");
            System.out.println(adres);
            System.out.println(rdao.findById(adres.reiziger.reiziger_id));
        }
        System.out.println("---");

        //find by reiziger
        Reiziger reiziger = rdao.findById(77);
        System.out.println("find by reiziger = " + adao.findByReiziger(reiziger));

        // Save & Delete & update
        Adres mijnAdres = new Adres(32, "2802HB", "633", "K.w.Weg", "Gouda", reiziger);


        Adres newAdres = new Adres(32, "2802HB", "32", "K.w.Weg", "Gouda", reiziger);
        adao.update(newAdres);
        System.out.println("na update = " + adao.findByReiziger(reiziger));



        adresssen = adao.findAll();
        System.out.println(adresssen.size() + " adresssen na delete");
        adao.save(mijnAdres);
        adresssen = adao.findAll();
        System.out.println(adresssen.size() + " adresssen na save");



        adao.delete(mijnAdres);
        rdao.delete(rdao.findById(77));


    }

    private static void testOvChipDAO(OvChipkaartDAO odao, ReizigerDAO rdao) throws SQLException, ParseException {
        System.out.println("\n---------- Test OvChipkaartDAO -------------");

        // findAll
        List<OvChipkaart> OvChipkaarten = odao.findAll();
        System.out.println("[Test] OvChipkaartDAO.findAll() geeft de volgende OvChipkaarten:");
        for (OvChipkaart ovChipkaart : OvChipkaarten) {
            System.out.println("---");
            System.out.println(ovChipkaart);
            System.out.println(rdao.findById(ovChipkaart.reiziger.reiziger_id));
        }
        System.out.println("---");


        //create reiziger
        String gbdatum = "2000-05-17";
        Reiziger Ayoub = new Reiziger(99, "A", "", "Aarkoub", java.sql.Date.valueOf(gbdatum));
        rdao.save(Ayoub);

        //create OVChip
        String geldigdatum = "2021-12-03";
        OvChipkaart ovChipkaart = new OvChipkaart(99999, java.sql.Date.valueOf(geldigdatum), 1, 50, Ayoub);
        odao.save(ovChipkaart);


        System.out.println("find by reiziger VOOR UPDATE = " + odao.findByReiziger(Ayoub));
        //update OVChip
        OvChipkaart ovChipkaartUpdate = new OvChipkaart(99999, java.sql.Date.valueOf(geldigdatum), 2, 50, Ayoub);
        odao.update(ovChipkaartUpdate);

        //find by reiziger
        System.out.println("find by reiziger NA UPDATE = " + odao.findByReiziger(Ayoub));

        //delete gegevens
        odao.delete(ovChipkaart);
        rdao.delete(Ayoub);
    }

    private static void testProductDAO(ProductDAO pdao, OvChipkaartDAO odao, ReizigerDAO rdao) throws SQLException, ParseException{

        //create reiziger
        String gbdatum = "2000-05-17";
        Reiziger Ayoubtest = new Reiziger(654, "A", "", "Aarkoub", java.sql.Date.valueOf(gbdatum));
        rdao.save(Ayoubtest);
        //create OVChip
        String geldigdatum = "2021-12-03";
        OvChipkaart testovChipkaart = new OvChipkaart(88888, java.sql.Date.valueOf(geldigdatum), 1, 50, Ayoubtest);
        odao.save(testovChipkaart);

        //create Product
        Product testProduct = new Product(9, "testproduct", "beschrijving", 20);
        pdao.save(testProduct);

        System.out.println("\n---------- Test ProductDAO -------------");
        // findAll
        List<Product> Producten = pdao.findAll();
        System.out.println("[Test] ProductDAO.findAll() geeft de volgende Producten:");
        for (Product product : Producten) {
            System.out.println("---");
            System.out.println(product);
            List<OvChipkaart> ovChipkaarten = odao.findByProduct(product);
            for (OvChipkaart ovChipkaart: ovChipkaarten){
                System.out.println(ovChipkaart);
            }
        }

        System.out.println("find by ovChipkaart = " + pdao.findByOVChipkaart(odao.findbyId(35283)));

        System.out.println("---");

        //delete gegevens
        odao.delete(testovChipkaart);
        rdao.delete(Ayoubtest);
        pdao.delete(testProduct);
    }

}
