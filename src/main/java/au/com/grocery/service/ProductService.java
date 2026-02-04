package au.com.grocery.service;

import au.com.grocery.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Product
 * which defines method for maintaining
 * Product in the system
 */
public interface ProductService {

    /**
     * Save product into system
     * @param products Product to save into system
     * @return List of product saved
     */
    List<Product> saveProduct(List<Product> products);

    /**
     * Get all product available in the system
     * @return return all product
     */
    List<Product> getAllProducts();

    /**
     * get Product by product code
     * @param productCode product code to get product for
     * @return product
     */
    Optional<Product> getProductByProductCode(String productCode);

    /**
     * update product present in the system or save if not present
     * @param products product to be updated or saved
     * @return List of product updated or saved in the system
     */
    List<Product> updateProducts(List<Product> products);

    /**
     * Delete product
     * @param productCode product code which needs to be deleted
     */
    void deleteProduct(String productCode);

}
