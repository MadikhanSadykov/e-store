<%--
  Created by IntelliJ IDEA.
  User: sssad
  Date: 5/5/2022
  Time: 2:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html>
<head>
    <title>Register</title>

    <jsp:include page="head.jsp" />

</head>

<body>

<jsp:include page="header.jsp"/>

<div id="mainBody">
    <div class="container">
        <div class="row">

            <jsp:include page="slidebar.jsp" />

            <div class="span9">
                <ul class="breadcrumb">
                    <li><a href="index.html">Home</a> <span class="divider">/</span></li>
                    <li class="active">Registration</li>
                </ul>
                <h3> Registration</h3>
                <div class="well">

                    <form class="form-horizontal" action="/register" method="post" >
                        <h4>Your personal information</h4>

                        <div class="control-group">
                            <label class="control-label" for="name">First name <sup>*</sup></label>
                            <div class="controls">
                                <input type="text" name="name" id="name" placeholder="First Name">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="surname">Last name <sup>*</sup></label>
                            <div class="controls">
                                <input type="text" name="surname" id="surname" placeholder="Last Name">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="email">Email <sup>*</sup></label>
                            <div class="controls">
                                <input type="text" name="email" id="email" placeholder="Email">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="phoneNumber">Phone Number <sup>*</sup></label>
                            <div class="controls">
                                <input type="text" name="phoneNumber" id="phoneNumber" placeholder="Phone Number">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="address">Address <sup>*</sup></label>
                            <div class="controls">
                                <input type="text" name="address" id="address" placeholder="Address">
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="password">Password <sup>*</sup></label>
                            <div class="controls">
                                <input type="password" name="password" id="password" placeholder="Password">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="confirmPassword">Confirm Password <sup>*</sup></label>
                            <div class="controls">
                                <input type="password" name="confirmPassword" id="confirmPassword" placeholder="Confirm Password">
                            </div>
                        </div>
                        <div class="control-group">
                            <div class="controls">
                                <input type="hidden" name="email_create" value="1">
                                <input type="hidden" name="is_new_customer" value="1">
                                <input class="btn btn-large btn-success" type="submit" value="Register" />
                            </div>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>
<!-- MainBody End ============================= -->

<jsp:include page="footer.jsp"/>

</body>

</html>