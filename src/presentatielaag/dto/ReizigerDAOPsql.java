package presentatielaag.dto;

import data.ReizigerDAO;
import domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    String url = "jdbc:postgresql://localhost:5001/ovchip";
    String user = "postgres";
    String password = "CB500f1999";
    private final Connection conn = DriverManager.getConnection(url, user, password);

    public ReizigerDAOPsql() throws SQLException {}

    @Override
    public boolean save(Reiziger reiziger) {
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
            System.out.println("Reiziger: " + reiziger.toString() + " is toegevoegd aan de Database");
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            Statement statement = conn.createStatement();

            String sql = String.format("UPDATE Reiziger SET voorletters = 'P' WHERE reiziger_id = 77");

            statement.execute(sql);
            System.out.println("Reiziger: " + reiziger.toString() + " is updated");
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            Statement statement = conn.createStatement();

            String sql = String.format("DELETE FROM reiziger "
                    + "WHERE reiziger_id = %s", reiziger.getId());

            statement.execute(sql);
            System.out.println("Reiziger: " + reiziger.toString() + " is verwijderd uit de Database");
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

            ResultSet result = statement.executeQuery(String.format("SELECT FROM reiziger "
                    + "WHERE reiziger_id = %d", id));

            return new Reiziger(result.getInt("reiziger_id"),
                    result.getString("voorletters"),
                    result.getString("tussenvoegsel"),
                    result.getString("achternaam"),
                    result.getDate(String.valueOf("geboortedatum")));
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

            ResultSet result = statement.executeQuery(String.format("SELECT FROM reiziger "+
                    "WHERE geboortedatum = %s", java.sql.Date.valueOf(datum)));

            while(result.next()){
                Reiziger reiziger = new Reiziger(
                        result.getInt("reiziger_id"),
                        result.getString("voorletters"),
                        result.getString("tussenvoegsel"),
                        result.getString("achternaam"),
                        result.getDate(String.valueOf("geboortedatum")));
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
                        result.getDate(String.valueOf("geboortedatum")));
                reizigerList.add(reiziger);
            }
            return reizigerList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
