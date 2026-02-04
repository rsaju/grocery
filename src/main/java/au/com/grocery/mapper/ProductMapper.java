package au.com.grocery.mapper;

import au.com.grocery.entity.DomainProduct;
import au.com.grocery.model.Product;
import org.springframework.stereotype.Component;

/**
 * Mapper class to convert DAO to DTO and vice versa
 */
@Component
public class ProductMapper {

    /**
     * Convert Product DTO to DAO
     * @param product Product
     * @return DAO class of Product
     */
    public DomainProduct toDomainProduct(Product product){
        if(product == null){
            return null;
        }
        return DomainProduct.builder()
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .build();
    }

    /**
     * Convert Product DAO to DTO
     * @param domainProduct domainProduct
     * @return Dto Product
     */
    public Product toProduct(DomainProduct domainProduct){
        if(domainProduct == null){
            return null;
        }
        return Product.builder()
                .productCode(domainProduct.getProductCode())
                .productName(domainProduct.getProductName())
                .productPrice(domainProduct.getProductPrice())
                .build();
    }
}
