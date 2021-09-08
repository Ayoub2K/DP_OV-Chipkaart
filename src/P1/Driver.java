package P1;

import java.sql.*;

public class Driver {
    public static void main(String[] args) {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "postgres");
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * from reiziger");

            System.out.println("Alle reizigers:");
            while (myRs.next()){
                System.out.println(myRs.getString("reiziger_id") + " " + myRs.getString("voorletters") + ". "+
                        myRs.getString("tussenvoegsel") +" " + myRs.getString("achternaam") + " (" + myRs.getString("geboortedatum") + ")");
            }
        }catch (Exception exc){
            exc.printStackTrace();
        }
    }
}