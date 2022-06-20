package com.madikhan.estore.service.impl;

import com.madikhan.estore.dao.OrderDAO;
import com.madikhan.estore.dao.impl.OrderDAOImpl;
import com.madikhan.estore.model.Cart;
import com.madikhan.estore.model.Order;
import com.madikhan.estore.service.OrderService;

import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private static OrderService instance;
    private OrderDAO orderDAO = OrderDAOImpl.getInstance();

    private OrderServiceImpl() {
        super();
    }
    
    public static OrderService getInstance() {
        if (instance == null)
            instance = new OrderServiceImpl();
        return instance;
    }

    @Override
    public Long save(Cart cart, Long userID) {
        return orderDAO.makeOrder(cart, userID);
    }

    @Override
    public Order findByID(Long orderID, Integer languageID) throws SQLException {
        return orderDAO.getByID(orderID, languageID);
    }

    @Override
    public List<Order> listAllOrdersByUserID(Long userID, Long page, Integer limit, Integer languageID) {
        return orderDAO.getAllOrdersByUserID(userID, page, limit, languageID);
    }

    @Override
    public Long countMyOrders(Long userID) {
        return orderDAO.getCountOrdersByUserID(userID);
    }
}
