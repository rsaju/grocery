package au.com.grocery.api;

import au.com.grocery.model.Product;
import au.com.grocery.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    Product product;
    MockMvc mockMvc;
    ObjectMapper objectMapper;
    @InjectMocks
    ProductController productController;
    @Mock
    ProductService productService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();
        product = Product.builder()
                .productCode("CE")
                .productName("Cheese")
                .productPrice(10).build();
    }

    @Test
    void insertGrocerySuccessfulTest() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(List.of(product));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/product/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isAccepted())
                .andReturn();
    }

    @Test
    void insertGroceryEmptyProductTest() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(Collections.emptyList());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/product/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andReturn();
        Assertions.assertEquals("Please provide the product details to be saved",mvcResult.getResponse().getContentAsString());
    }

    @Test
    void insertGroceryNoBodyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/product/add")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        Mockito.verify(productService,Mockito.times(0)).saveProduct(List.of(product));
    }

    @Test
    void getAllGrocerySuccessfulTest() throws Exception {
        Mockito.when(productService.getAllProducts()).thenReturn(List.of(product));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/product/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<Product> productResponseTest = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Product>>(){});

        Assertions.assertEquals(List.of(product),productResponseTest);
    }

    @Test
    void getAllGroceryNotFoundTest() throws Exception {
        Mockito.when(productService.getAllProducts()).thenReturn(Collections.emptyList());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/product/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        Assertions.assertEquals("No products found in system. Please contact administrator",mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getProductByProductCodeSuccessTest() throws Exception {
        Mockito.when(productService.getProductByProductCode("CE")).thenReturn(Optional.ofNullable(product));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/product/get/{productCode}","CE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Product productResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Product.class);

        Assertions.assertEquals(product,productResponse);
    }

    @Test
    void getProductByProductCodeNotFoundTest() throws Exception {
        Mockito.when(productService.getProductByProductCode("CE")).thenReturn(Optional.empty());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/product/get/{productCode}","CE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        Assertions.assertEquals("No products found in system for product code CE",mvcResult.getResponse().getContentAsString());
    }


    @Test
    void updateProductSuccessfulTest() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(List.of(product));
        Mockito.when(productService.updateProducts(List.of(product))).thenReturn(List.of(product));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/product/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        Mockito.verify(productService,Mockito.times(1)).updateProducts(List.of(product));
    }

    @Test
    void updateProductBadRequestTest() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(List.of());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/product/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andReturn();
        Mockito.verify(productService,Mockito.times(0)).updateProducts(List.of(product));
        Assertions.assertEquals("Product details is missing",mvcResult.getResponse().getContentAsString());
    }

    @Test
    void updateProductInternalErrorTest() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(List.of(product));
        Mockito.when(productService.updateProducts(List.of(product))).thenReturn(Collections.emptyList());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/product/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isInternalServerError())
                .andReturn();
        Mockito.verify(productService,Mockito.times(1)).updateProducts(List.of(product));
        Assertions.assertEquals("Update failed. Please contact administrator",mvcResult.getResponse().getContentAsString());
    }

    @Test
    void deleteProduct() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/product/delete/{productCode}","CE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Mockito.verify(productService,Mockito.times(1)).deleteProduct("CE");
        Assertions.assertEquals("RECORD DELETED SUCCESSFULLY",mvcResult.getResponse().getContentAsString());
    }
}