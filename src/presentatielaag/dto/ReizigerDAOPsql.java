package presentatielaag.dto;

import data.AdresDAO;
import data.ReizigerDAO;
import domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    public Connection conn;
    public AdresDAO adao;

    public ReizigerDAOPsql(Connection conn){
        this.conn = conn;
    }

    public ReizigerDAOPsql(Connection conn, AdresDAO adao) throws SQLException{
        this.conn = conn;
        this.adao = adao;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        if(reiziger.getAdres() != null){
            adao.save(reiziger.getAdres());
        }
        try {
            Statement statement = conn.createStatement();

            int id = reiziger.getId();
            String voorl = reiziger.getVoorletters();
            String tussenvg = reiziger.getTussenvoegsel();
            String achternm = reiziger.getAchternaam();
            Date geboortedg = reiziger.getGeboortedatum();

            String sql = String.format("insert into reiziger "
                    + "(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) "
                    + "values('%s', '%s', '%s', '%s', '%s')", id, voorl, tussenvg, achternm, geboortedg.toString());

            statement.execute(sql);

            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        if(reiziger.getAdres() != null){
            adao.update(reiziger.getAdres());
        }
        try {
            Statement statement = conn.createStatement();

            String sql = String.format("UPDATE Reiziger SET voorletters = '%s' WHERE reiziger_id = '%d'", reiziger.getVoorletters(), reiziger.getId());

            statement.execute(sql);
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        if(reiziger.getAdres() != null){
            adao.delete(reiziger.getAdres());
        }
        try {
            Statement statement = conn.createStatement();

            String sql = String.format("DELETE FROM reiziger "
                    + "WHERE reiziger_id = '%s'", reiziger.getId());

            statement.execute(sql);
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public Reiziger findById(int id) {
        try {
            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery(String.format("SELECT * FROM reiziger "
                    + "WHERE reiziger_id = '%d'", id));
            while(result.next()) {
                return new Reiziger(result.getInt("reiziger_id"),
                        result.getString("voorletters"),
                        result.getString("tussenvoegsel"),
                        result.getString("achternaam"),
                        result.getDate(String.valueOf("geboortedatum")));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        List<Reiziger> reizigerList = new ArrayList<>();
        try{
            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery(String.format("SELECT * FROM reiziger "+
                    "WHERE geboortedatum = '%s'", datum));

            while(result.next()){
                Reiziger reiziger = new Reiziger(
                        result.getInt("reiziger_id"),
                        result.getString("voorletters"),
                        result.getString("tussenvoegsel"),
                        result.getString("achternaam"),
                        result.getDate("geboortedatum"));
                reizigerList.add(reiziger);
            }
            return reizigerList;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Reiziger> findAll() {
        List<Reiziger> reizigerList = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery("SELECT * FROM reiziger");
            while(result.next()){
                Reiziger reiziger = new Reiziger(
                        result.getInt("reiziger_id"),
                        result.getString("voorletters"),
                        result.getString("tussenvoegsel"),
                        result.getString("achternaam"),
                        result.getDate("geboortedatum"));
                reizigerList.add(reiziger);
            }
            return reizigerList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
