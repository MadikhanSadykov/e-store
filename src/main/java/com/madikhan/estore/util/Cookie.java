package com.madikhan.estore.util;

public enum Cookie {
    //1 year ttl
    SHOPPING_CART("iSCC", 60 * 60 * 24 * 30);

    private final String name;
    private final int ttl;

    private Cookie(String name, int ttl) {
        this.name = name;
        this.ttl = ttl;
    }

    public String getName() {
        return name;
    }

    public int getTtl() {
        return ttl;
    }
}