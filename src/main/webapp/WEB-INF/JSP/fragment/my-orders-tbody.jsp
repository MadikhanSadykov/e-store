<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:forEach var="order" items="${requestScope.orders }">
    <tr class="item">
        <td><a href="/order?id=${order.id }">Order # ${order.id }</a></td>
        <td><fmt:formatDate value="${order.created }" pattern="yyyy-MM-dd HH:mm" /></td>
        <c:choose>
            <c:when test="${not empty order.finished}">
                <td><fmt:formatDate value="${order.finished }" pattern="yyyy-MM-dd HH:mm" /></td>
            </c:when>
            <c:otherwise>
                <td>Not finished</td>
            </c:otherwise>
        </c:choose>
        <td>$ ${order.totalCost}</td>
        <td>${order.status}</td>
    </tr>
</c:forEach>