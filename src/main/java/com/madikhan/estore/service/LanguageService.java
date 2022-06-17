package com.madikhan.estore.service;

import com.madikhan.estore.model.Language;

import java.sql.SQLException;
import java.util.List;

public interface LanguageService {

    List<Language> listAllLanguages() throws SQLException;

    Integer getLanguageID(String language) throws SQLException;

}
