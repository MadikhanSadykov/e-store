package com.madikhan.estore.model;

import java.math.BigDecimal;

public class Product extends AbstractModel<Long> {

    private static final long serialVersionUID = 5106768120687963456L;

    private String name;
    private String description;
    private String imageLink;
    private BigDecimal price;
    private String category;
    private Long categoryID;
    private String producer;
    private Long producerID;

    public Product() {
        super();
    }

    public Product(String name, String description, String imageLink, BigDecimal price, String category, String producer, Integer idLanguage) {
        this.name = name;
        this.description = description;
        this.imageLink = imageLink;
        this.price = price;
        this.category = category;
        this.producer = producer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    public Long getProducerID() {
        return producerID;
    }

    public void setProducerID(Long producerID) {
        this.producerID = producerID;
    }

    @Override
    public String toString() {
        return String.format("Product [id=%s, name=%s, description=%s, imageLink=%s, " +
                        "price=%s, idCategory=%s, idProducer=%s",
                super.getId(), name, description, imageLink, price, category, producer);
    }
}
