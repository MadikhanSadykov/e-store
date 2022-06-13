package com.madikhan.estore.model;

public class Cart extends AbstractModel<Long> {

    private static final long serialVersionUID = -8314957731155525898L;

    private Integer productCount;
    private Long idUser;
    private Long idProduct;

    public Cart() {
        super();
    }

    public Cart(Integer productCount, Long idUser, Long idProduct) {
        this.productCount = productCount;
        this.idUser = idUser;
        this.idProduct = idProduct;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    @Override
    public String toString() {
        return String.format("Cart [id=%s, productCount=%s, idUser=%s, idProduct=%s",
                getId(), productCount, idUser, idProduct);
    }
}
