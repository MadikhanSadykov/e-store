<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="estore" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />
<h4 class="text-center">All orders</h4>
<hr />
<table id="allOrders" class="table table-bordered" data-page-number="1" data-page-count="${requestScope.pageCount }">
    <thead>
    <tr>
        <th>ID</th>

        <th><fmt:message key="user.name" /> </th>
        <th><fmt:message key="user.email" /> </th>
        <th><fmt:message key="user.phone" /></th>
        <th><fmt:message key="user.address" /></th>

        <th><fmt:message key="created" /></th>
        <th><fmt:message key="finished" /></th>
        <th><fmt:message key="total.cost" /></th>
        <th><fmt:message key="status" /> </th>
        <th><fmt:message key="action" /></th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${empty requestScope.orders }">
        <tr>
            <td colspan="2" class="text-center"><fmt:message key="no.orders.found" /></td>
        </tr>
    </c:if>
    <jsp:include page="../fragment/admin-orders-tbody.jsp" />
    </tbody>

</table>


<div class="text-center hidden-print">
    <c:if test="${requestScope.pageCount > 1 }">
        <a id="loadMoreAllOrders" class="btn btn-primary btn-lg btn-block"><b><fmt:message key="load.more.orders" /></b></a>
    </c:if>
</div>