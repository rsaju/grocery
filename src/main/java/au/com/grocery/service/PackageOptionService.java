package au.com.grocery.service;

import au.com.grocery.model.PackageOptions;

import java.util.List;

/**
 * Service layer which defines
 * Package Option maintenance
 */
public interface PackageOptionService {

    /**
     * Save PackageOption into application
     * @param packageOptions package option to be saved in the system
     * @return returns saved Package Option
     */
    List<PackageOptions> savePackageOption(List<PackageOptions> packageOptions);

    /**
     * Receives all Package Option
     * available in the application
     * @return List of Package Option available in the system
     */
    List<PackageOptions> getAllPackageOption();

    /**
     * Get package Option by Product code
     * @param productCode product code to get package options for
     * @return List of Package option available for that product code
     */
    List<PackageOptions> getPackageOptionByProductCode(String productCode);

    /**
     * Update package options available in system if not present
     * save data into database
     * @param packageOptions Package options which needs to be updated or saved
     * @return list of Package Option which gotupdated or saved
     */
    List<PackageOptions> updatePackageOption(List<PackageOptions> packageOptions);

    /**
     * Delete provided package options
     * @param packageOptions package option which needs to be deleted
     */
    void deletePackageOption(List<PackageOptions> packageOptions);
}
