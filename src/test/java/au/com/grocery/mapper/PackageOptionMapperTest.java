package au.com.grocery.mapper;

import au.com.grocery.entity.DomainPackageOptions;
import au.com.grocery.entity.DomainProduct;
import au.com.grocery.model.PackageOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PackageOptionMapperTest {

    PackageOptions packageOptions;
    DomainPackageOptions domainPackageOptions;
    @InjectMocks
    PackageOptionMapper packageOptionMapper;

    @BeforeEach
    void setUp() {
        packageOptions = PackageOptions.builder()
                .productCode("CE")
                .quantity(3)
                .packagePrice(20.0)
                .build();
        domainPackageOptions = DomainPackageOptions.builder()
                .productCode("CE")
                .quantity(3)
                .packagePrice(20.0)
                .build();
    }

    @Test
    void toDomainPackageOptionTest() {
        DomainPackageOptions convertedPackageOption = packageOptionMapper.toDomainPackageOption(packageOptions);
        Assertions.assertEquals(domainPackageOptions, convertedPackageOption);
    }

    @Test
    void toDomainPackageOptionNullTest() {
        DomainPackageOptions convertedPackageOption = packageOptionMapper.toDomainPackageOption(null);
        assertNull(convertedPackageOption);
    }

    @Test
    void toPackageOptionTest() {
        PackageOptions convertedPackageOption = packageOptionMapper.toPackageOption(domainPackageOptions);
        Assertions.assertEquals(packageOptions, convertedPackageOption);
    }

    @Test
    void toPackageOptionNullTest() {
        PackageOptions convertedPackageOption = packageOptionMapper.toPackageOption(null);
        assertNull(convertedPackageOption);
    }
}