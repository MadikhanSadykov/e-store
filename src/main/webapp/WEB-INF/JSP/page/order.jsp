<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="estore" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div id="order">
  <c:if test="${sessionScope.CURRENT_MESSAGE != null }">
    <div class="alert alert-success hidden-print" role="alert">${sessionScope.CURRENT_MESSAGE }</div>
  </c:if>
  <h4 class="text-center">Order # ${requestScope.order.id }</h4>
  <hr />
  <estore:product-table items="${requestScope.order.orderItems }" totalCost="${requestScope.order.totalCost }" showActionColumn="false" />
  <div class="row hidden-print">
    <div class="col-md-4 col-md-offset-4 col-lg-2 col-lg-offset-5">
      <a href="/my-orders" class="btn btn-primary btn-block">Go to My orders</a>
    </div>
  </div>
</div>