package au.com.grocery.service;

import au.com.grocery.model.Order;

import java.util.List;

/**
 * Service layer which defines the
 * business logic for user to place order
 */
public interface OrderService {

    /**
     * Handles the business logic to place order
     * and return optimized package recommendations
     * at checkout based on their order quantity
     * @param orderList list of order contains product and quantity details
     *                 which user wants to place
     * @return return optimized package recommendations
     * at checkout based on their order quantity
     */
    List<Order> placeOrder(List<Order> orderList);
}
