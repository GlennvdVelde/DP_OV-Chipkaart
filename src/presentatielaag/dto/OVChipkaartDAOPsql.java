package presentatielaag.dto;

import data.AdresDAO;
import data.OVChipkaartDAO;
import data.ProductDAO;
import data.ReizigerDAO;
import domein.OVChipkaart;
import domein.Product;
import domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    public Connection conn;
    public ReizigerDAO rdao;
    public ProductDAO pdao;

    public OVChipkaartDAOPsql(Connection conn, ReizigerDAO rdao, ProductDAO pdao) throws SQLException {
        this.conn = conn;
        this.rdao = rdao;
        this.pdao = pdao;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        try{
            Statement statement = conn.createStatement();

            if(!ovChipkaart.getProductList().isEmpty()){
                for(Product product : ovChipkaart.getProductList()){
                    pdao.save(product);

                    String SQL_junction_table = String.format("INSERT INTO ov_chipkaart_product "
                            + "(kaart_nummer, product_nummer)"
                            + "values ('%d', '%d')", ovChipkaart.getKaart_nummer(), product.getProduct_nummer());
                }
            }
            String sql = String.format("INSERT INTO ov_chipkaart " +
                    "(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id)" +
                    "VALUES('%d', '%s', '%s', '%s', '%d')", ovChipkaart.getKaart_nummer(), ovChipkaart.getGeldig_tot(), ovChipkaart.getKlasse(), ovChipkaart.getSaldo(), ovChipkaart.reiziger.getId());

            statement.executeQuery(sql);
            statement.close();
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
            statement.close();
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
        try{
            Statement statement = conn.createStatement();

            String sql = String.format("DELETE * FROM ov_chipkaart " +
                    "WHERE kaart_nummer = '%d'", ovChipkaart.getKaart_nummer());

            statement.executeQuery(sql);
            statement.close();
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        List<OVChipkaart> ovChipkaartList = new ArrayList<>();
        try{
            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery("SELECT * FROM ov_chipkaart WHERE reiziger_id = " + reiziger.getId());
            statement.close();
            while(result.next()){
                OVChipkaart ov = new OVChipkaart(
                    result.getInt("kaart_nummer"),
                    result.getDate("geldig_tot"),
                    result.getInt("klasse"),
                    result.getFloat("saldo"),
                    rdao.findById(reiziger.getId()));
                ovChipkaartList.add(ov);
            }
            result.close();
            return ovChipkaartList;
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
            statement.close();
            while(result.next()){
                OVChipkaart ovChipkaart = new OVChipkaart(
                    result.getInt("kaart_nummer"),
                    result.getDate("geldig_tot"),
                    result.getInt("klasse"),
                    result.getFloat("saldo"),
                    rdao.findById(result.getInt("reiziger_id")));
                    ovChipkaartList.add(ovChipkaart);
            }
            result.close();
            return ovChipkaartList;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
