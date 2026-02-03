package au.com.grocery.mapper;

import au.com.grocery.entity.DomainProduct;
import au.com.grocery.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

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
