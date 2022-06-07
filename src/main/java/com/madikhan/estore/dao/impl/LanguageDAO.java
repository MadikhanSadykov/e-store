package com.madikhan.estore.dao.impl;

import com.madikhan.estore.dao.DAO;
import com.madikhan.estore.model.Language;

import java.sql.SQLException;
import java.util.List;

public class LanguageDAO implements DAO<Language> {
    @Override
    public void create(Language object) throws SQLException {

    }

    @Override
    public void update(Long id, Language object) {

    }

    @Override
    public Language getByID(Long id) {
        return null;
    }

    @Override
    public List<Language> getAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
