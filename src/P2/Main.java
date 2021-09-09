package P2;

import java.sql.*;
import java.text.ParseException;
import java.util.List;

public class Main {
    private static Connection conn = null;

    public static void main(String[] args) throws SQLException, ParseException {
        ReizigerDAOPsql RDsql = new ReizigerDAOPsql(getConnection());
        testReizigerDAO(RDsql);
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
        conn.close();
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
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
        rdao.delete(sietske);
    }

}
