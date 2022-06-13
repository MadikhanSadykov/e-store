package com.madikhan.estore.model;

public class Status extends AbstractModel<Integer> {

    private static final long serialVersionUID = -8860242627085505937L;

    private String name;
    private Integer idLanguage;

    public Status() {
        super();
    }

    public Status(String name, Integer idLanguage) {
        this.name = name;
        this.idLanguage = idLanguage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(Integer idLanguage) {
        this.idLanguage = idLanguage;
    }

    @Override
    public String toString() {
        return String.format("Status [id=%s, name=%s, idLanguage=%s", getId(), name, idLanguage);
    }
}
