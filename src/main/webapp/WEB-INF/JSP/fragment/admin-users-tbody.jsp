<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />
<c:forEach var="user" items="${requestScope.users }">
    <tr class="item">
        <td>${user.id }</td>
        <td>${user.name}  ${user.surname}</td>
        <td>${user.email}</td>
        <td>${user.address}</td>
        <td>${user.isAdmin}</td>
        <td>
            <c:choose>
                <c:when test="${user.isAdmin}">
                    <a class="btn btn-success as-user" data-id-user="${user.id}"><fmt:message key="mark.as.user" /></a>
                </c:when>
                <c:otherwise>
                    <a class="btn btn-warning as-admin" data-id-user="${user.id}"><fmt:message key="mark.as.admin" /></a>
                </c:otherwise>
            </c:choose>
            <a class="btn btn-danger delete-user" data-id-user="${user.id}"><fmt:message key="delete"/></a>
        </td>

    </tr>
</c:forEach>