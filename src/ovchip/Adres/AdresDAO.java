package ovchip.Adres;

import ovchip.Reiziger.Reiziger;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface AdresDAO {
    boolean save(Adres adres) throws SQLException;
    boolean update(Adres adres) throws SQLException;
    boolean delete(Adres adres) throws SQLException;
    Adres findByReiziger(Reiziger reiziger);
    List<Adres> findAll() throws SQLException, ParseException;
}
