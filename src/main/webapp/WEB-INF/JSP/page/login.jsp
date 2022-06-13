<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="estore" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="row">
    <form class="form-horizontal" style="margin-top: 10%" action="/login" method="post">
        <c:choose>
            <c:when test="${not empty requestScope.emailAuthError}">
                <div class="form-group has-error has-feedback">
                    <label class="col-sm-4 control-label" for="emailError"><c:out value="${requestScope.emailAuthError}" /></label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" name="email" id="emailError"
                               value="${requestScope.email}" required />
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="form-group">
                    <label for="email" class="col-sm-4 control-label">Email</label>
                    <div class="col-sm-3">
                        <input type="email" class="form-control" id="email" name="email" placeholder="Email"
                        value="<c:if test="${not empty requestScope.passwordAuthError}">
"                              <c:out value="${requestScope.email}" />
                               </c:if>"  required />
                    </div>
                </div>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${not empty requestScope.passwordAuthError}">
                <div class="form-group has-error has-feedback">
                    <label class="col-sm-4 control-label" for="passwordError"><c:out value="${requestScope.passwordAuthError}" /></label>
                    <div class="col-sm-3">
                        <input type="password" class="form-control" name="password" id="passwordError"  required />
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="form-group">
                    <label for="password" class="col-sm-4 control-label">Password</label>
                    <div class="col-sm-3">
                        <input type="password" class="form-control" id="password" name="password" placeholder="Password"  required />
                    </div>
                </div>
            </c:otherwise>
        </c:choose>


        <div class="col-sm-offset-4 col-sm-3">
            <span>Don't have account? </span> <a href="/registrationPage">Create Account</a>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-4 col-sm-3">
                <button type="submit" class="btn btn-default">Sign In</button>
            </div>
        </div>
    </form>

</div>
