<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="ishop" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />
<div id="shoppingCart">
    <div class="alert alert-warning hidden-print" role="alert"><fmt:message key="sign.in.to.order"/></div>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th><fmt:message key="product"/></th>
            <th><fmt:message key="price"/></th>
            <th><fmt:message key="count"/></th>
            <th class="hidden-print"><fmt:message key="action"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${sessionScope.CURRENT_SHOPPING_CART.items }" >
            <tr id="product${item.product.id}" class="item">
                <td class="text-center"><img class="small" src="${item.product.imageLink}" alt="${item.product.name}"><br>${item.product.name}</td>
                <td class="price">$ ${item.product.price}</td>
                <td class="productCount">${item.productCount}</td>
                <td class="hidden-print">
                    <c:choose>
                        <c:when test="${item.productCount > 1}">
                            <a class="btn btn-danger remove-product" data-id-product="${item.product.id}" data-count="1"><fmt:message key="remove.one"/></a>
                            <a class="btn btn-danger remove-product all" data-id-product="${item.product.id}" data-count="${item.productCount}">
                                <fmt:message key="remove.all"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-danger remove-product" data-id-product="${item.product.id}" data-count="1"><fmt:message key="remove.one"/> </a>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="2" class="text-right"><strong>Total:</strong></td>
            <td colspan="2" class="total">$ ${sessionScope.CURRENT_SHOPPING_CART.totalCost}</td>
        </tr>
        </tbody>
    </table>
    <div class="row hidden-print">
        <div class="col-md-4 col-md-offset-4 col-lg-2 col-lg-offset-5">
            <c:choose>
                <c:when test="${not empty sessionScope.CURRENT_USER}">
                    <a href="/create-order" class="btn btn-primary btn-block sign-in">
                        <i class="fa-sign-in-alt" aria-hidden="true"></i><fmt:message key="make.order"/></a>
                </c:when>
                <c:otherwise>
                    <a class="btn btn-primary btn-block sign-in" href="/loginPage">
                        <i class="fa-sign-in-alt" aria-hidden="true"></i><fmt:message key="sign.in"/> </a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>