package au.com.grocery.service;

import au.com.grocery.entity.DomainPackageOptions;
import au.com.grocery.entity.DomainProduct;
import au.com.grocery.model.Order;
import au.com.grocery.model.PackageOptions;
import au.com.grocery.model.Product;
import jdk.jfr.Name;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    PackageOptions packageOptions;
    List<PackageOptions> packageOptionsList = new ArrayList<>();
    List<Product> products = new ArrayList<>();
    Product product = null;
    @Mock
    private ProductService productService;
    @Mock
    private PackageOptionService packageOptionService;
    @InjectMocks
    OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {

        product = Product.builder()
                .productCode("CE")
                .productPrice(10)
                .productName("Cheese").build();
        products = List.of(product);
        packageOptions = PackageOptions.builder()
                .productCode("CE")
                .quantity(5)
                .packagePrice(20.0)
                .build();
        packageOptionsList.add(packageOptions);
    }

    @Test
    void placeOrderToGetTwoPackageOfFiveTest1() {
        Order order = Order.builder()
                .productCode("CE")
                .quantity(10).build();
        List<Order> orderList = List.of(order);
        Mockito.when(productService.getProductByProductCode("CE")).thenReturn(Optional.ofNullable(product));
        Mockito.when(packageOptionService.getPackageOptionByProductCode("CE")).thenReturn(packageOptionsList);
        List<Order> orderWithBreakdown = orderService.placeOrder(orderList);
        Assertions.assertEquals(2, orderWithBreakdown.get(0).getPackagingBreakdowns().get(0).getPackageCount());
        Assertions.assertEquals(40, orderWithBreakdown.get(0).getTotalPrice());

    }

    @Test
    void placeOrderToGetTwoPackageOfFiveAlongWithOneTest() {
        Order order = Order.builder()
                .productCode("CE")
                .quantity(11).build();
        List<Order> orderList = List.of(order);
        Mockito.when(productService.getProductByProductCode("CE")).thenReturn(Optional.ofNullable(product));
        Mockito.when(packageOptionService.getPackageOptionByProductCode("CE")).thenReturn(packageOptionsList);
        List<Order> orderWithBreakdown = orderService.placeOrder(orderList);
        Assertions.assertEquals(2, orderWithBreakdown.get(0).getPackagingBreakdowns().get(0).getPackageCount());
        Assertions.assertEquals(5, orderWithBreakdown.get(0).getPackagingBreakdowns().get(0).getPackageOptions().getQuantity());
        Assertions.assertEquals(1, orderWithBreakdown.get(0).getPackagingBreakdowns().get(1).getPackageCount());
        Assertions.assertEquals(1, orderWithBreakdown.get(0).getPackagingBreakdowns().get(1).getPackageOptions().getQuantity());
        Assertions.assertEquals(50, orderWithBreakdown.get(0).getTotalPrice());

    }

    @Test
    void placeEmptyOrderTest() {
        List<Order> orderList = orderService.placeOrder(Collections.emptyList());
        Assertions.assertEquals(Collections.emptyList(),orderList);
    }
}