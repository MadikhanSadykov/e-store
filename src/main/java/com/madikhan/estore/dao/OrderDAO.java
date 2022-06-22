package com.madikhan.estore.dao;

import com.madikhan.estore.model.Cart;
import com.madikhan.estore.model.Order;

import java.util.List;

public interface OrderDAO extends DAO<Order> {

    Long makeOrder(Cart cart, Long userID);

    List<Order> getAllOrdersByUserID(Long userID, Long page, Integer limit, Integer languageID);

    Long getCountOrdersByUserID(Long userID);

    Order getByID(Long orderID, Integer languageID);

    void updateStatus(Long orderID, Integer statusID);

    List<Order> listAllOrdersWithLimit(Long page, Integer limit, Integer languageID);

    Long countAllOrders();


}
