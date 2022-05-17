<%--
  Created by IntelliJ IDEA.
  User: sssad
  Date: 5/18/2022
  Time: 2:17 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="alert alert-info">
    <p>Found <strong>${productCount }</strong> products</p>
</div>

<jsp:include page="products.jsp" />