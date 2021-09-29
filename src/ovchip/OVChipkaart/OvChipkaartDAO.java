package ovchip.OVChipkaart;

import ovchip.Product.Product;
import ovchip.Reiziger.Reiziger;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface OvChipkaartDAO {

    boolean save(OvChipkaart ovChipkaart) throws SQLException;
    boolean update(OvChipkaart ovChipkaart) throws SQLException;
    boolean delete(OvChipkaart ovChipkaart) throws SQLException;
    OvChipkaart findByReiziger(Reiziger reiziger);
    List<OvChipkaart> findByProduct(Product product);
    List<OvChipkaart> findAll() throws SQLException, ParseException;

    OvChipkaart findbyId(int i) throws SQLException;
}
