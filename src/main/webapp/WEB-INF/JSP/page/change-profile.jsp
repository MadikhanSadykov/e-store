<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="estore" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />

<div class="row">
    <form class="form-horizontal" style="margin-top: 10%" action="/profileUpdate" method="post">

        <c:if test="${not empty requestScope.UserDataIsNotComplete}">
            <div class="col-sm-offset-4 col-sm-3">
                <p class="text-danger">${requestScope.UserDataIsNotComplete}</p>
            </div>
        </c:if>
        <div class="form-group">
            <div class="col-sm-3">
                <input type="hidden" class="form-control" name="userID"
                       value="${sessionScope.CURRENT_USER.id}" aria-hidden="true" />
            </div>
        </div>
        <div class="form-group">
            <label for="name" class="col-sm-4 control-label"><fmt:message key="first.name"/></label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="name" name="name" placeholder="Name"
                       value="<c:choose>
<c:when test="${not empty requestScope.name}">${requestScope.name}</c:when>
<c:otherwise>${sessionScope.CURRENT_USER.name}</c:otherwise></c:choose>" required />
            </div>
        </div>
        <div class="form-group">
            <label for="surname" class="col-sm-4 control-label"><fmt:message key="second.name" /> </label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="surname" name="surname" placeholder="Surname"
                       value="<c:choose>
<c:when test="${not empty requestScope.surname}">${requestScope.surname}</c:when>
<c:otherwise>${sessionScope.CURRENT_USER.surname}</c:otherwise></c:choose>" />
            </div>
        </div>
        <div class="form-group">
            <label for="email" class="col-sm-4 control-label"><fmt:message key="email" /></label>
            <div class="col-sm-3">
                <input type="email" class="form-control" id="email" name="email" placeholder="Email"
                       value="<c:choose>
<c:when test="${not empty requestScope.email}">${requestScope.email}</c:when>
<c:otherwise>${sessionScope.CURRENT_USER.email}</c:otherwise></c:choose>" required />
                <c:if test="${not empty requestScope.emailIsWrong}">
                    <small class="text-danger">${requestScope.emailIsWrong}</small>
                </c:if>
            </div>
        </div>
        <div class="form-group">
            <label for="phoneNumber" class="col-sm-4 control-label"><fmt:message key="phone.number"/></label>
            <div class="col-sm-3">
                <input type="tel" class="form-control tel" id="phoneNumber" name="phoneNumber"
                       value="<c:choose>
<c:when test="${not empty requestScope.phoneNumber}">${requestScope.phoneNumber}</c:when>
<c:otherwise>${sessionScope.CURRENT_USER.phoneNumber}</c:otherwise></c:choose>" />
                <c:if test="${not empty requestScope.phoneNumberWrong}">
                    <small class="text-danger"><fmt:message key="${requestScope.phoneNumberWrong}" /> </small>
                </c:if>
            </div>
        </div>
        <div class="form-group">
            <label for="address" class="col-sm-4 control-label"><fmt:message key="address"/></label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="address" name="address" placeholder="Address"
                       value="<c:choose>
<c:when test="${not empty requestScope.address}">${requestScope.address}</c:when>
<c:otherwise>${sessionScope.CURRENT_USER.address}</c:otherwise></c:choose>" />
            </div>
        </div>

        <c:choose>
            <c:when test="${not empty requestScope.newPasswordIsWrong}">
                <div class="form-group has-error has-feedback">
                    <label class="col-sm-4 control-label" for="newPasswordError">
                            <fmt:message key="${requestScope.newPasswordIsWrong}" /> </label>
                    <div class="col-sm-3">
                        <input type="password" class="form-control" name="newPassword" id="newPasswordError" autocomplete="off" />
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="form-group">
                    <label for="newPassword" class="col-sm-4 control-label"><fmt:message key="new.password"/></label>
                    <div class="col-sm-3">
                        <input type="password" class="form-control" id="newPassword" name="newPassword"
                               placeholder="<fmt:message key="new.password"/>" autocomplete="off" />
                    </div>
                </div>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${not empty requestScope.confirmNewPasswordIsWrong}">
                <div class="form-group has-error has-feedback">
                    <label class="col-sm-4 control-label" for="confirmNewPasswordError">
                            <fmt:message key="${requestScope.confirmNewPasswordIsWrong}" />
                    </label>
                    <div class="col-sm-3">
                        <input type="password" class="form-control" name="confirmNewPassword" id="confirmNewPasswordError"
                               autocomplete="off" />
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="form-group">
                    <label for="confirmNewPassword" class="col-sm-4 control-label">
                        <fmt:message key="confirm.new.password"/>
                    </label>
                    <div class="col-sm-3">
                        <input type="password" class="form-control" id="confirmNewPassword" name="confirmNewPassword"
                               placeholder="<fmt:message key="confirm.new.password"/>" autocomplete="off" />
                    </div>
                </div>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${not empty requestScope.passwordAuthError}">
                <div class="form-group has-error has-feedback">
                    <label class="col-sm-4 control-label" for="passwordError">
                        <fmt:message key="${requestScope.passwordAuthError}" />
                    </label>
                    <div class="col-sm-3">
                        <input type="password" class="form-control" name="password" id="passwordError" required />
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="form-group">
                    <label for="password" class="col-sm-4 control-label"> <fmt:message key="your.password"/></label>
                    <div class="col-sm-3">
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="<fmt:message key="your.password"/>" required />
                    </div>
                </div>
            </c:otherwise>
        </c:choose>


        <div class="form-group">
            <div class="col-sm-offset-4 col-sm-3">
                <button type="submit" class="btn btn-success"> <fmt:message key="update"/> </button>
            </div>
        </div>
    </form>

</div>
