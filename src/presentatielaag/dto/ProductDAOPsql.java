package presentatielaag.dto;

import data.ProductDAO;
import domein.Product;

import java.sql.Connection;

public class ProductDAOPsql implements ProductDAO {
    public Connection conn;

    public ProductDAOPsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save(Product product) {
        return false;
    }

    @Override
    public boolean update(Product product) {
        return false;
    }

    @Override
    public boolean delete(Product product) {
        return false;
    }
}
