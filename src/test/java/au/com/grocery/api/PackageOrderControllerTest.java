package au.com.grocery.api;

import au.com.grocery.model.Order;
import au.com.grocery.model.PackageOptions;
import au.com.grocery.model.PackagingBreakdown;
import au.com.grocery.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PackageOrderControllerTest {

    Order order;
    Order orderResponse;
    MockMvc mockMvc;
    ObjectMapper objectMapper;
    @Mock
    OrderService orderService;
    @InjectMocks
    PackageOrderController packageOrderController;

    @BeforeEach
    void setUp() {
        order = Order.builder()
                .productCode("CE")
                .quantity(10).build();
        orderResponse = Order.builder()
                .productCode("CE")
                .quantity(10)
                .totalPrice(40.0)
                .packagingBreakdowns(List.of(PackagingBreakdown.builder()
                        .packageCount(2)
                        .packageOptions(PackageOptions.builder()
                                .packagePrice(20.0)
                                .quantity(5)
                                .build())
                        .build()))
                .build();
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(packageOrderController).build();
    }

    @Test
    void placeOrderSuccessTest() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(List.of(order));
        Mockito.when(orderService.placeOrder(List.of(order))).thenReturn(List.of(orderResponse));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/order/placeOrder")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        List<Order> orderResponseTest = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Order>>(){});
        Assertions.assertEquals(List.of(orderResponse), orderResponseTest);
    }

    @Test
    void placeOrderFailureTest() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(Collections.EMPTY_LIST);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/order/placeOrder")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(status().isNotFound())
                .andReturn();
        Assertions.assertEquals("Item unavailable, Please contact Administrator for any queries", result.getResponse().getContentAsString());

    }
}