package ovchip.OVChipkaart;

import ovchip.Adres.Adres;
import ovchip.Product.Product;
import ovchip.Reiziger.Reiziger;
import ovchip.Reiziger.ReizigerDAO;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class OvChipkaartDAOPsql implements OvChipkaartDAO{

    private Connection conn = null;
    private ReizigerDAO rdao = null;

    public OvChipkaartDAOPsql(Connection conn, ReizigerDAO rdao){
        this.conn = conn;
        this.rdao = rdao;
    }

    @Override
    public boolean save(OvChipkaart ovChipkaart) throws SQLException {
        try {
            Statement myStmt = conn.createStatement();
            String q = "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, ovChipkaart.kaart_nummer);
            pst.setDate(2, (Date) ovChipkaart.geldig_tot);
            pst.setInt(3, ovChipkaart.klasse);
            pst.setInt(4, ovChipkaart.saldo);
            pst.setInt(5, ovChipkaart.reiziger.getReiziger_id());

            pst.executeUpdate();
            myStmt.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(OvChipkaart ovChipkaart) throws SQLException {
        try {
            Statement myStmt = conn.createStatement();
            String q = "UPDATE ov_chipkaart SET geldig_tot=?, klasse=?, saldo=?,  reiziger_id=? WHERE kaart_nummer=?";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setDate(1, (Date) ovChipkaart.geldig_tot);
            pst.setInt(2, ovChipkaart.klasse);
            pst.setInt(3, ovChipkaart.saldo);
            pst.setInt(4, ovChipkaart.reiziger.getReiziger_id());
            pst.setInt(5, ovChipkaart.kaart_nummer);

            pst.executeUpdate();
            myStmt.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(OvChipkaart ovChipkaart) throws SQLException {
        try {
            Statement myStmt = conn.createStatement();
            String q ="DELETE FROM ov_chipkaart WHERE kaart_nummer=? ";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, ovChipkaart.kaart_nummer);
            pst.executeUpdate();
            myStmt.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public OvChipkaart findbyId(int id) throws SQLException {
        OvChipkaart ovChipkaart = null;
        try {
            Statement myStmt = conn.createStatement();
            String q ="SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, id);
            ResultSet myRs = pst.executeQuery();
            while (myRs.next()) {
                int kaart_nummer = myRs.getInt("kaart_nummer");
                String geldig_tot = myRs.getString("geldig_tot");
                int klasse = myRs.getInt("klasse");
                int saldo = (int) myRs.getDouble("saldo");
                int reiziger_id = myRs.getInt("reiziger_id");


                java.sql.Date sqlGeldig_tot = java.sql.Date.valueOf( geldig_tot );

                ovChipkaart = new OvChipkaart( kaart_nummer, sqlGeldig_tot, klasse, saldo, reiziger_id);
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ovChipkaart;
    };

    @Override
    public OvChipkaart findByReiziger(Reiziger reiziger) {
        OvChipkaart ovChipkaart = null;
        try {
            Statement myStmt = conn.createStatement();
            String q ="SELECT * FROM ov_chipkaart WHERE reiziger_id = ?";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, reiziger.reiziger_id);
            ResultSet myRs = pst.executeQuery();
            while (myRs.next()) {
                int kaart_nummer = myRs.getInt("kaart_nummer");
                String geldig_tot = myRs.getString("geldig_tot");
                int klasse = myRs.getInt("klasse");
                int saldo = (int) myRs.getDouble("saldo");
                int reiziger_id = myRs.getInt("reiziger_id");


                java.sql.Date sqlGeldig_tot = java.sql.Date.valueOf( geldig_tot );

                ovChipkaart = new OvChipkaart( kaart_nummer, sqlGeldig_tot, klasse, saldo, reiziger);
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ovChipkaart;
    }

    @Override
    public List<OvChipkaart> findByProduct(Product product) {
        List<OvChipkaart> ovChipkaarten = new ArrayList<>();
        try {
            Statement myStmt = conn.createStatement();
            String q ="select * from ov_chipkaart where kaart_nummer in (select ov_chipkaart_product.kaart_nummer from ov_chipkaart_product where product_nummer = ?)";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, product.product_nummer);
            ResultSet myRs = pst.executeQuery();
            while (myRs.next()) {
                int kaart_nummer = myRs.getInt("kaart_nummer");
                String geldig_tot = myRs.getString("geldig_tot");
                int klasse = myRs.getInt("klasse");
                int saldo = (int) myRs.getDouble("saldo");
                int reiziger_id = myRs.getInt("reiziger_id");


                java.sql.Date sqlGeldig_tot = java.sql.Date.valueOf( geldig_tot );

                OvChipkaart ovChipkaart = new OvChipkaart( kaart_nummer, sqlGeldig_tot, klasse, saldo, reiziger_id);
                ovChipkaarten.add(ovChipkaart);
            }

            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ovChipkaarten;
    }

    @Override
    public List<OvChipkaart> findAll() throws SQLException, ParseException {

        List<OvChipkaart> OList = new ArrayList<>();
        try {
        Statement myStmt = conn.createStatement();
        ResultSet myRs = myStmt.executeQuery("SELECT * from ov_chipkaart");

        while (myRs.next()){
            int kaart_nummer = myRs.getInt("kaart_nummer");
            String geldig_tot = myRs.getString("geldig_tot");
            int klasse = myRs.getInt("klasse");
            int saldo = myRs.getInt("saldo");
            int reiziger_id = myRs.getInt("reiziger_id");

            java.sql.Date sqlGeldig_tot = java.sql.Date.valueOf( geldig_tot );

            Reiziger reiziger = rdao.findById(reiziger_id);

            OvChipkaart ovChipkaart = new OvChipkaart( kaart_nummer, sqlGeldig_tot, klasse, saldo, reiziger);

            OList.add(ovChipkaart);
        }

        myStmt.close();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return OList;
    }
}
