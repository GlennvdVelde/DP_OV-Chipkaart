package data;

import domein.Product;

public interface ProductDAO {
    public boolean save(Product product);
    public boolean update(Product product);
    public boolean delete(Product product);
}