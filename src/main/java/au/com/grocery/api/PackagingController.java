package au.com.grocery.api;

import au.com.grocery.model.PackageOptions;
import au.com.grocery.service.PackageOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Packaging controller to maintain
 * packaging options in the system
 * by passing in package details for
 * the product
 */
@RestController
@RequestMapping("/packaging-options")
@RequiredArgsConstructor
public class PackagingController {

    private final PackageOptionService packageOptionService;

    @PostMapping(path = "/add")
    public ResponseEntity insertPackageOption(@RequestBody List<PackageOptions> packageOptions) {
        if(CollectionUtils.isEmpty(packageOptions)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Package options are mandatory");
        }
        packageOptionService.savePackageOption(packageOptions);
        return ResponseEntity.accepted().build();
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity getAllPackageOption() {
        List<PackageOptions> packageOptionsResponse = packageOptionService.getAllPackageOption();
        if(CollectionUtils.isEmpty(packageOptionsResponse)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please contact Administrator for any queries");
        }
        return ResponseEntity.ok(packageOptionsResponse);
    }

    @GetMapping(path = "/get/{productCode}")
    public ResponseEntity getPackageOptionByProduct(@PathVariable String productCode) {
            List<PackageOptions> packageOptions = packageOptionService.getPackageOptionByProductCode(productCode);
            if (CollectionUtils.isEmpty(packageOptions)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No product available with product code %s",productCode));
            }
            return ResponseEntity.ok().body(packageOptions);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<List<PackageOptions>> updatePackageOptionByProduct(@RequestBody List<PackageOptions> packageOptions) {
        List<PackageOptions> packageOptionsList = packageOptionService.updatePackageOption(packageOptions);
        return ResponseEntity.ok(packageOptionsList);
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<String> deletePackageOption(@RequestBody List<PackageOptions> packageOptions) {
        if(CollectionUtils.isEmpty(packageOptions)){
            return ResponseEntity.badRequest().body("Please insert the data to be removed");
        }
        packageOptionService.deletePackageOption(packageOptions);
        return ResponseEntity.ok("RECORD DELETED SUCCESSFULLY");
    }
}
