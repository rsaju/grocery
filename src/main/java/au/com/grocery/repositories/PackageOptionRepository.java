package au.com.grocery.repositories;

import au.com.grocery.entity.DomainPackageOptions;
import au.com.grocery.entity.PackageOptionPkid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
/**
 * This repository provides an abstraction
 * layer for data access operations
 * of PackageOption entities
 */
public interface PackageOptionRepository extends JpaRepository<DomainPackageOptions, PackageOptionPkid> {

    /**
     * finds package option by their product code
     * @param productCode product code to search for
     * @return List of package option available for that product code
     */
    Optional<List<DomainPackageOptions>> findByProductCode(String productCode);

}
