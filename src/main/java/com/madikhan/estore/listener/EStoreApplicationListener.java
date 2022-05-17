package com.madikhan.estore.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.madikhan.estore.service.impl.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class EStoreApplicationListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(EStoreApplicationListener.class);
    private ServiceManager serviceManager;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            serviceManager = ServiceManager.getInstance(sce.getServletContext());
        } catch (RuntimeException e) {
            LOGGER.error("Web application 'estore' init failed: "+e.getMessage(), e);
            throw e;
        }
        LOGGER.info("Web application 'estore' initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        serviceManager.close();
        LOGGER.info("Web application 'estore' destroyed");
    }

}
