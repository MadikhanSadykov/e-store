package com.madikhan.estore.dao;

import com.madikhan.estore.model.Language;

import java.sql.SQLException;

public interface LanguageDAO extends DAO<Language> {

    Integer getLanguageID(String language) throws SQLException;

}
