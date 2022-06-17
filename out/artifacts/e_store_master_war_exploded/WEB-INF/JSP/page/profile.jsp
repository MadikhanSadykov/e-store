<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="estore" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="row">

    <div class="col-sm-offset-4 col-sm-4" style="margin-top: 10%">
        <table class="table table-condensed" >
            <tr>
                <th>Name</th><td>${sessionScope.userName}</td>
            </tr>
            <tr>
                <th>Surname</th><td>${sessionScope.userSurname}</td>
            </tr>
            <tr>
                <th>Email</th><td>${sessionScope.userEmail}</td>
            </tr>
            <tr>
                <th>Phone Number</th><td>${sessionScope.userPhoneNumber}</td>
            </tr>
            <tr>
                <th>Address</th><td>${sessionScope.userAddress}</td>
            </tr>
        </table>
    </div>



    <div class="col-sm-offset-4 col-sm-3">
        <a href="/changeProfile" class="btn btn-primary navbar-btn navbar-right sign-in">
            <i class="fa-sign-in-alt" aria-hidden="true"></i>Change Profile</a>
    </div>


</div>
