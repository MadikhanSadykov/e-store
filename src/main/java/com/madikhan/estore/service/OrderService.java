package com.madikhan.estore.service;

import com.madikhan.estore.model.Cart;
import com.madikhan.estore.model.CartItem;
import com.madikhan.estore.model.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {

    Long save(Cart cart, Long userID);

    Order findByID(Long orderID, Integer languageID) throws SQLException;

    List<Order> listAllOrdersByUserID(Long userID, Long page, Integer limit, Integer languageID);

    Long countMyOrders(Long userID);
}
