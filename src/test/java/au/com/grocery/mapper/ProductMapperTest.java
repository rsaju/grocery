package au.com.grocery.mapper;

import au.com.grocery.entity.DomainProduct;
import au.com.grocery.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductMapperTest {
    Product product;
    DomainProduct domainProduct;

    @InjectMocks
    ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .productCode("CE")
                .productPrice(10)
                .productName("Cheese").build();
        domainProduct = DomainProduct.builder()
                .productCode("CE")
                .productName("Cheese")
                .productPrice(10)
                .build();
    }

    @Test
    void toDomainProductTest() {
        DomainProduct convertedProduct = productMapper.toDomainProduct(product);
        Assertions.assertEquals(domainProduct,convertedProduct);
    }

    @Test
    void toDomainProductNullTest() {
        DomainProduct convertedProduct = productMapper.toDomainProduct(null);
        assertNull(convertedProduct);
    }

    @Test
    void toProductTest() {
        Product convertedProduct = productMapper.toProduct(domainProduct);
        Assertions.assertEquals(product,convertedProduct);
    }
    @Test
    void toProductNullTest() {
        Product convertedProduct = productMapper.toProduct(null);
        assertNull(convertedProduct);
    }

}