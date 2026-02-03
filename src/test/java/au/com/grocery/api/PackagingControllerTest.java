package au.com.grocery.api;

import au.com.grocery.model.Order;
import au.com.grocery.model.PackageOptions;
import au.com.grocery.service.PackageOptionService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PackagingControllerTest {

    PackageOptions packageOptions;

    MockMvc mockMvc;

    ObjectMapper objectMapper;

    @Mock
    PackageOptionService packageOptionService;

    @InjectMocks
    PackagingController packagingController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(packagingController).build();
        objectMapper = new ObjectMapper();
        packageOptions = PackageOptions.builder()
                .productCode("CE")
                .packagePrice(20.0)
                .quantity(5).build();
    }

    @Test
    void insertPackageOptionTest() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(List.of(packageOptions));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/packaging-options/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isAccepted())
                .andReturn();

    }

    @Test
    void insertPackageOptionEmptyListTest() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(Collections.emptyList());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/packaging-options/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andReturn();
        Assertions.assertEquals("Package options are mandatory",mvcResult.getResponse().getContentAsString());

    }

    @Test
    void insertPackageOptionEmptyBodyTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/packaging-options/add")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    void getAllPackageOptionSuccessTest() throws Exception {
        Mockito.when(packageOptionService.getAllPackageOption()).thenReturn(List.of(packageOptions));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/packaging-options/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<PackageOptions> packageResponseTest = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<PackageOptions>>(){});

        Assertions.assertEquals(List.of(packageOptions),packageResponseTest);
    }

    @Test
    void getAllPackageOptionFailTest() throws Exception {
        Mockito.when(packageOptionService.getAllPackageOption()).thenReturn(Collections.EMPTY_LIST);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/packaging-options/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        Assertions.assertEquals("Please contact Administrator for any queries", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getPackageOptionByProductSuccessTest() throws Exception {
        Mockito.when(packageOptionService.getPackageOptionByProductCode("CE")).thenReturn(List.of(packageOptions));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/packaging-options/get/{productCode}","CE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<PackageOptions> packageResponseTest = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<PackageOptions>>(){});

        Assertions.assertEquals(List.of(packageOptions),packageResponseTest);
    }

    @Test
    void getPackageOptionByProductNotFoundTest() throws Exception {
        Mockito.when(packageOptionService.getPackageOptionByProductCode("CE")).thenReturn(Collections.EMPTY_LIST);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/packaging-options/get/{productCode}","CE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        Assertions.assertEquals("No product available with product code CE", mvcResult.getResponse().getContentAsString());

    }

    @Test
    void updatePackageOptionByProductSuccessTest() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(List.of(packageOptions));
        Mockito.when(packageOptionService.updatePackageOption(List.of(packageOptions))).thenReturn(List.of(packageOptions));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/packaging-options/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        List<PackageOptions> packageResponseTest = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<PackageOptions>>(){});

        Assertions.assertEquals(List.of(packageOptions),packageResponseTest);
    }


    @Test
    void deletePackageOptionSuccessTest() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(List.of(packageOptions));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/packaging-options/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();
        Mockito.verify(packageOptionService,Mockito.times(1)).deletePackageOption(List.of(packageOptions));
        Assertions.assertEquals("RECORD DELETED SUCCESSFULLY",mvcResult.getResponse().getContentAsString());
    }

    @Test
    void deletePackageOptionFailureTest() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(Collections.emptyList());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/packaging-options/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andReturn();
        Assertions.assertEquals("Please insert the data to be removed", mvcResult.getResponse().getContentAsString());
        Mockito.verify(packageOptionService,Mockito.times(0)).deletePackageOption(List.of(packageOptions));
    }
}