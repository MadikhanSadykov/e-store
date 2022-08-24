package com.madikhan.estore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Order extends AbstractModel<Long> {

    private static final long serialVersionUID = 2937379598362283529L;

    private List<OrderItem> orderItems;
    private BigDecimal totalCost;
    private Timestamp created;
    private Timestamp finished;
    private Long idUser;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String userAddress;

    private Integer idStatus;
    private String status;

    public Order() {
        super();
    }

    public Order(List<OrderItem> orderItems, BigDecimal totalCost, Timestamp created, Timestamp finished, Long idUser, Integer idStatus) {
        this.orderItems = orderItems;
        this.totalCost = totalCost;
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

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
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

    public BigDecimal getTotalCostItems() {
        BigDecimal totalCost = BigDecimal.ZERO;
        if (orderItems != null) {
            for (OrderItem item : orderItems) {
                totalCost = totalCost.add(item.getCost().multiply(BigDecimal.valueOf(item.getProductCount())));
            }
        }
        return totalCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    @Override
    public String toString() {
        return String.format("Order [id=%s, totalCost=%s, created=%s, finished=%s, idUser=%s, idStatus=%s",
                getId(), getTotalCost(), created, finished, idUser, idStatus);
    }
}
