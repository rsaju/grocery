package au.com.grocery.model;

import lombok.*;

/**
 * DTO class for Package Options
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PackageOptions {

    private String productCode;
    private Integer quantity;
    private Double packagePrice;
}
