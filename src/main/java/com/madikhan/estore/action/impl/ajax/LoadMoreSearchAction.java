package com.madikhan.estore.action.impl.ajax;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.model.Product;
import com.madikhan.estore.model.search.SearchForm;
import com.madikhan.estore.service.ProductService;
import com.madikhan.estore.service.impl.ProductServiceImpl;
import com.madikhan.estore.util.RoutingUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.madikhan.estore.constants.NamesConstants.MAX_PRODUCTS_PER_HTML_PAGE;

public class LoadMoreSearchAction implements Action {

    private ProductService productService = ProductServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException,
            ServletException {
        SearchForm searchForm = createSearchForm(request);

        HttpSession session = request.getSession(true);
        Integer languageID = (Integer) session.getAttribute("languageID");
        List<Product> products = productService.listProductsBySearchForm(searchForm, getPage(request),
                MAX_PRODUCTS_PER_HTML_PAGE, languageID);
        request.setAttribute("products", products);

        RoutingUtil.forwardToFragment("product-list.jsp", request, response);
    }
}