package au.com.grocery.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "PACKAGE_OPTIONS")
@IdClass(PackageOptionPkid.class)
public class DomainPackageOptions {

    @Id
    @Column(name = "QUANTITY")
    private Integer quantity;
    @Id
    @Column(name = "PRODUCT_CODE")
    private String productCode;
    @Column(name = "PACKAGE_PRICE")
    private Double packagePrice;
}
