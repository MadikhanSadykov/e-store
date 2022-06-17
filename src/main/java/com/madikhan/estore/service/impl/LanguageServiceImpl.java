package com.madikhan.estore.service.impl;

import com.madikhan.estore.dao.LanguageDAO;
import com.madikhan.estore.dao.impl.LanguageDAOImpl;
import com.madikhan.estore.model.Language;
import com.madikhan.estore.service.LanguageService;

import java.sql.SQLException;
import java.util.List;

public class LanguageServiceImpl implements LanguageService {

    private static LanguageServiceImpl instance;
    private LanguageDAO languageDAO = LanguageDAOImpl.getInstance();

    private LanguageServiceImpl() {
        super();
    }

    public static LanguageService getInstance() {
        if (instance == null)
            instance = new LanguageServiceImpl();
        return instance;
    }

    @Override
    public List<Language> listAllLanguages() throws SQLException {
        return languageDAO.getAll();
    }

    @Override
    public Integer getLanguageID(String language) throws SQLException {
        return languageDAO.getLanguageID(language);
    }
}
