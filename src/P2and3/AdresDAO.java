package P2and3;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

interface AdresDAO {
    boolean save(Adres adres) throws SQLException;
    boolean update(Adres adres) throws SQLException;
    boolean delete(Adres adres) throws SQLException;
    Adres findByReiziger(Reiziger reiziger);
    List<Adres> findAll() throws SQLException, ParseException;
}
