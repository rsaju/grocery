package au.com.grocery.repositories;

import au.com.grocery.entity.DomainProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<DomainProduct, String> {

    Optional<DomainProduct> findByProductCode(String productCOde);

}
