package au.com.grocery.service;

import au.com.grocery.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> placeOrder(List<Order> orderList);
}
