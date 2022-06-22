<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="estore" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />
<h4 class="text-center"><fmt:message key="all.products"/></h4>
<a href="/createProductPage" class="btn btn-success btn-block btn-lg"><fmt:message key="create.product"/></a>
<hr />
<table id="allProducts" class="table table-bordered" data-page-number="1" data-page-count="${requestScope.pageCount }">
    <thead>
    <tr>
        <th>Id</th>
        <th><fmt:message key="name" /></th>
        <th><fmt:message key="description" /></th>
        <th><fmt:message key="category" /></th>
        <th><fmt:message key="producer" /> </th>
        <th><fmt:message key="price" /></th>
        <th><fmt:message key="action" /></th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${empty requestScope.products }">
        <tr>
            <td colspan="2" class="text-center"><fmt:message key="no.products.found" /></td>
        </tr>
    </c:if>
    <jsp:include page="../fragment/admin-products-tbody.jsp" />
    </tbody>
</table>



    <c:if test="${requestScope.pageCount > 1}" >
        <div class="text-center hidden-print">
            <img id="loadMoreIndicator" src="${pageContext.request.contextPath}/static/img/loading.gif" class="hidden" alt="Loading...">
            <a id="loadMoreAdminProducts" class="btn btn-primary btn-lg btn-block"><b><fmt:message key="load.more.products"/></b></a>
        </div>
    </c:if>
