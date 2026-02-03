package au.com.grocery.model;

import lombok.*;

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
