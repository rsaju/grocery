package au.com.grocery.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * DAO class for Product
 */
@Table(name = "PRODUCT")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DomainProduct {

    @Id
    @Column(name = "PRODUCT_CODE")
    private String productCode;
    @Column(name = "PRODUCT_NAME")
    private String productName;
    @Column(name = "PRODUCT_PRICE")
    private double productPrice;
}
