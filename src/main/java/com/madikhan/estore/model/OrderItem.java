package com.madikhan.estore.model;

import java.math.BigDecimal;

public class OrderItem extends AbstractModel<Long> {

    private static final long serialVersionUID = 66546161942640024L;

    private Integer productCount;
    private BigDecimal cost;
    private Long idOrder;
    private Long idProduct;
    private Product product;

    public OrderItem() {
        super();
    }

    public OrderItem(Integer productCount, BigDecimal cost, Long idOrder, Product product) {
        this.productCount = productCount;
        this.cost = cost;
        this.idOrder = idOrder;
        this.product = product;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    @Override
    public String toString() {
        return String.format("OrderItem [id=%s, productCount=%s, cost=%s, idOrder=%s, idProduct=%s",
                getId(), productCount, cost, idOrder, product);
    }
}
