<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="estore" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />

<div class="row">
    <form class="form-horizontal" style="margin-top: 10%" action="/login" method="post">
        <c:choose>
            <c:when test="${not empty requestScope.emailAuthError}">
                <div class="form-group has-error has-feedback">
                    <label class="col-sm-4 control-label" for="emailError"><fmt:message key="email.is.wrong"/></label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" name="email" id="emailError"
                               value="${requestScope.userEmail}" required autocomplete="off" />
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="form-group">
                    <label for="email" class="col-sm-4 control-label"><fmt:message key="email"/></label>
                    <div class="col-sm-3">
                        <input type="email" class="form-control" id="email" name="email" placeholder="<fmt:message key="email"/>"
                        value="<c:if test="${not empty requestScope.passwordAuthError}">"
                        <c:out value="${requestScope.userEmail}" />
                        </c:if>" required autocomplete="off" />
                    </div>
                </div>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${not empty requestScope.passwordAuthError}">
                <div class="form-group has-error has-feedback">
                    <label class="col-sm-4 control-label" for="passwordError"><fmt:message key="password.is.wrong"/></label>
                    <div class="col-sm-3">
                        <input type="password" class="form-control" name="password" id="passwordError" required autocomplete="off" />
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="form-group">
                    <label for="password" class="col-sm-4 control-label"><fmt:message key="password"/></label>
                    <div class="col-sm-3">
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="<fmt:message key="password"/>" required autocomplete="off" />
                    </div>
                </div>
            </c:otherwise>
        </c:choose>


        <div class="col-sm-offset-4 col-sm-3">
            <span><fmt:message key="do.not.have.account" /> </span> <a href="/registrationPage"><fmt:message key="create.account"/></a>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-4 col-sm-3">
                <button type="submit" class="btn btn-default"><fmt:message key="sign.in"/></button>
            </div>
        </div>
    </form>

</div>
