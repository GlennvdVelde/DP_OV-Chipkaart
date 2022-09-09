package presentatielaag.dto;

import data.AdresDAO;
import data.ReizigerDAO;
import domein.Adres;
import domein.Reiziger;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    public Connection conn;
    public ReizigerDAO rdao;

    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Adres adres) {
        try {
            Statement statement = conn.createStatement();

            String sql = String.format("INSERT INTO adres "
                    + "(adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) "
                    + "VALUES('%d', '%s', '%s', '%s', '%s', '%d')", adres.getAdres_Id(), adres.getPostcode(), adres.getHuisnummer(), adres.getStraat(), adres.getWoonplaats(), adres.getReiziger_id());
            statement.execute(sql);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Adres adres) {
        try {
            Statement statement = conn.createStatement();

            String sql = String.format("UPDATE adres "
                    + "SET postcode = '%s', huisnummer = '%s', straat = '%s', woonplaats = '%s' "
                    + "WHERE adres_id = '%d'", adres.getPostcode(), adres.getHuisnummer(), adres.getStraat(), adres.getWoonplaats(), adres.getAdres_Id());

            statement.executeQuery(sql);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            Statement statement = conn.createStatement();

            String sql = String.format("DELETE FROM adres "
                    + "WHERE adres_id = '%d'", adres.getAdres_Id());

            statement.executeQuery(sql);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public Adres findById(int id) {
        try {
            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery(String.format("SELECT * FROM adres "
                    + "WHERE adres_id = %d", id));

            if(result.next()) {
                return new Adres(
                        result.getInt("adres_id"),
                        result.getString("postcode"),
                        result.getString("huisnummer"),
                        result.getString("straat"),
                        result.getString("woonplaats"),
                        result.getInt("reiziger_id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Adres findByReiziger (Reiziger reiziger){
        try {
            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery(String.format("SELECT * FROM adres "
                + "WHERE adres_id = %d", reiziger.getId()));

            return new Adres(
                    result.getInt("adres_id"),
                    result.getString("postcode"),
                    result.getString("huisnummer"),
                    result.getString("straat"),
                    result.getString("woonplaats"),
                    result.getInt("reiziger_id"));
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Adres> findAll(){
        try{
            List<Adres> adresList = new ArrayList<>();
            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery("SELECT * FROM adres");

            while(result.next()){
                Adres adres = new Adres(
                        result.getInt("adres_id"),
                        result.getString("postcode"),
                        result.getString("huisnummer"),
                        result.getString("straat"),
                        result.getString("woonplaats"),
                        result.getInt("reiziger_id"));
                adresList.add(adres);
            }
            return adresList;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}