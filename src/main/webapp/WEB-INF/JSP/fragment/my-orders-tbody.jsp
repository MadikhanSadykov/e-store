<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />
<c:forEach var="order" items="${requestScope.orders }">
    <tr class="item">
        <td><a href="/order?id=${order.id }"><fmt:message key="order" /> # ${order.id }</a></td>
        <td><fmt:formatDate value="${order.created }" pattern="yyyy-MM-dd HH:mm" /></td>
        <c:choose>
            <c:when test="${not empty order.finished}">
                <td><fmt:formatDate value="${order.finished }" pattern="yyyy-MM-dd HH:mm" /></td>
            </c:when>
            <c:otherwise>
                <td><fmt:message key="not.finished"/></td>
            </c:otherwise>
        </c:choose>
        <td>$ ${order.totalCost}</td>
        <td>${order.status}</td>
    </tr>
</c:forEach>