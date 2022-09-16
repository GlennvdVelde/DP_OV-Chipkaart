package presentatielaag.dto;

import data.AdresDAO;
import data.OVChipkaartDAO;
import data.ReizigerDAO;
import domein.OVChipkaart;
import domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    public Connection conn;
    public ReizigerDAO rdao;

    public OVChipkaartDAOPsql(Connection conn, ReizigerDAO rdao) throws SQLException {
        this.conn = conn;
        this.rdao = rdao;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        if(ovChipkaart.getReiziger() != null) {
            rdao.save(ovChipkaart.getReiziger());
        }
        try{
            Statement statement = conn.createStatement();

            String sql = String.format("INSERT INTO ov_chipkaart " +
                    "(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id)" +
                    "VALUES('%d', '%s', '%s', '%s', '%d')", ovChipkaart.getKaart_nummer(), ovChipkaart.getGeldig_tot(), ovChipkaart.getKlasse(), ovChipkaart.getSaldo(), ovChipkaart.reiziger.getId());

            statement.executeQuery(sql);
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        if(ovChipkaart.getReiziger() != null) {
            rdao.update(ovChipkaart.getReiziger());
        }
        try{
            Statement statement = conn.createStatement();

            String sql = String.format("UPDATE ov_chipkaart " +
                    "SET kaart_nummer = '%d', " +
                    "geldig_tot = '%s', " +
                    "klasse = '%d', " +
                    "saldo = '%f'" +
                    "WHERE kaart_nummer = '%d'",

                    ovChipkaart.getKaart_nummer(),
                    ovChipkaart.getGeldig_tot(),
                    ovChipkaart.getKlasse(),
                    ovChipkaart.getSaldo(),
                    ovChipkaart.reiziger.getId());

            statement.executeQuery(sql);
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        if(ovChipkaart.getReiziger() != null) {
            rdao.delete(ovChipkaart.getReiziger());
        }
        try{
            Statement statement = conn.createStatement();

            String sql = String.format("DELETE * FROM ov_chipkaart " +
                    "WHERE kaart_nummer = '%d'", ovChipkaart.getKaart_nummer());

            statement.executeQuery(sql);
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public OVChipkaart findByReiziger(Reiziger reiziger) {
        try{
            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery("SELECT * FROM ov_chipkaart WHERE reiziger_id = " + reiziger.getId());

            if(result.next()){
                return new OVChipkaart(
                    result.getInt("kaart_nummer"),
                    result.getDate("geldig_tot"),
                    result.getInt("klasse"),
                    result.getFloat("saldo"),
                    rdao.findById(reiziger.getId()));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<OVChipkaart> findAll() {
        List<OVChipkaart> ovChipkaartList = new ArrayList<>();
        try{
            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery("SELECT * FROM ov_chipkaart");

            while(result.next()){
                OVChipkaart ovChipkaart = new OVChipkaart(
                    result.getInt("kaart_nummer"),
                    result.getDate("geldig_tot"),
                    result.getInt("klasse"),
                    result.getFloat("saldo"),
                    rdao.findById(result.getInt("reiziger_id")));
                    ovChipkaartList.add(ovChipkaart);
            }
            return ovChipkaartList;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
