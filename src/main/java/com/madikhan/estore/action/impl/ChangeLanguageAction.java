package com.madikhan.estore.action.impl;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.service.LanguageService;
import com.madikhan.estore.service.impl.LanguageServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class ChangeLanguageAction implements Action {

    private final LanguageService languageService = LanguageServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException, ServletException {

        HttpSession session = request.getSession(true);
        String newLanguage = request.getParameter(NEW_LANGUAGE);
        session.setAttribute(LANGUAGE, newLanguage);
        session.setAttribute(LANGUAGE_ID, languageService.getLanguageID(newLanguage));

    }
}
