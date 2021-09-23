package ovchip.Product;

import ovchip.Adres.Adres;
import ovchip.OVChipkaart.*;
import ovchip.Reiziger.Reiziger;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql {

    private Connection conn = null;
    private OvChipkaartDAO ovdao = null;

    public ProductDAOPsql(Connection conn, OvChipkaartDAO ovdao) {
        this.conn = conn;
        this.ovdao = ovdao;
    }


    boolean save(Product product) throws SQLException {
        return false;
    }

    boolean update(Product product) throws SQLException {
        return false;
    }

    boolean delete(Product product) throws SQLException {
        return false;
    }

    List<Product> findByOVChipkaart(OvChipkaart ovChipkaart) {
        List<Product> producten = new ArrayList<>();

        try {
            Statement myStmt = conn.createStatement();
            String q ="SELECT p.product_nummer, p.naam, p.beschrijving, p.prijs FROM ov_chipkaart_product ov_p, product p" +
                    " WHERE ov_p.productnummer = p.productnummer AND ov_p.kaartnummer = ? ";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, ovChipkaart.kaart_nummer);
            ResultSet myRs = pst.executeQuery();

            while (myRs.next()) {
                int product_nummer = myRs.getInt("product_nummer");
                String naam = myRs.getString("naam");
                String beschijving = myRs.getString("beschijving");
                int prijs = myRs.getInt("prijs");

                Product product = new Product(product_nummer, naam, beschijving, prijs);

                pst = conn.prepareStatement("SELECT kaart_nummer FROM ov_chipkaart_product WHERE product_nummer = ?");
                pst.setInt(1, product.product_nummer);
                ResultSet myRsOv = pst.executeQuery();

                while (myRsOv.next()){
                    //product.addOvChipkaarten(myRsOv.getInt("kaart_nummer"));
                }

                producten.add(product);
            }

            myStmt.close();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }

        return producten;
    }

    List<Product> findAll() throws SQLException, ParseException {
        return null;
    }
}
