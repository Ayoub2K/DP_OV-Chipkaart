package P2;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

interface ReizigerDAO {
    void save(Reiziger reiziger) throws SQLException;
    void update(Reiziger reiziger) throws SQLException;
    void delete(Reiziger reiziger) throws SQLException;
    Reiziger findById(int id);
    List<Reiziger> findByGbdatum(String datum);
    List<Reiziger> findAll() throws SQLException, ParseException;

}
