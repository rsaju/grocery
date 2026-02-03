package au.com.grocery.service;

import au.com.grocery.entity.DomainPackageOptions;
import au.com.grocery.mapper.PackageOptionMapper;
import au.com.grocery.model.PackageOptions;
import au.com.grocery.repositories.PackageOptionRepository;
import com.zaxxer.hikari.metrics.micrometer.MicrometerMetricsTracker;
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
class PackageOptionServiceImplTest {
    @Mock
    private PackageOptionRepository packageOptionRepository;
    @Mock
    private PackageOptionMapper packageOptionMapper;
    @InjectMocks
    PackageOptionServiceImpl packageOptionService;
    PackageOptions packageOptions;
    DomainPackageOptions domainPackageOptions;
    List<PackageOptions> packageOptionsList = new ArrayList<>();
    List<DomainPackageOptions> domainPackageOptionsList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        packageOptions = PackageOptions.builder()
                .productCode("CE")
                .quantity(3)
                .packagePrice(20.0)
                .build();
        packageOptionsList.add(packageOptions);
        domainPackageOptions = DomainPackageOptions.builder()
                .productCode("CE")
                .quantity(3)
                .packagePrice(20.0)
                .build();
        domainPackageOptionsList.add(domainPackageOptions);

    }

    @Test
    void savePackageOptionSuccessTest() {
        Mockito.when(packageOptionMapper.toDomainPackageOption(packageOptions)).thenReturn(domainPackageOptions);
        Mockito.when(packageOptionRepository.saveAll(domainPackageOptionsList)).thenReturn(domainPackageOptionsList);
        Mockito.when(packageOptionMapper.toPackageOption(domainPackageOptions)).thenReturn(packageOptions);
        List<PackageOptions> savePackageOption = packageOptionService.savePackageOption(packageOptionsList);
        Assertions.assertEquals(3,savePackageOption.get(0).getQuantity());
    }

    @Test
    void savePackageOptionsExceptionTest() {
        Mockito.when(packageOptionMapper.toDomainPackageOption(packageOptions)).thenReturn(domainPackageOptions);
        Mockito.when(packageOptionRepository.saveAll(domainPackageOptionsList)).thenThrow(new RuntimeException("Db connectivity"));
        List<PackageOptions> savePackageOption = packageOptionService.savePackageOption(packageOptionsList);
        Assertions.assertEquals(Collections.emptyList(),savePackageOption);
    }

    @Test
    void getAllPackageOptionSucsessTest() {
        Mockito.when(packageOptionRepository.findAll()).thenReturn(domainPackageOptionsList);
        Mockito.when(packageOptionMapper.toPackageOption(domainPackageOptions)).thenReturn(packageOptions);
        List<PackageOptions> allPackageOption = packageOptionService.getAllPackageOption();
        Assertions.assertEquals(3,allPackageOption.get(0).getQuantity());
    }

    @Test
    void getAllPackageOptionEmptyTest() {
        Mockito.when(packageOptionRepository.findAll()).thenReturn(domainPackageOptionsList);
        Mockito.when(packageOptionMapper.toPackageOption(domainPackageOptions)).thenReturn(packageOptions);
        List<PackageOptions> allPackageOption = packageOptionService.getAllPackageOption();
        Assertions.assertEquals(3,allPackageOption.get(0).getQuantity());
    }

    @Test
    void getAllPackageOptionExceptionTest() {
        Mockito.when(packageOptionRepository.findAll()).thenThrow(new RuntimeException("Db connectivity"));
        List<PackageOptions> allPackageOption = packageOptionService.getAllPackageOption();
        Assertions.assertEquals(Collections.emptyList(),allPackageOption);
    }

    @Test
    void getAllPackageOptionByProductCodeSucsessTest() {
        Mockito.when(packageOptionRepository.findByProductCode("CE")).thenReturn(Optional.ofNullable(domainPackageOptionsList));
        Mockito.when(packageOptionMapper.toPackageOption(domainPackageOptions)).thenReturn(packageOptions);
        List<PackageOptions> allPackageOption = packageOptionService.getPackageOptionByProductCode("CE");
        Assertions.assertEquals(3,allPackageOption.get(0).getQuantity());
    }

    @Test
    void getAllPackageOptionByProductCodeEmptyTest() {
        Mockito.when(packageOptionRepository.findByProductCode("CE")).thenReturn(Optional.empty());
        List<PackageOptions> allPackageOption = packageOptionService.getPackageOptionByProductCode("CE");
        Assertions.assertEquals(Collections.emptyList(),allPackageOption);
    }

    @Test
    void getAllPackageOptionByProductCodeExceptionTest() {
        Mockito.when(packageOptionRepository.findByProductCode("CE")).thenThrow(new RuntimeException("Db connectivity"));
        List<PackageOptions> allPackageOption = packageOptionService.getPackageOptionByProductCode("CE");
        Assertions.assertEquals(Collections.emptyList(),allPackageOption);
    }

    @Test
    void updatePackageOptionSuccessTest() {
        Mockito.when(packageOptionMapper.toDomainPackageOption(packageOptions)).thenReturn(domainPackageOptions);
        Mockito.when(packageOptionRepository.saveAll(domainPackageOptionsList)).thenReturn(domainPackageOptionsList);
        Mockito.when(packageOptionMapper.toPackageOption(domainPackageOptions)).thenReturn(packageOptions);
        List<PackageOptions> savePackageOption = packageOptionService.updatePackageOption(packageOptionsList);
        Assertions.assertEquals(3,savePackageOption.get(0).getQuantity());
    }

    @Test
    void updatePackageOptionsEmptyTest() {
        List<PackageOptions> savePackageOption = packageOptionService.updatePackageOption(Collections.emptyList());
        Mockito.verify(packageOptionRepository,Mockito.times(0)).saveAll(domainPackageOptionsList);
        Assertions.assertEquals(Collections.emptyList(),savePackageOption);
    }

    @Test
    void updatePackageOptionExceptionTest() {
        Mockito.when(packageOptionRepository.findByProductCode("CE")).thenThrow(new RuntimeException("Db connectivity"));
        List<PackageOptions> allPackageOption = packageOptionService.getPackageOptionByProductCode("CE");
        Assertions.assertEquals(Collections.emptyList(),allPackageOption);
    }

    @Test
    void deletePackageOptionSuccessTest() {
        Mockito.when(packageOptionMapper.toDomainPackageOption(packageOptions)).thenReturn(domainPackageOptions);
        packageOptionService.deletePackageOption(packageOptionsList);
        Mockito.verify(packageOptionRepository,Mockito.times(1)).deleteAll(domainPackageOptionsList);
    }

    @Test
    void deletePackageOptionEmptyParamTest() {
        packageOptionService.deletePackageOption(Collections.emptyList());
        Mockito.verify(packageOptionRepository,Mockito.times(0)).deleteAll(domainPackageOptionsList);
    }
}