package au.com.grocery.entity;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PackageOptionPkid implements Serializable {

    private int quantity;
    private String productCode;
}
