package com.madikhan.estore.model;

public class Category extends AbstractModel<Long> {

    private static final long serialVersionUID = -6012566076949775829L;

    private String name;
    private String url;
    private Integer productCount;
    private Integer idLanguage;

    public Category() {
        super();
    }

    public Category(String name, String url, Integer productCount, Integer idLanguage) {
        this.name = name;
        this.url = url;
        this.productCount = productCount;
        this.idLanguage = idLanguage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Integer getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(Integer idLanguage) {
        this.idLanguage = idLanguage;
    }

    @Override
    public String toString() {
        return String.format("Category [id=%s, name=%s, url=%s, productCount=%s]", getId(), name, url, productCount);
    }
}
