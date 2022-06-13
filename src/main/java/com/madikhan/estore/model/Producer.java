package com.madikhan.estore.model;

public class Producer extends AbstractModel<Long> {

    private static final long serialVersionUID = 2900957609159097565L;

    private String name;
    private int productCount;

    public Producer() {
        super();
    }

    public Producer(String name, int productCount) {
        this.name = name;
        this.productCount = productCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    @Override
    public String toString() {
        return String.format("Producer [id=%s, name=%s, productCount=%s", getId(), name, productCount);
    }
}
