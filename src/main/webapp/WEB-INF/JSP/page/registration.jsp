<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="estore" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />

<div class="row">
    <form class="form-horizontal" style="margin-top: 10%" action="/register" method="post">
        <div class="form-group">
            <label for="name" class="col-sm-4 control-label"><fmt:message key="first.name"/></label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="name" name="name" placeholder="<fmt:message key="name"/>" >
            </div>
        </div>
        <div class="form-group">
            <label for="email" class="col-sm-4 control-label"><fmt:message key="email"/></label>
            <div class="col-sm-3">
                <input type="email" class="form-control" id="email" name="email" placeholder="<fmt:message key="email"/>"
                value="<c:if test="${not empty requestScope.email}">
                        <c:out value="${requestScope.email}" />
                       </c:if>" >
                <c:if test="${not empty requestScope.emailIsWrong}">
                    <small class="text-danger">${requestScope.emailIsWrong}</small>
                </c:if>
            </div>
        </div>
        <div class="form-group">
            <label for="password" class="col-sm-4 control-label"><fmt:message key="password"/></label>
            <div class="col-sm-3">
                <input type="password" class="form-control" id="password" name="password" placeholder="<fmt:message key="password"/>" >
                <c:if test="${not empty requestScope.passwordIsWrong}">
                    <small class="text-danger">${requestScope.passwordIsWrong}</small>
                </c:if>
            </div>
        </div>
        <div class="form-group">
            <label for="confirmPassword" class="col-sm-4 control-label"><fmt:message key="confirm.password"/></label>
            <div class="col-sm-3">
                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="<fmt:message key="confirm.password"/>">
                <c:if test="${not empty requestScope.passwordIsWrong}">
                    <small class="text-danger">${requestScope.passwordIsWrong}</small>
                </c:if>
            </div>
        </div>
        <div class="col-sm-offset-4 col-sm-3">
            <span><fmt:message key="do.you.have.account"/> </span>
            <br>
            <a href="/loginPage"><fmt:message key="sign.in"/></a>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-4 col-sm-3">
                <button type="submit" class="btn btn-default"><fmt:message key="sign.up"/></button>
            </div>
        </div>
    </form>

</div>
