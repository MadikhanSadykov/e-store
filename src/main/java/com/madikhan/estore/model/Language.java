package com.madikhan.estore.model;

public class Language extends AbstractModel<Integer>{

    private static final long serialVersionUID = -858456723745560796L;

    private String name;
    private String shortName;

    public Language() {
        super();
    }

    public Language(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return String.format("Language [id=%s, name=%s, shortName=%s", getId(), name, shortName);
    }
}
