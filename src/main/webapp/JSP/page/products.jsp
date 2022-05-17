<%--
  Created by IntelliJ IDEA.
  User: sssad
  Date: 5/17/2022
  Time: 5:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="estore" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div id="productList">

        <jsp:include page="../fragment/product-list.jsp" />


    <div class="text-center hidden-print">
        <img id="loadMoreIndicator" src="${pageContext.request.contextPath}/static/img/loading.gif" class="hidden" alt="Loading...">
        <a id="loadMore" class="btn btn-success">Load more products</a>
    </div>
</div>
<estore:add-product-popup />