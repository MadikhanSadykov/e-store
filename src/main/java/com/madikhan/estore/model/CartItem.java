package com.madikhan.estore.model;

import java.io.Serializable;

public class CartItem extends AbstractModel<Long> implements Serializable {

    private static final long serialVersionUID = -5183641531128411113L;

    private Integer productCount;
    private Product product;
    private Boolean isPersisted = false;
    private Boolean isDeleted = false;
    private Integer productCountToDelete;
    private Long idProduct;
    private Long idUser;

    public CartItem() {
        super();
    }

    public CartItem(Integer productCount, Product product) {
        this.productCount = productCount;
        this.product = product;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Boolean getPersisted() {
        return isPersisted;
    }

    public void setPersisted(Boolean persisted) {
        isPersisted = persisted;
    }

    public Long getIdUser() {
        return idUser;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Integer getProductCountToDelete() {
        return productCountToDelete;
    }

    public void setProductCountToDelete(Integer productCountToDelete) {
        this.productCountToDelete = productCountToDelete;
    }

    @Override
    public String toString() {
        return String.format("Cart [id=%s, productCount=%s, product=%s",
                getId(), productCount, product);
    }

}
