package com.madikhan.estore.action;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.model.form.ProductForm;
import com.madikhan.estore.model.form.SearchForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public interface Action {


    void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException, ServletException;

    default Long getPage(HttpServletRequest request) {
        try {
            return Long.parseLong(request.getParameter(PAGE));
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
                request.getParameter(QUERY),
                request.getParameterValues(CATEGORY),
                request.getParameterValues(PRODUCER));
    }

    default ProductForm createProductForm(HttpServletRequest request) {
        return new ProductForm(
                Long.parseLong(request.getParameter(ID_PRODUCT_PARAMETER)),
                Integer.parseInt(request.getParameter(PRODUCT_COUNT)));
    }

}
