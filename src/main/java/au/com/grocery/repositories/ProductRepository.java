package au.com.grocery.repositories;

import au.com.grocery.entity.DomainProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This repository provides an abstraction
 * layer for data access operations
 * of Product entities
 */
@Repository
public interface ProductRepository extends JpaRepository<DomainProduct, String> {

    /**
     * find product by product code
     * @param productCode product code to search for
     * @return Optional of Product
     */
    Optional<DomainProduct> findByProductCode(String productCode);

}
