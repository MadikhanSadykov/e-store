<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />

<div class="alert alert-danger hidden-print" role="alert">
    <h1>Code: ${statusCode }</h1>
    <c:choose>
        <c:when test="${statusCode == 403}"><fmt:message key="403"/></c:when>
        <c:when test="${statusCode == 404}"><fmt:message key="404"/></c:when>
        <c:otherwise><fmt:message key="error.page.message"/></c:otherwise>
    </c:choose>
</div>