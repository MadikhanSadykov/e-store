package com.madikhan.estore.model.form;

public class ProductForm {

    private Long idProduct;
    private Integer productCount;

    public ProductForm() {
        super();
    }

    public ProductForm(Long idProduct, Integer productCount) {
        this.idProduct = idProduct;
        this.productCount = productCount;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }
}
