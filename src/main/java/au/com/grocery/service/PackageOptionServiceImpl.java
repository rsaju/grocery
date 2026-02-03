package au.com.grocery.service;

import au.com.grocery.entity.DomainPackageOptions;
import au.com.grocery.mapper.PackageOptionMapper;
import au.com.grocery.model.PackageOptions;
import au.com.grocery.repositories.PackageOptionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class PackageOptionServiceImpl implements PackageOptionService{

    private final PackageOptionRepository packageOptionRepository;
    private final PackageOptionMapper packageOptionMapper;

    @Override
    public List<PackageOptions> savePackageOption(List<PackageOptions> packageOptions) {
        if (!CollectionUtils.isEmpty(packageOptions)) {
            List<DomainPackageOptions> domainPackageOptions = packageOptions
                    .stream()
                    .map(packageOptionMapper::toDomainPackageOption).toList();
            try {
                List<DomainPackageOptions> productDao = packageOptionRepository.saveAll(domainPackageOptions);
                return productDao.stream().map(packageOptionMapper::toPackageOption).toList();
            } catch (Exception ex){
                log.error("Insert failed due to error {}", ex.getMessage());
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<PackageOptions> getAllPackageOption() {
        try {
            List<DomainPackageOptions> domainPackageOptionsList = packageOptionRepository.findAll();
            if (!CollectionUtils.isEmpty(domainPackageOptionsList)) {
                return domainPackageOptionsList.stream().map(packageOptionMapper::toPackageOption).toList();
            }
        } catch (Exception ex){
            log.error("Failed to get package information because of {}", ex.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public List<PackageOptions> getPackageOptionByProductCode(String productCode) {
        if(StringUtils.isNotBlank(productCode)){
            try {
                Optional<List<DomainPackageOptions>> domainPackageOptions = packageOptionRepository.findByProductCode(productCode);
                if (domainPackageOptions.isPresent() && !CollectionUtils.isEmpty(domainPackageOptions.get())) {
                    return domainPackageOptions.get().stream().map(packageOptionMapper::toPackageOption).toList();
                }
            } catch (Exception ex){
                log.error("Failed to get package options for {} product code {}",productCode,ex.getMessage());
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<PackageOptions> updatePackageOption(List<PackageOptions> packageOptions) {
        if(CollectionUtils.isEmpty(packageOptions)){
            return Collections.emptyList();
        }
        List<DomainPackageOptions> domainPackageOptions = packageOptions.stream().map(packageOptionMapper::toDomainPackageOption).toList();
        try {
            return packageOptionRepository.saveAll(domainPackageOptions).stream().map(packageOptionMapper::toPackageOption).toList();
        } catch (Exception ex){
            log.error("Failed to update product package due to {}", ex.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void deletePackageOption(List<PackageOptions> packageOptionsList) {
        if(!CollectionUtils.isEmpty(packageOptionsList)) {
            List<DomainPackageOptions> domainPackageOptions = packageOptionsList.stream().map(packageOptionMapper::toDomainPackageOption).toList();
                packageOptionRepository.deleteAll(domainPackageOptions);
        }
    }
}
