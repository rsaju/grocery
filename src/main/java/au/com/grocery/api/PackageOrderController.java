package au.com.grocery.api;

import au.com.grocery.model.Order;
import au.com.grocery.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class PackageOrderController {

    private final OrderService orderService;

    @PostMapping(path = "/placeOrder")
    public ResponseEntity placeOrder(@RequestBody List<Order> orderList) {
        if (CollectionUtils.isEmpty(orderList)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item unavailable, Please contact Administrator for any queries");
        }
        List<Order> orders = orderService.placeOrder(orderList);
        return ResponseEntity.ok(orders);
    }
}
