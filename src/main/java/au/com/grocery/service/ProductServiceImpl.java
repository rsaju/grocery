package au.com.grocery.service;

import au.com.grocery.entity.DomainProduct;
import au.com.grocery.mapper.ProductMapper;
import au.com.grocery.model.Product;
import au.com.grocery.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<Product> saveProduct(List<Product> products) {
        if (!CollectionUtils.isEmpty(products)) {
            List<DomainProduct> domainProducts = products
                    .stream()
                    .map(productMapper::toDomainProduct).toList();
            try {
                List<DomainProduct> productDao = productRepository.saveAll(domainProducts);
                return productDao.stream().map(productMapper::toProduct).toList();
            } catch (Exception ex) {
                log.error("Failed to save product due to {}", ex.getMessage());
            }

        }
        return Collections.emptyList();
    }

    @Override
    public List<Product> getAllProducts() {
        try {
            List<DomainProduct> products = productRepository.findAll();
            if (!CollectionUtils.isEmpty(products)) {
                return products.stream().map(productMapper::toProduct).toList();
            }
        } catch (Exception ex) {
            log.error("Failed to get product due to {}", ex.getMessage());
        }
        return Collections.emptyList();

    }

    @Override
    public Optional<Product> getProductByProductCode(String productCode) {
        if (StringUtils.isNotBlank(productCode)) {
            try {
                Optional<DomainProduct> domainProduct = productRepository.findByProductCode(productCode);
                if (domainProduct.isPresent()) {
                    return Optional.ofNullable(productMapper.toProduct(domainProduct.orElse(null)));
                }
            } catch (Exception ex) {
                log.error("Failed to find product by {} product code due to {}", productCode, ex.getMessage());
            }

        }
        return Optional.empty();
    }

    @Override
    public List<Product> updateProducts(List<Product> products) {
        if (CollectionUtils.isEmpty(products)) {
            return Collections.emptyList();
        }
        List<DomainProduct> domainProductList = products.stream().map(productMapper::toDomainProduct).toList();
        try {
            return productRepository.saveAll(domainProductList).stream().map(productMapper::toProduct).toList();

        } catch (Exception ex) {
            log.error("Failed to update product due to {}", ex.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void deleteProduct(String productCode) {
        if (StringUtils.isNotBlank(productCode)) {
            productRepository.deleteById(productCode);
            log.info("Product deleted with product code {}",productCode);
        }
    }
}
