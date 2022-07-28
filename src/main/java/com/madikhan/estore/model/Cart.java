package com.madikhan.estore.model;

import com.madikhan.estore.service.CartService;
import com.madikhan.estore.service.impl.CartServiceImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart extends AbstractModel<Long> implements Serializable {

    private static final long serialVersionUID = -9106742499007343174L;
    private final CartService cartService = CartServiceImpl.getInstance();

    private Long totalCount = (long) 0;
    private BigDecimal totalCost = BigDecimal.ZERO;
    private Long idUser;
    private Long idCartItem;
    private Map<Long, CartItem> products = new LinkedHashMap<>();
    private Map<Long, CartItem> persistedDeletedList = new LinkedHashMap<>();

    public Cart() {
        super();
    }

    public Cart(Long totalCount, Long idUser, Long idCartItem) {
        this.totalCount = totalCount;
        this.idUser = idUser;
        this.idCartItem = idCartItem;
    }

    private void refreshTotalCountAndTotalCost() {
        setTotalCount( (long) 0 );
        setTotalCost(BigDecimal.ZERO);
        for (CartItem cartItem : getItems() ) {
            setTotalCount(getTotalCount() + cartItem.getProductCount());
            setTotalCost(getTotalCost()
                    .add(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getProductCount()))) );
        }
    }

    public void addProduct(Product product, int productCount, Boolean isPersisted) {
        cartService.validateShoppingCartSize(product.getId(), getProducts());
        CartItem cartItem = getProducts().get(product.getId());
        if (cartItem == null) {
            cartService.validateProductCount(productCount);
            cartItem = new CartItem(productCount, product);
            cartItem.setPersisted(isPersisted);
            cartItem.setId(product.getId());
            getProducts().put(product.getId(), cartItem);
        } else {
            cartService.validateProductCount(productCount + cartItem.getProductCount());
            cartItem.setProductCount(cartItem.getProductCount() + productCount);
            cartItem.setPersisted(isPersisted);
        }
        refreshTotalCountAndTotalCost();
    }

    public void removeProduct(Long idProduct, int productCount) {
        CartItem cartItem = getProducts().get(idProduct);
        if (cartItem != null) {
            if (cartItem.getProductCount() > productCount) {
                cartItem.setProductCount(cartItem.getProductCount() - productCount);
                if (cartItem.getPersisted()) {
                    cartItem.setProductCountToDelete(productCount);
                    cartItem.setDeleted(true);
                    cartItem.setIdProduct(idProduct);
                    persistedDeletedList.put(idProduct, cartItem);
                }
            } else {
                if (cartItem.getPersisted()) {
                    cartItem.setIdProduct(idProduct);
                    cartItem.setProductCountToDelete(productCount);
                    cartItem.setDeleted(true);
                    persistedDeletedList.put(idProduct, cartItem);
                }
                getProducts().remove(idProduct);
            }
            refreshTotalCountAndTotalCost();
        }
    }

    public Collection<CartItem> getItems() {
        return products.values();
    }

    public Collection<CartItem> getPersistedDeleted() {
        return persistedDeletedList.values();
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdCartItem() {
        return idCartItem;
    }

    public void setIdCartItem(Long idCartItem) {
        this.idCartItem = idCartItem;
    }

    public Map<Long, CartItem> getProducts() {
        return products;
    }

    public void setProducts(Map<Long, CartItem> products) {
        this.products = products;
    }

    public Map<Long, CartItem> getPersistedDeletedList() {
        return persistedDeletedList;
    }

    public void setPersistedDeletedList(Map<Long, CartItem> persistedDeletedList) {
        this.persistedDeletedList = persistedDeletedList;
    }

    @Override
    public String toString() {
        return String.format("Cart [id=%s, totalCount=%s, idUser=%s, idCartItem=%s, totalCost=%s",
                getId(), totalCount, idUser, idCartItem, totalCost);
    }

}
