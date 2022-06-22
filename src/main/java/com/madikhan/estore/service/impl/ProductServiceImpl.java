package com.madikhan.estore.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.madikhan.estore.dao.ProductDAO;
import com.madikhan.estore.dao.impl.ProductDAOImpl;
import com.madikhan.estore.model.Cart;
import com.madikhan.estore.model.CartItem;
import com.madikhan.estore.model.Product;
import com.madikhan.estore.model.form.ProductForm;
import com.madikhan.estore.model.form.SearchForm;
import com.madikhan.estore.service.ProductService;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
    private static ProductService productService;
    private ProductDAO productDAO = ProductDAOImpl.getInstance();

    public static ProductService getInstance() {
        if (productService == null)
            productService = new ProductServiceImpl();
        return productService;
    }

    @Override
    public List<Product> listAllProducts(Long page, int limitOfProducts, int languageID) throws SQLException {
        return productDAO.getProductsWithLimit(page, limitOfProducts, languageID);
    }

    @Override
    public List<Product> listProductsByCategory(String categoryUrl, Long page, int maxAmountOfProducts, int languageID) {
        return productDAO.getProductsByCategoryWithLimit(categoryUrl, page, maxAmountOfProducts, languageID);
    }

    @Override
    public Long countAllProducts() {
        return productDAO.getCountAllProducts();
    }

    @Override
    public Long countAllProductsByCategory(String categoryUrl, int languageID) {
        return productDAO.getCountAllProductsByCategory(categoryUrl, languageID);
    }

    @Override
    public List<Product> listProductsBySearchForm(SearchForm searchForm, Long page, int maxAmountOfProducts, int languageID) {
        return productDAO.getProductsBySearchForm(searchForm, page, maxAmountOfProducts, languageID);
    }

    @Override
    public Long countAllProductsBySearchForm(SearchForm searchForm, int languageID) {
        return productDAO.getCountAllProductsBySearchForm(searchForm, languageID);
    }

    @Override
    public void addProductToCart(ProductForm productForm, Cart cart, Boolean isPersisted, int languageID) throws SQLException {
        productDAO.addProductToCart(productForm, cart, isPersisted, languageID);
    }

    @Override
    public void addProductToCartFromDB(CartItem cartItem, Cart cart, Boolean isPersisted, int languageID) throws SQLException {

    }

    @Override
    public void removeProductFromCart(ProductForm productForm, Cart cart) {
        cart.removeProduct(productForm.getIdProduct(), productForm.getProductCount());
    }

    @Override
    public Product getProductByID(Long idProduct, int languageID) throws SQLException {
        return productDAO.getByID(idProduct, languageID);
    }

    @Override
    public String serializeCart(Cart cart) {
        StringBuilder result = new StringBuilder();
        for (CartItem cartItem : cart.getItems()) {
            result.append(cartItem.getProduct().getId()).append("-")
                    .append(cartItem.getProductCount()).append("-")
                    .append(cartItem.getPersisted())
                    .append("|");
        }
        if (result.length() > 0) {
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString();
    }

    @Override
    public void save(Product product) throws SQLException {
        productDAO.create(product);
    }

    @Override
    public void delete(Long productID) throws SQLException {
        productDAO.delete(productID);
    }

    @Override
    public void update(Long productID, Product product) throws SQLException {
        productDAO.update(productID, product);
    }

    @Override
    public Cart deserializeCart(String string, int languageID) throws SQLException {
        Cart cart = new Cart();
        String[] items = string.split("\\|");
        for (String item : items) {
            try {
                String data[] = item.split("-");
                Long idProduct = Long.parseLong(data[0]);
                int productCount = Integer.parseInt(data[1]);
                Boolean isPersisted = Boolean.parseBoolean(data[2]);
                Product product = productService.getProductByID(idProduct, languageID);
                cart.addProduct(product, productCount, isPersisted);
            } catch (RuntimeException e) {
                LOGGER.error("Can't add product to Cart during deserialization: item = " + item, e);
            }
        }
        return cart.getItems().isEmpty() ? null : cart;
    }
}
