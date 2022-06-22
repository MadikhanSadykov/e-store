package com.madikhan.estore.service.impl;

import com.madikhan.estore.dao.OrderItemDAO;
import com.madikhan.estore.dao.impl.OrderItemDAOImpl;
import com.madikhan.estore.service.OrderItemService;

public class OrderItemServiceImpl implements OrderItemService {

    private static OrderItemService instance;
    private OrderItemDAO orderItemDAO = OrderItemDAOImpl.getInstance();

    private OrderItemServiceImpl() {
        super();
    }

    public static OrderItemService getInstance() {
        if (instance == null)
            instance = new OrderItemServiceImpl();
        return instance;
    }
    @Override
    public Long countAllByProductID(Long productID) {
        return orderItemDAO.countOrderItemByProductID(productID);
    }
}
