package au.com.grocery.service;

import au.com.grocery.model.PackageOptions;

import java.util.List;

public interface PackageOptionService {

    List<PackageOptions> savePackageOption(List<PackageOptions> packageOptions);

    List<PackageOptions> getAllPackageOption();

    List<PackageOptions> getPackageOptionByProductCode(String productCode);

    List<PackageOptions> updatePackageOption(List<PackageOptions> products);

    void deletePackageOption(List<PackageOptions> packageOptions);
}
