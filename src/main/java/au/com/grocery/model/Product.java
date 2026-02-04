package au.com.grocery.model;

import lombok.*;

/**
 * DTO class for Product
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    private String productCode;
    private String productName;
    private double productPrice;
}
