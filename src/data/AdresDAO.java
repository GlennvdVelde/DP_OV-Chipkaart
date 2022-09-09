package data;

import domein.Adres;
import domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface AdresDAO {
    boolean save(Adres adres);
    boolean update(Adres adres);
    boolean delete(Adres adres);
    Adres findById(int id);
    Adres findByReiziger(Reiziger reiziger);
    List<Adres> findAll();
}
