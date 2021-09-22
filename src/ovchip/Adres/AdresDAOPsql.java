package ovchip.Adres;

import ovchip.Reiziger.Reiziger;
import ovchip.Reiziger.ReizigerDAO;
import ovchip.Reiziger.ReizigerDAOPsql;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {

    private Connection conn = null;
    private ReizigerDAO rdao = null;

    public AdresDAOPsql(Connection conn, ReizigerDAO RDsql) {
        this.conn = conn;
        this.rdao = RDsql;
    }

    @Override
    public boolean save(Adres adres) throws SQLException {
        try {
            Statement myStmt = conn.createStatement();
            String q = "INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, adres.adres_id);
            pst.setString(2, adres.postcode);
            pst.setString(3, adres.huisnummer);
            pst.setString(4, adres.straat);
            pst.setString(5, adres.woonplaats);
            pst.setInt(6, adres.reiziger.getReiziger_id());

            pst.executeUpdate();
            myStmt.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        try {
            Statement myStmt = conn.createStatement();
            String q = "UPDATE adres SET postcode=?, huisnummer=?, straat=?, woonplaats=?, reiziger_id=? WHERE adres_id=?";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setString(1, adres.postcode);
            pst.setString(2, adres.huisnummer);
            pst.setString(3, adres.straat);
            pst.setString(4, adres.woonplaats);
            pst.setInt(5, adres.reiziger.getReiziger_id());
            pst.setInt(6, adres.adres_id);

            pst.executeUpdate();
            myStmt.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        try {
            Statement myStmt = conn.createStatement();
            String q = "DELETE FROM adres WHERE adres_id=? ";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, adres.adres_id);
            pst.executeUpdate();
            myStmt.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        Adres adres = null;
        try {
            Statement myStmt = conn.createStatement();
            String q = "SELECT * FROM adres WHERE reiziger_id = ?";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, reiziger.reiziger_id);
            ResultSet myRs = pst.executeQuery();
            while (myRs.next()) {
                int adres_id = myRs.getInt("adres_id");
                String postcode = myRs.getString("postcode");
                String huisnummer = myRs.getString("huisnummer");
                String straat = myRs.getString("straat");
                String woonplaats = myRs.getString("woonplaats");

                adres = new Adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger);
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return adres;
    }

    @Override
    public List<Adres> findAll() throws SQLException, ParseException {

        List<Adres> AList = new ArrayList<>();
        try {
            Statement myStmt = conn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * from adres");

            while (myRs.next()) {
                int adres_id = myRs.getInt("adres_id");
                String postcode = myRs.getString("postcode");
                String huisnummer = myRs.getString("huisnummer");
                String straat = myRs.getString("straat");
                String woonplaats = myRs.getString("woonplaats");
                int reiziger_id = myRs.getInt("reiziger_id");


                Reiziger reiziger = rdao.findById(reiziger_id);

                Adres adres = new Adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger);

                AList.add(adres);
            }

            myStmt.close();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return AList;
    }
}
