<%--
  Created by IntelliJ IDEA.
  User: sssad
  Date: 5/17/2022
  Time: 5:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="language" />

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#ishopNav" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <img src="/static/img/logo.png" href="/products" alt="EStore">
<%--            <a class="navbar-brand" href="/products">EStore</a>--%>
        </div>



        <div class="collapse navbar-collapse" id="ishopNav">



            <ul id="language" class="nav navbar-nav navbar-right hidden">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        <i class="fa fa-shopping-cart" aria-hidden="true"></i> Shopping cart (<span class="total-count">0</span>)<span class="caret"></span>
                    </a>
                    <div class="dropdown-menu shopping-cart-desc">
                        Total count: <span class="total-count">0</span><br>
                        Total cost: <span class="total-cost">0</span><br>
                        <a href="/shopping-cart" class="btn btn-primary btn-block">View cart</a>
                    </div>
                </li>
            </ul>

            <a href="#" class="btn btn-primary navbar-btn navbar-right sign-in"><i class="fa fa-facebook-official" aria-hidden="true"></i><fmt:message key="sign.in"></a>
        </div>
    </div>
</nav>
