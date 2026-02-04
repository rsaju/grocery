package au.com.grocery.entity;

import lombok.*;

import java.io.Serializable;

/**
 * Class to create composite key
 * for Package Option
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PackageOptionPkid implements Serializable {

    private int quantity;
    private String productCode;
}
