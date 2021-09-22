package ovchip.Product;

import java.sql.SQLException;
import java.text.ParseException;

public interface ProductDAO {
    boolean save(Product product) throws SQLException;
    boolean update(Product product) throws SQLException;
    boolean delete(Product product) throws SQLException;
}
