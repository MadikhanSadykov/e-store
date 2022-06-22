<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="estore" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />

<div id="productList" data-page-count="${requestScope.pageCount}" data-page-number="1">

    <div class="row">
        <jsp:include page="../fragment/product-list.jsp" />
    </div>

    <c:if test="${requestScope.pageCount > 1}" >
        <div class="text-center hidden-print">
            <img id="loadMoreIndicator" src="${pageContext.request.contextPath}/static/img/loading.gif" class="hidden" alt="Loading...">
            <a id="loadMore" class="btn btn-primary btn-lg btn-block"><b><fmt:message key="load.more.products"/> </b></a>
        </div>
    </c:if>
</div>
<estore:add-product-popup />