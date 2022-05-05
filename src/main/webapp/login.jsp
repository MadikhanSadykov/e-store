<%--
  Created by IntelliJ IDEA.
  User: sssad
  Date: 5/5/2022
  Time: 3:11 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
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
                    <li class="active">Login</li>
                </ul>
                <h3> Login</h3>
                <hr class="soft"/>

                <div class="row">
                    <div class="span4">
                        <div class="well">
                            <h5>CREATE YOUR ACCOUNT</h5><br/>
                            Enter your e-mail address to create an account.<br/><br/><br/>
                            <form action="" method="">
                                <div class="control-group">
                                    <label class="control-label" for="email">Email</label>
                                    <div class="controls">
                                        <input class="span3" type="text" id="email" placeholder="Email">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="password">Password</label>
                                    <div class="controls">
                                        <input type="password" class="span3" id="password" placeholder="Password">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <div class="controls">
                                        <button type="submit" class="btn">Sign in</button> <a href="forgetpass.html">Forget password?</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>
</body>
</html>
