package P2;

import java.util.List;

interface ReizigerDAO {
    boolean save(Reiziger reiziger);
    boolean update(Reiziger reiziger);
    boolean delete(Reiziger reiziger);
    Reiziger findById(int id);
    List<Reiziger> findByGbdatum(String datum);
    List<Reiziger> findAll();

}
