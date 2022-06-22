<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />

<nav class="navbar navbar-default">

    <div class="container-fluid" style="background-color: #000000">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#ishopNav" aria-expanded="false">
                <span class="sr-only"><fmt:message key="toggle.navigation" /></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-header" href="/products">
                <img src="/static/img/logo.png" href="/products" alt="EStore">
            </a>
        </div>



        <div class="collapse navbar-collapse" id="ishopNav">



            <ul id="currentShoppingCart" class="nav navbar-nav navbar-right
            ${(sessionScope.CURRENT_SHOPPING_CART == null or sessionScope.CURRENT_SHOPPING_CART.totalCount == 0)  ? 'hidden' : ''}" >
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        <i class="fa fa-shopping-cart" aria-hidden="true"></i><fmt:message key="shopping.cart"/> (<span class="total-count">${sessionScope.CURRENT_SHOPPING_CART.totalCount}</span>)<span class="caret"></span>
                    </a>
                    <div class="dropdown-menu shopping-cart-desc">
                        Total count: <span class="total-count">${sessionScope.CURRENT_SHOPPING_CART.totalCount}</span><br>
                        Total cost: <span class="total-cost">${sessionScope.CURRENT_SHOPPING_CART.totalCost}</span><br>
                        <a href="/shopping-cart" class="btn btn-primary btn-block"><fmt:message key="view.cart" /></a>
                    </div>
                </li>
            </ul>

            <div class="navbar-right navbar-btn">
                <select class="form-control" id="language" name="language">
                    <c:forEach var="language" items="${requestScope.languageList}">
                        <option value="${language.shortName}" ${language.shortName == sessionScope.language ? 'selected' : ''}>
                                ${language.name}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <c:if test="${empty sessionScope.userName}">
                <a href="/loginPage" class="btn btn-primary navbar-btn navbar-right sign-in">
                    <i class="fa-sign-in-alt" aria-hidden="true"></i><fmt:message key="sign.in"/> </a>
            </c:if>

            <c:if test="${not empty sessionScope.CURRENT_USER.name}">
                <a href="/my-orders" class="btn btn-primary navbar-btn navbar-right sign-in">
                    <i class="fa-sign-in-alt" aria-hidden="true"></i><fmt:message key="my.orders" /></a>
            </c:if>

            <c:if test="${not empty sessionScope.CURRENT_USER.name}">
                <a href="/logOut" class="btn btn-primary navbar-btn navbar-right sign-in">
                    <i class="fa-sign-in-alt" aria-hidden="true"></i>
                    <fmt:message key="log.out" />
                </a>
            </c:if>

            <c:if test="${not empty sessionScope.CURRENT_USER.email}">
                <a href="/profile" class="btn btn-primary navbar-btn navbar-right sign-in">
                <i class="fa-sign-in-alt" aria-hidden="true"></i>
                <c:out value="${sessionScope.CURRENT_USER.email}" />
                </a>
            </c:if>

            <c:if test="${sessionScope.CURRENT_USER.isAdmin}" >
                <a href="/adminPage" class="btn btn-primary navbar-btn navbar-right sign-in">
                    <i class="fa-sign-in-alt" aria-hidden="true"></i>
                    <fmt:message key="admin" />
                </a>
            </c:if>


        </div>
    </div>
</nav>
