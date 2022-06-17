<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="estore" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="row">
    <form class="form-horizontal" style="margin-top: 10%" action="/profileUpdate" method="post">

        <div class="form-group">
            <div class="col-sm-3">
                <input type="hidden" class="form-control" name="userID" value="${sessionScope.userID}" aria-hidden="true" />
            </div>
        </div>
        <div class="form-group">
            <label for="name" class="col-sm-4 control-label">First Name</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="name" name="name" placeholder="Name" value="${sessionScope.userName}" required />
            </div>
        </div>
        <div class="form-group">
            <label for="surname" class="col-sm-4 control-label">Second Name</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="surname" name="surname" placeholder="Surname" value="${sessionScope.userSurname}">
            </div>
        </div>
        <div class="form-group">
            <label for="email" class="col-sm-4 control-label">Email</label>
            <div class="col-sm-3">
                <input type="email" class="form-control" id="email" name="email" placeholder="Email" value="${sessionScope.userEmail}" required />
                <c:if test="${not empty requestScope.emailIsWrong}">
                    <small class="text-danger">${requestScope.emailIsWrong}</small>
                </c:if>
            </div>
        </div>
        <div class="form-group">
            <label for="phoneNumber" class="col-sm-4 control-label">Phone number</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" placeholder="Phone number" value="${sessionScope.userPhoneNumber}">
            </div>
        </div>
        <div class="form-group">
            <label for="address" class="col-sm-4 control-label">Address</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="address" name="address" placeholder="Address" value="${sessionScope.userAddress}">
            </div>
        </div>

        <c:choose>
            <c:when test="${not empty requestScope.newPasswordIsWrong}">
                <div class="form-group has-error has-feedback">
                    <label class="col-sm-4 control-label" for="newPasswordError">${requestScope.newPasswordIsWrong}</label>
                    <div class="col-sm-3">
                        <input type="password" class="form-control" name="newPassword" id="newPasswordError" >
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="form-group">
                    <label for="newPassword" class="col-sm-4 control-label">New Password</label>
                    <div class="col-sm-3">
                        <input type="password" class="form-control" id="newPassword" name="newPassword" placeholder="New Password">
                    </div>
                </div>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${not empty requestScope.confirmNewPasswordIsWrong}">
                <div class="form-group has-error has-feedback">
                    <label class="col-sm-4 control-label" for="confirmNewPasswordError">${requestScope.confirmNewPasswordIsWrong}</label>
                    <div class="col-sm-3">
                        <input type="password" class="form-control" name="confirmNewPassword" id="confirmNewPasswordError" >
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="form-group">
                    <label for="confirmNewPassword" class="col-sm-4 control-label">Confirm New Password</label>
                    <div class="col-sm-3">
                        <input type="password" class="form-control" id="confirmNewPassword" name="confirmNewPassword" placeholder="Confirm New Password">
                    </div>
                </div>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${not empty requestScope.passwordAuthError}">
                <div class="form-group has-error has-feedback">
                    <label class="col-sm-4 control-label" for="passwordError"><c:out value="${requestScope.passwordAuthError}" /></label>
                    <div class="col-sm-3">
                        <input type="password" class="form-control" name="password" id="passwordError" required />
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="form-group">
                    <label for="password" class="col-sm-4 control-label"> Your Password</label>
                    <div class="col-sm-3">
                        <input type="password" class="form-control" id="password" name="password" placeholder="Your Password" required />
                    </div>
                </div>
            </c:otherwise>
        </c:choose>


        <div class="form-group">
            <div class="col-sm-offset-4 col-sm-3">
                <button type="submit" class="btn btn-success"> Update </button>
            </div>
        </div>
    </form>

</div>
