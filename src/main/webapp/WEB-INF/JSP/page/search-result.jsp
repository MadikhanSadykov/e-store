<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />
<div class="alert alert-info">
    <p><fmt:message key="found"/> <strong>${productCount }</strong> <fmt:message key="products"/></p>
</div>

<jsp:include page="products.jsp" />