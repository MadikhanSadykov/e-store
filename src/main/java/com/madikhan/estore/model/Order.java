package com.madikhan.estore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Order extends AbstractModel<Long> {

    private static final long serialVersionUID = 2937379598362283529L;

    private List<OrderItem> orderItems;
    private Timestamp created;
    private Timestamp finished;
    private Long idUser;
    private Integer idStatus;

    public Order() {
        super();
    }

    public Order(List<OrderItem> orderItems, Timestamp created, Timestamp finished, Long idUser, Integer idStatus) {
        this.orderItems = orderItems;
        this.created = created;
        this.finished = finished;
        this.idUser = idUser;
        this.idStatus = idStatus;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getFinished() {
        return finished;
    }

    public void setFinished(Timestamp finished) {
        this.finished = finished;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    public BigDecimal getTotalCost() {
        BigDecimal totalCost = BigDecimal.ZERO;
        if (orderItems != null) {
            for (OrderItem item : orderItems) {
                totalCost = totalCost.add(item.getCost().multiply(BigDecimal.valueOf(item.getProductCount())));
            }
        }
        return totalCost;
    }


    @Override
    public String toString() {
        return String.format("Order [id=%s, totalCost=%s, created=%s, finished=%s, idUser=%s, idStatus=%s",
                getId(), getTotalCost(), created, finished, idUser, idStatus);
    }
}
