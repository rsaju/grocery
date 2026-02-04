package au.com.grocery.service;

import au.com.grocery.model.Order;
import au.com.grocery.model.PackageOptions;
import au.com.grocery.model.PackagingBreakdown;
import au.com.grocery.model.Product;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This class provides the concrete implementation of the
 * Order Service where actual business logic is defined
 */
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductService productService;
    private final PackageOptionService packageOptionService;

    /**
     *
     * @param orderList list of order contains product and quantity details
     *                 which user wants to place
     * @return optimized package recommendations
     * at checkout based on their order quantity
     */
    @Override
    public List<Order> placeOrder(List<Order> orderList) {

        List<Order> orders = new ArrayList<>();
        if (CollectionUtils.isEmpty(orderList)) {
            return Collections.emptyList();
        }
        for (Order order : orderList) {
            List<PackagingBreakdown> packagingBreakdowns = new ArrayList<>();
            String productCode = order.getProductCode();
            AtomicReference<Integer> quantity = new AtomicReference<>(order.getQuantity());
            AtomicReference<Double> totalPrice = new AtomicReference<>(0.0);
            if (StringUtils.isNotBlank(productCode) && 0 != quantity.get()) {
                Optional<Product> product = productService.getProductByProductCode(productCode);
                if (product.isPresent()) {
                    List<PackageOptions> packageOptions = packageOptionService.getPackageOptionByProductCode(productCode);
                    if (!CollectionUtils.isEmpty(packageOptions)) {
                        List<PackageOptions> sortedPackageOption = packageOptions.stream()
                                .sorted(Comparator.comparingInt(PackageOptions::getQuantity).reversed())
                                .toList();
                        sortedPackageOption.forEach(x -> {
                            int count = 0;
                            boolean isAddBreakDown = false;
                            while (quantity.get() >= x.getQuantity()) {
                                quantity.getAndSet(quantity.get() - x.getQuantity());
                                count++;
                                isAddBreakDown = true;
                            }
                            if (isAddBreakDown) {
                                totalPrice.set(totalPrice.get() + count * x.getPackagePrice());
                                packagingBreakdowns.add(PackagingBreakdown.builder()
                                        .packageCount(count)
                                        .packageOptions(PackageOptions.builder()
                                                .productCode(x.getProductCode())
                                                .packagePrice(x.getPackagePrice())
                                                .quantity(x.getQuantity())
                                                .build())
                                        .build());
                            }
                        });
                    }
                    if (quantity.get() > 0) {
                        totalPrice.set(totalPrice.get() + (product.get().getProductPrice() * quantity.get()));
                        packagingBreakdowns.add(PackagingBreakdown.builder()
                                .packageCount(quantity.get())
                                .packageOptions(PackageOptions.builder()
                                        .productCode(productCode)
                                        .packagePrice(product.get().getProductPrice())
                                        .quantity((1))
                                        .build())
                                .build());
                    }
                }
            }
            if (!CollectionUtils.isEmpty(packagingBreakdowns)) {
                orders.add(Order.builder()
                        .productCode(order.getProductCode())
                        .quantity(order.getQuantity())
                        .totalPrice(totalPrice.get())
                        .packagingBreakdowns(packagingBreakdowns).build());
            }
        }
        return orders;
    }
}
