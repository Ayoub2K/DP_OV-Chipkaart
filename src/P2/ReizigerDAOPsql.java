package P2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO{

    public ReizigerDAOPsql(Connection conn){

    }

    @Override
    public boolean save(Reiziger reiziger) {
        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        return false;
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
    public List<Reiziger> findAll() {
        return null;
    }
}
