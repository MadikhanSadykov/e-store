package com.madikhan.estore.dao;

import com.madikhan.estore.model.CartItem;
import com.madikhan.estore.model.OrderItem;

import java.util.List;

public interface OrderItemDAO extends DAO<OrderItem> {

    void addOrderItem(Long orderID, CartItem cartItem);

    List<OrderItem> getAllByOrderID(Long orderID);

    Long countOrderItemByProductID(Long productID);

}
