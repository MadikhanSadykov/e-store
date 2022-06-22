<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="estore" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />

<div class="row">

    <div class="col-sm-offset-4 col-sm-4" style="margin-top: 10%">
        <table class="table table-condensed" >
            <tr>
                <th><fmt:message key="name"/></th><td>${sessionScope.CURRENT_USER.name}</td>
            </tr>
            <tr>
                <th><fmt:message key="surname"/></th><td>${sessionScope.CURRENT_USER.surname}</td>
            </tr>
            <tr>
                <th><fmt:message key="email"/></th><td>${sessionScope.CURRENT_USER.email}</td>
            </tr>
            <tr>
                <th><fmt:message key="phone.number"/> </th><td>${sessionScope.CURRENT_USER.phoneNumber}</td>
            </tr>
            <tr>
                <th><fmt:message key="address"/></th><td>${sessionScope.CURRENT_USER.address}</td>
            </tr>
        </table>
    </div>



    <div class="col-sm-offset-4 col-sm-3">
        <a href="/changeProfile" class="btn btn-primary navbar-btn navbar-right sign-in">
            <i class="fa-sign-in-alt" aria-hidden="true"></i><fmt:message key="change.profile"/></a>
    </div>


</div>
