package presentatielaag.dto;

import data.AdresDAO;
import data.OVChipkaartDAO;
import data.ReizigerDAO;
import domein.Adres;
import domein.OVChipkaart;
import domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    public Connection conn;
    public AdresDAO adao;
    public OVChipkaartDAO odao;

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
        if(!reiziger.getOv_chipkaarten_list().isEmpty()){
            for(OVChipkaart ov : reiziger.getOv_chipkaarten_list()){
                odao.save(ov);
            }
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

            statement.executeQuery(sql);
            statement.close();

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
        if(!reiziger.getOv_chipkaarten_list().isEmpty()){
            for(OVChipkaart ov : reiziger.getOv_chipkaarten_list()){
                odao.update(ov);
            }
        }
        try {
            Statement statement = conn.createStatement();

            String sql = String.format("UPDATE reiziger SET voorletters = '%s' WHERE reiziger_id = '%d'", reiziger.getVoorletters(), reiziger.getId());

            statement.executeQuery(sql);
            statement.close();
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
        if(!reiziger.getOv_chipkaarten_list().isEmpty()){
            for(OVChipkaart ov : reiziger.getOv_chipkaarten_list()){
                odao.delete(ov);
            }
        }
        try {
            Statement statement = conn.createStatement();

            String sql = String.format("DELETE FROM reiziger "
                    + "WHERE reiziger_id = '%s'", reiziger.getId());

            statement.executeQuery(sql);
            statement.close();
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public Reiziger findById(int id){
        try {
            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery(String.format("SELECT * FROM reiziger "
                    + "WHERE reiziger_id = '%d'", id));
            statement.close();

            Reiziger reiziger = new Reiziger();

            while(result.next()) {
                reiziger.setId(id);
                reiziger.setTussenvoegsel(result.getString("tussenvoegsel"));
                reiziger.setAchternaam(result.getString("achternaam"));
                reiziger.setGeboortedatum(result.getDate("geboortedatum"));
            }

            Adres adres = adao.findByReiziger(reiziger);
            if(adres != null){
                reiziger.setAdres(adres);
            }

            List<OVChipkaart> ov = odao.findByReiziger(reiziger);
            if(!ov.isEmpty()){
                reiziger.setOv_chipkaarten_list(ov);
            }

            result.close();

            return reiziger;
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
            statement.close();

            while(result.next()){
                Reiziger reiziger = new Reiziger(
                        result.getInt("reiziger_id"),
                        result.getString("voorletters"),
                        result.getString("tussenvoegsel"),
                        result.getString("achternaam"),
                        result.getDate("geboortedatum"));
                        Adres adres = adao.findByReiziger(reiziger);
                        if(adres != null){
                            reiziger.setAdres(adres);
                        }
                        List<OVChipkaart> ov = odao.findByReiziger(reiziger);
                        if(!ov.isEmpty()){
                            reiziger.setOv_chipkaarten_list(ov);
                        }
                    reizigerList.add(reiziger);
            }
            result.close();
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
            statement.close();

            while(result.next()){
                Reiziger reiziger = new Reiziger(
                        result.getInt("reiziger_id"),
                        result.getString("voorletters"),
                        result.getString("tussenvoegsel"),
                        result.getString("achternaam"),
                        result.getDate("geboortedatum"));
                Adres adres = adao.findByReiziger(reiziger);
                if(adres != null){
                    reiziger.setAdres(adres);
                }
                List<OVChipkaart> ov = odao.findByReiziger(reiziger);
                if(!ov.isEmpty()){
                    reiziger.setOv_chipkaarten_list(ov);
                }
                reizigerList.add(reiziger);
            }
            result.close();
            return reizigerList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
