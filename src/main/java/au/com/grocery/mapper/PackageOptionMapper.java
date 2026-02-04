package au.com.grocery.mapper;

import au.com.grocery.entity.DomainPackageOptions;
import au.com.grocery.model.PackageOptions;
import org.springframework.stereotype.Component;

/**
 * Mapper class to convert PackageOption DAO to DTO and vice versa
 */
@Component
public class PackageOptionMapper {

    /**
     * Convert Package Option DTO to DAO
     * @param packageOptions packageOptions
     * @return DAO PackageOptions
     */
    public DomainPackageOptions toDomainPackageOption(PackageOptions packageOptions){
        if(packageOptions == null){
            return null;
        }
        return DomainPackageOptions.builder()
                .productCode(packageOptions.getProductCode())
                .quantity(packageOptions.getQuantity())
                .packagePrice(packageOptions.getPackagePrice())
                .build();
    }

    /**
     * convert PackageOptions DAO to DTO
     * @param domainPackageOptions domainPackageOptions
     * @return DTO PackageOptions
     */
    public PackageOptions toPackageOption(DomainPackageOptions domainPackageOptions){
        if(domainPackageOptions == null){
            return null;
        }
        return PackageOptions.builder()
                .productCode(domainPackageOptions.getProductCode())
                .quantity(domainPackageOptions.getQuantity())
                .packagePrice(domainPackageOptions.getPackagePrice())
                .build();
    }
}
