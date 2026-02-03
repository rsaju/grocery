package au.com.grocery.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PackagingBreakdown {

    private Integer packageCount;
    private PackageOptions packageOptions;
}
