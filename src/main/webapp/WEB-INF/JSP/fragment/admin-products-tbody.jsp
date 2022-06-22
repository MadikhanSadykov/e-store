<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />
<c:forEach var="product" items="${requestScope.products }">
    <tr id="product${product.id}" class="item">
        <td>${product.id }</td>
        <td>${product.name}</td>
        <td>${product.description}</td>
        <td>${product.category}</td>
        <td>${product.producer}</td>
        <td>${product.price}</td>
        <td>
            <a class="btn btn-warning" href="/updateProductPage?id=${product.id}" ><fmt:message key="update"/></a>
            <a class="btn btn-danger delete-product" data-id-product="${product.id}" onclick="deleteProduct(this)" ><fmt:message key="delete"/></a>
        </td>
    </tr>
</c:forEach>