package au.com.grocery.repositories;

import au.com.grocery.entity.DomainPackageOptions;
import au.com.grocery.entity.PackageOptionPkid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PackageOptionRepository extends JpaRepository<DomainPackageOptions, PackageOptionPkid> {

    Optional<List<DomainPackageOptions>> findByProductCode(String productCode);

}
