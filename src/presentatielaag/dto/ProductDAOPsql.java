package presentatielaag.dto;

import data.OVChipkaartDAO;
import data.ProductDAO;
import domein.OVChipkaart;
import domein.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {
    public Connection conn;
    public OVChipkaartDAO odao;

    public ProductDAOPsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save(Product product) {
        try{
            Statement statement = conn.createStatement();

            //Product object attributen
            int product_nummer = product.getProduct_nummer();
            String naam = product.getNaam();
            String beschrijving = product.getBeschrijving();
            double prijs = product.getPrijs();

            if(!product.getOvChipkaartList().isEmpty()){
                for(OVChipkaart ovchipkaart : product.getOvChipkaartList()) {
                    odao.save(ovchipkaart);

                    String SQL_junction_table = String.format("INSERT INTO ov_chipkaart_product "
                                    + "(kaart_nummer, product_nummer)"
                                    + "values ('%d', '%d')",
                            ovchipkaart.getKaart_nummer(), product_nummer);

                    statement.executeQuery(SQL_junction_table);
                }
            }

            String SQL_product = String.format("INSERT INTO product "
                            + "(product_nummer, naam, beschrijving, prijs)"
                            + "values ('%d', '%s', '%s', '%f')",
                            product_nummer, naam, beschrijving, prijs);

            statement.executeQuery(SQL_product);
            statement.close();
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }


    @Override
    public boolean update(Product product) {
        try{
            Statement statement = conn.createStatement();

            //Product object attributen
            int product_nummer = product.getProduct_nummer();
            String naam = product.getNaam();
            String beschrijving = product.getBeschrijving();
            double prijs = product.getPrijs();

        if(!product.getOvChipkaartList().isEmpty()) {
            for(OVChipkaart ovchipkaart : product.getOvChipkaartList()){
                odao.update(ovchipkaart);

                String SQL_junction_table = String.format("UPDATE ov_chipkaart_product SET "
                                + "kaart_nummmer = '%d' WHERE product_nummer = '%d')",
                                ovchipkaart.getKaart_nummer(), product_nummer);

                statement.executeQuery(SQL_junction_table);
            }
        }
            String SQL_product = String.format("UPDATE product SET "
                            + "naam = '%s', beschrijving = '%s', prijs = '%f' WHERE product_nummer = '%d')",
                                naam, beschrijving, prijs, product_nummer);

            statement.executeQuery(SQL_product);
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
    public boolean delete(Product product) {
        try{
            Statement statement = conn.createStatement();

        if(!product.getOvChipkaartList().isEmpty()) {
            for(OVChipkaart ovchipkaart : product.getOvChipkaartList()){
                odao.delete(ovchipkaart);

                String SQL_junction_table = String.format("DELETE FROM ov_chipkaart_product "
                                + "WHERE kaart_nummmer = '%d')",
                                ovchipkaart.getKaart_nummer());

                statement.executeQuery(SQL_junction_table);
            }
        }
            int product_nummer = product.getProduct_nummer();

            String SQL = String.format("DELETE FROM product " +
                    "WHERE product_nummer = '%d'", product_nummer);

            statement.executeQuery(SQL);
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
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        try{
            Statement statement = conn.createStatement();

            int kaartNummer = ovChipkaart.getKaart_nummer();

            List<Integer> productIDList = new ArrayList<>();
            List<Product> productList = new ArrayList<>();

            ResultSet result = statement.executeQuery(String.format(
                    "SELECT o.product_nummer, o.kaart_nummer FROM ov_chipkaart_product o "+
                    "INNER JOIN product p " +
                    "ON p.product_nummer = o.product_nummmer " +
                    "GROUP BY o.product_nummer, o.kaart_nummer " +
                    "HAVING kaart_nummer = '%d'", kaartNummer));

            while(result.next()){
                productIDList.add(result.getInt("product_nummer"));
            }

            for(int ID : productIDList){
                for(Product product : findAll()){
                    if(ID == product.getProduct_nummer()){
                        productList.add(product);
                    }
                }
            }

            result.close();
            statement.close();
            return productList;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        try {
            Statement statement = conn.createStatement();

            List<Product> productList = new ArrayList<>();

            ResultSet result = statement.executeQuery("SELECT * FROM product");

            while(result.next()){
                Product product = new Product(
                        result.getInt("product_nummer"),
                        result.getString("naam"),
                        result.getString("beschrijving"),
                        result.getDouble("prijs"));

                productList.add(product);
            }

            statement.close();
            result.close();

            return productList;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
