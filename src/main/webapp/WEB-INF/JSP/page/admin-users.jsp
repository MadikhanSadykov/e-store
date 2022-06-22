<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="estore" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />
<h4 class="text-center">All Users</h4>
<hr />
<table id="allUsers" class="table table-bordered" data-page-number="1" data-page-count="${requestScope.pageCount }">
    <thead>
    <tr>
        <th>Id</th>
        <th><fmt:message key="name"/></th>
        <th><fmt:message key="email"/></th>
        <th><fmt:message key="address"/></th>
        <th><fmt:message key="is.admin"/> </th>
        <th><fmt:message key="action"/> </th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${empty requestScope.users }">
        <tr>
            <td colspan="2" class="text-center"><fmt:message key="no.useres.found"/></td>
        </tr>
    </c:if>
    <jsp:include page="../fragment/admin-users-tbody.jsp" />
    </tbody>
</table>


<c:if test="${requestScope.pageCount > 1}" >
    <div class="text-center hidden-print">
        <img id="loadMoreIndicator" src="${pageContext.request.contextPath}/static/img/loading.gif" class="hidden" alt="Loading...">
        <a id="loadMoreAdminUsers" class="btn btn-primary btn-lg btn-block"><b><fmt:message key="load.more.users"/></b></a>
    </div>
</c:if>

