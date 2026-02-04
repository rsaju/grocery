package au.com.grocery.api;

import au.com.grocery.model.Product;
import au.com.grocery.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Product controller class
 * to maintain product and its price
 * in the system
 */
@RestController()
@RequestMapping(path = "/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(path = "/add")
    public ResponseEntity insertGrocery(@RequestBody List<Product> product) {
        if(CollectionUtils.isEmpty(product)){
            return ResponseEntity.badRequest().body("Please provide the product details to be saved");
        }
        List<Product> products = productService.saveProduct(product);
        return ResponseEntity.accepted().body(products);
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity getAllGrocery() {
        List<Product> productList = productService.getAllProducts();
        if(CollectionUtils.isEmpty(productList)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found in system. Please contact administrator");
        }
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping(path = "/get/{productCode}")
    public ResponseEntity getProduct(@PathVariable String productCode) {
            Optional<Product> product = productService.getProductByProductCode(productCode);
            if (product.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No products found in system for product code %s",productCode));
            }
            return ResponseEntity.ok().body(product.get());
    }

    @PutMapping(path = "/update")
    public ResponseEntity updateProduct(@RequestBody List<Product> products) {
        if(CollectionUtils.isEmpty(products)){
            return ResponseEntity.badRequest().body("Product details is missing");
        }
        List<Product> product = productService.updateProducts(products);
        if (CollectionUtils.isEmpty(product)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed. Please contact administrator");
        }
        return ResponseEntity.ok(product);
    }

    @DeleteMapping(path = "/delete/{productCode}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productCode) {
        productService.deleteProduct(productCode);
        return ResponseEntity.ok("RECORD DELETED SUCCESSFULLY");
    }
}
