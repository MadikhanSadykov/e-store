package com.madikhan.estore.action.impl;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.exception.AccessDeniedException;
import com.madikhan.estore.model.Category;
import com.madikhan.estore.model.Producer;
import com.madikhan.estore.model.Product;
import com.madikhan.estore.model.User;
import com.madikhan.estore.service.CategoryService;
import com.madikhan.estore.service.ProducerService;
import com.madikhan.estore.service.ProductService;
import com.madikhan.estore.service.impl.CategoryServiceImpl;
import com.madikhan.estore.service.impl.ProducerServiceImpl;
import com.madikhan.estore.service.impl.ProductServiceImpl;
import com.madikhan.estore.util.RoutingUtil;
import com.madikhan.estore.util.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

public class CreateProductAction implements Action {

    private final ProductService productService = ProductServiceImpl.getInstance();
    private final CategoryService categoryService = CategoryServiceImpl.getInstance();
    private final ProducerService producerService = ProducerServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException, ServletException {

        User user = SessionUtil.getCurrentUser(request);

        if (user.getIsAdmin()) {
            Product product = new Product();
            product.setName(request.getParameter(NAME));
            product.setDescription(request.getParameter(DESCRIPTION));
            product.setPrice(BigDecimal.valueOf(Double.parseDouble(request.getParameter(PRICE))));
            product.setCategoryID(Long.parseLong(request.getParameter(CATEGORY_ID)));
            product.setProducerID(Long.parseLong(request.getParameter(PRODUCER_ID)));

            Category category = categoryService.getByID(product.getCategoryID());
            Producer producer = producerService.getByID(product.getProducerID());

            String imageLink = MEDIA_FOLDER_PATH + category.getUrl() + JPG_FORMAT;
            product.setImageLink(imageLink);
            product.setProducer(producer.getName());
            product.setCategory(category.getName());
            productService.save(product);
            RoutingUtil.redirect(ADMIN_PRODUCTS_PAGE_PATH, request, response);
        } else {
            throw new AccessDeniedException(YOU_ARE_NOT_ADMIN_MESSAGE);
        }
    }
}
