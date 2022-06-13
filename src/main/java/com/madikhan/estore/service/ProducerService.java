package com.madikhan.estore.service;

import com.madikhan.estore.model.Producer;

import java.sql.SQLException;
import java.util.List;

public interface ProducerService {

    List<Producer> listAllProducers() throws SQLException;

}
