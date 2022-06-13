package com.madikhan.estore.service.impl;

import com.madikhan.estore.dao.ProducerDAO;
import com.madikhan.estore.dao.impl.ProducerDAOImpl;
import com.madikhan.estore.model.Producer;
import com.madikhan.estore.service.ProducerService;

import java.sql.SQLException;
import java.util.List;

public class ProducerServiceImpl implements ProducerService {

    private static ProducerService instance;
    private ProducerDAO producerDAO = ProducerDAOImpl.getInstance();

    private ProducerServiceImpl(){
        super();
    }

    public static ProducerService getInstance() {
        if (instance == null)
            instance = new ProducerServiceImpl();
        return instance;
    }

    @Override
    public List<Producer> listAllProducers() throws SQLException {
        return producerDAO. getAll();
    }
}
