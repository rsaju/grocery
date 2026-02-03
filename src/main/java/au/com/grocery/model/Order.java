package au.com.grocery.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {

    private String productCode;
    private Integer quantity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private Double totalPrice;
    private List<PackagingBreakdown> packagingBreakdowns;
}
