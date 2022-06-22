<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />
<div class="row">
    <div class="col-sm-offset-4 col-sm-3" style="margin-top: 10%">
        <a href="/adminUsersPage" class="btn btn-primary btn-block">
            <i class="fa-sign-in-alt" aria-hidden="true"></i>
            <fmt:message key="users" />
        </a>
    </div>

    <div class="col-sm-offset-4 col-sm-3" style="margin-top: 10%; margin-bottom: 10%">
        <a href="/adminProductsPage" class="btn btn-primary btn-block">
            <i class="fa-sign-in-alt" aria-hidden="true"></i>
            <fmt:message key="products" />
        </a>
    </div>

    <div class="col-sm-offset-4 col-sm-3">
        <a href="/adminOrdersPage" class="btn btn-primary btn-block">
            <i class="fa-sign-in-alt" aria-hidden="true"></i>
            <fmt:message key="orders" />
        </a>
    </div>
</div>