package com.madikhan.estore.model;

import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractModel<T> implements Serializable {

    private static final long serialVersionUID = 7983924542842026918L;

    T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AbstractModel<?> that = (AbstractModel<?>) object;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%s [id=%s]", getClass().getSimpleName(), id);
    }
}
