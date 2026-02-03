package au.com.grocery.service;


import au.com.grocery.entity.DomainProduct;
import au.com.grocery.mapper.ProductMapper;
import au.com.grocery.model.Product;
import au.com.grocery.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductServiceImpl productService;

    List<DomainProduct> domainProducts = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    Product product = null;
    DomainProduct domainProduct = null;


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
        domainProducts = List.of(domainProduct);
        products = List.of(product);
    }

    @Test
    void getAllProductSuccessTest() {
        Mockito.when(productRepository.findAll()).thenReturn(domainProducts);
        Mockito.when(productMapper.toProduct(Mockito.any())).thenReturn(Product.builder().productCode("CE").productName("Cheese").productPrice(10).build());
        List<Product> products = productService.getAllProducts();
        Assertions.assertEquals(10, products.get(0).getProductPrice());
    }

    @Test
    void getAllProductFailTest() {
        Mockito.when(productRepository.findAll()).thenReturn(null);
        List<Product> products = productService.getAllProducts();
        Assertions.assertEquals(Collections.EMPTY_LIST, products);
    }

    @Test
    void getAllProductFailWithExceptionTest() {
        Mockito.when(productRepository.findAll()).thenThrow(new RuntimeException("db connectivity issue"));
        List<Product> products = productService.getAllProducts();
        Assertions.assertEquals(Collections.EMPTY_LIST, products);
    }

    @Test
    void getSaveProductSuccessTest() {
        Mockito.when(productMapper.toDomainProduct(product)).thenReturn(domainProduct);
        Mockito.when(productRepository.saveAll(domainProducts)).thenReturn(domainProducts);
        Mockito.when(productMapper.toProduct(domainProduct)).thenReturn(product);
        List<Product> productResposne = productService.saveProduct(products);
        Assertions.assertEquals(10, productResposne.get(0).getProductPrice());
    }

    @Test
    void getSaveProductFailTest() {
        List<Product> productResposne = productService.saveProduct(null);
        Assertions.assertEquals(Collections.emptyList(), productResposne);
    }

    @Test
    void getSaveProductExceptionTest() {
        Mockito.when(productMapper.toDomainProduct(product)).thenReturn(domainProduct);
        Mockito.when(productRepository.saveAll(domainProducts)).thenThrow(new RuntimeException("db connectivity"));
        List<Product> productResposne = productService.saveProduct(products);
        Assertions.assertEquals(Collections.emptyList(), productResposne);
    }

    @Test
    void getProductByProductCodeSuccessTest() {
        Mockito.when(productRepository.findByProductCode("CE")).thenReturn(Optional.of(domainProduct));
        Mockito.when(productMapper.toProduct(domainProduct)).thenReturn(product);
        Optional<Product> productResposne = productService.getProductByProductCode("CE");
        Assertions.assertEquals(10, productResposne.get().getProductPrice());
    }

    @Test
    void getProductByProductCodeFailTest() {
        Optional<Product> productResposne = productService.getProductByProductCode(null);
        Assertions.assertEquals(Optional.empty(), productResposne);
    }

    @Test
    void getProductByProductCodeExceptionTest() {
        Mockito.when(productRepository.findByProductCode("CE")).thenThrow(new RuntimeException("db connectivity"));
        Optional<Product> productResposne = productService.getProductByProductCode("CE");
        Assertions.assertEquals(Optional.empty(), productResposne);
    }

    @Test
    void getUpdateProductSuccessTest() {
        Mockito.when(productMapper.toDomainProduct(product)).thenReturn(domainProduct);
        Mockito.when(productRepository.saveAll(domainProducts)).thenReturn(domainProducts);
        Mockito.when(productMapper.toProduct(domainProduct)).thenReturn(product);
        List<Product> productResposne = productService.updateProducts(products);
        Assertions.assertEquals(10, productResposne.get(0).getProductPrice());
    }

    @Test
    void getUpdateProductFailTest() {
        List<Product> productResposne = productService.updateProducts(Collections.emptyList());
        Assertions.assertEquals(Collections.emptyList(), productResposne);
    }

    @Test
    void getUpdateProductExceptionTest() {
        Mockito.when(productRepository.saveAll(domainProducts)).thenThrow(new RuntimeException("db connectivity"));
        List<Product> productResposne = productService.updateProducts(products);
        Assertions.assertEquals(Collections.emptyList(), productResposne);
    }

    @Test
    void getDeleteProductSuccessTest() {
        productService.deleteProduct("CE");
        Mockito.verify(productRepository, Mockito.times(1)).deleteById("CE");
    }

    @Test
    void getDeleteProductFailTest() {
        productService.deleteProduct(null);
        Mockito.verify(productRepository, Mockito.times(0)).deleteById("CE");
    }
}
