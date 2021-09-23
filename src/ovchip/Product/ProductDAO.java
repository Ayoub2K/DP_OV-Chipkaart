package ovchip.Product;

import ovchip.OVChipkaart.OvChipkaart;
import ovchip.Reiziger.Reiziger;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface ProductDAO {
    boolean save(Product product) throws SQLException;
    boolean update(Product product) throws SQLException;
    boolean delete(Product product) throws SQLException;
    List<Product> findByOVChipkaart(OvChipkaart ovChipkaart);
    List<Product> findAll() throws SQLException, ParseException;
}
