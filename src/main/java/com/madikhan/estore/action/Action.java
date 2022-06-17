package com.madikhan.estore.action;

import com.madikhan.estore.model.search.SearchForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public interface Action {


    void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException;

    default Long getPage(HttpServletRequest request) {
        try {
            return Long.parseLong(request.getParameter("page"));
        } catch (NumberFormatException exception) {
            return (long) 1;
        }
    }

    default Long getPageCount(Long totalCount, int itemsPerPage) {
        long result = totalCount / itemsPerPage;
        if (result * itemsPerPage != totalCount) {
            result++;
        }
        return result;
    }

    default SearchForm createSearchForm(HttpServletRequest request) {
        return new SearchForm(
                request.getParameter("query"),
                request.getParameterValues("category"),
                request.getParameterValues("producer"));
    }

}
