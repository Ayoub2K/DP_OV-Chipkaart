package ovchip.Product;

import ovchip.Adres.Adres;
import ovchip.OVChipkaart.*;
import ovchip.Reiziger.Reiziger;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO{

    private Connection conn = null;
    private OvChipkaartDAO ovdao = null;

    public ProductDAOPsql(Connection conn, OvChipkaartDAO ovdao) {
        this.conn = conn;
        this.ovdao = ovdao;
    }


    public boolean save(Product product) throws SQLException {
        try {
            Statement myStmt = conn.createStatement();
            String q = "INSERT INTO product (product_nummer, naam, beschrijving, prijs) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, product.product_nummer);
            pst.setString(2, product.naam);
            pst.setString(3, product.beschrijving);
            pst.setInt(4, (int) product.prijs);

            pst.executeUpdate();

            for (OvChipkaart ovChipkaart : product.getOvChipkaarten()){
                String q2 = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer) VALUES (?, ?)";
                PreparedStatement pst2 = conn.prepareStatement(q2);

                pst2.setInt(1, product.getProduct_nummer());
                pst2.setInt(2, ovChipkaart.getKaart_nummer());
                pst2.executeUpdate();
            }
            myStmt.close();
            return true;

        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean update(Product product) throws SQLException {
        try {
            Statement myStmt = conn.createStatement();
            String q = "UPDATE ov_chipkaart SET naam=?, beschrijving=?, prijs=?,  reiziger_id=? WHERE product_nummer=?";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setString(1, product.naam);
            pst.setString(2, product.beschrijving);
            pst.setInt(3, (int) product.prijs);
            pst.setInt(4,product.product_nummer);

            pst.executeUpdate();
            myStmt.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean delete(Product product) throws SQLException {
        try {
            Statement myStmt = conn.createStatement();

            for (OvChipkaart ovChipkaart : product.getOvChipkaarten()){
                String q2 = "DELETE FROM ov_chipkaart_product where kaart_nummer = ? and product_nummer = ?";
                PreparedStatement pst2 = conn.prepareStatement(q2);
                pst2.setInt(1, ovChipkaart.getKaart_nummer());
                pst2.setInt(2, product.getProduct_nummer());

                pst2.executeUpdate();
            }

            String q ="DELETE FROM product WHERE product_nummer=? ";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, product.getProduct_nummer());

            pst.executeUpdate();
            myStmt.close();

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<Product> findByOVChipkaart(OvChipkaart ovChipkaart) throws SQLException, ParseException  {
        List<Product> producten = new ArrayList<>();

        try {
            Statement myStmt = conn.createStatement();
            String q ="SELECT p.product_nummer, p.naam, p.beschrijving, p.prijs FROM ov_chipkaart_product ov_p, product p" +
                    " WHERE ov_p.product_nummer = p.product_nummer AND ov_p.kaart_nummer = ? ";
            PreparedStatement pst = conn.prepareStatement(q);
            pst.setInt(1, ovChipkaart.kaart_nummer);
            ResultSet myRs = pst.executeQuery();

            while (myRs.next()) {
                int product_nummer = myRs.getInt("product_nummer");
                String naam = myRs.getString("naam");
                String beschrijving = myRs.getString("beschrijving");
                int prijs = myRs.getInt("prijs");

                Product product = new Product(product_nummer, naam, beschrijving, prijs);

                pst = conn.prepareStatement("SELECT kaart_nummer FROM ov_chipkaart_product WHERE product_nummer = ?");
                pst.setInt(1, product.product_nummer);
                ResultSet myRsOv = pst.executeQuery();

                while (myRsOv.next()){
                    product.addOvChipkaarten(myRsOv.getInt("kaart_nummer"));
                }

                producten.add(product);
            }

            myStmt.close();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }

        return producten;
    }

    public List<Product> findAll() throws SQLException, ParseException {
        List<Product> productenList = new ArrayList<>();
        try {
            Statement myStmt = conn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * from product");

            while (myRs.next()){
                int product_nummer = myRs.getInt("product_nummer");
                String naam = myRs.getString("naam");
                String beschrijving = myRs.getString("beschrijving");
                int prijs = myRs.getInt("prijs");

                Product newProduct = new Product( product_nummer, naam, beschrijving, prijs);

                productenList.add(newProduct);
            }

            myStmt.close();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productenList;
    }
}
