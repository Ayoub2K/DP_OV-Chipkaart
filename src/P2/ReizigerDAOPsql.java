package P2;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO{

    private Connection conn = null;

    public ReizigerDAOPsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public void save(Reiziger reiziger) throws SQLException {
        Statement myStmt = conn.createStatement();
        String q ="INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(q);
        pst.setInt(1, reiziger.reiziger_id);
        pst.setString(2, reiziger.voorletters);
        pst.setString(3, reiziger.tussenvoegsel);
        pst.setString(4, reiziger.achternaam);
        pst.setDate(5, (Date) reiziger.geboortedatum);

        ResultSet myRs = pst.executeQuery();

        myStmt.close();
    }

    @Override
    public void update(Reiziger reiziger) throws SQLException {
        Statement myStmt = conn.createStatement();
        String q = "UPDATE reiziger SET voorletters=?, tussenvoegsel=?, achternaam=?, geboortedatum=? WHERE reiziger_id='reiziger_id'";
        PreparedStatement pst = conn.prepareStatement(q);
        pst.setString(1, reiziger.voorletters);
        pst.setString(2, reiziger.tussenvoegsel);
        pst.setString(3, reiziger.achternaam);
        pst.setDate(4, (Date) reiziger.geboortedatum);

        ResultSet myRs = pst.executeQuery();
        myStmt.close();

    }

    @Override
    public void delete(Reiziger reiziger) throws SQLException {
        Statement myStmt = conn.createStatement();
        String q ="DELETE FROM reiziger WHERE reiziger_id=? ";
        PreparedStatement pst = conn.prepareStatement(q);
        pst.setInt(1, reiziger.reiziger_id);
        ResultSet myRs = pst.executeQuery();
        myStmt.close();
    }

    @Override
    public Reiziger findById(int id) {
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        return null;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException, ParseException {

        List<Reiziger> RList = new ArrayList<>();

        Statement myStmt = conn.createStatement();
        ResultSet myRs = myStmt.executeQuery("SELECT * from reiziger");

        while (myRs.next()){
            int reiziger_id = myRs.getInt("reiziger_id");
            String voorletters = myRs.getString("voorletters");
            String tussenvoegsel = myRs.getString("tussenvoegsel");
            String achternaam = myRs.getString("achternaam");
            String geboortedatumString = myRs.getString("geboortedatum");

            java.sql.Date sqlDate = java.sql.Date.valueOf( geboortedatumString );

            Reiziger reiziger = new Reiziger(reiziger_id, voorletters, tussenvoegsel, achternaam, sqlDate);

            RList.add(reiziger);
        }

        myStmt.close();
        return RList;
    }
}
