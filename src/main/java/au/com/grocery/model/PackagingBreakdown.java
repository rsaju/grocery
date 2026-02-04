package au.com.grocery.model;

import lombok.*;

/**
 * DTO class to show the package breakdown
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PackagingBreakdown {

    private Integer packageCount;
    private PackageOptions packageOptions;
}
