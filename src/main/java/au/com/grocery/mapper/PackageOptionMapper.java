package au.com.grocery.mapper;

import au.com.grocery.entity.DomainPackageOptions;
import au.com.grocery.model.PackageOptions;
import org.springframework.stereotype.Component;

@Component
public class PackageOptionMapper {

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
