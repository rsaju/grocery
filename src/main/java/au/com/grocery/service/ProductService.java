package au.com.grocery.service;

import au.com.grocery.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> saveProduct(List<Product> products);
    List<Product> getAllProducts();
    Optional<Product> getProductByProductCode(String productCode);
    List<Product> updateProducts(List<Product> products);
    void deleteProduct(String productCode);

}
