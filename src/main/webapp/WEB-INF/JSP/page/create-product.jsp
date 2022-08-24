<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="estore" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />

<div class="row">
    <form class="form-horizontal" style="margin-top: 10%" action="/createProduct" method="post">


        <div class="form-group">
            <label for="name" class="col-sm-4 control-label"><fmt:message key="name"/></label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="name" name="name" placeholder="<fmt:message key="name"/>"  required />
            </div>
        </div>
        <div class="form-group">
            <label for="description" class="col-sm-4 control-label"><fmt:message key="description"/></label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="description" name="description" placeholder="<fmt:message key="description"/>" required />
            </div>
        </div>
        <div class="form-group">
            <label for="description" class="col-sm-4 control-label"><fmt:message key="category"/></label>
            <div class="col-sm-3">
                <select class="form-control" id="categoryID" name="categoryID" required>
                    <c:forEach var="category" items="${requestScope.categoryList}">
                        <option value="${category.id}" >
                                ${category.name}
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="description" class="col-sm-4 control-label"><fmt:message key="producer"/></label>
            <div class="col-sm-3">
                <select class="form-control" id="producerID" name="producerID" required>
                    <c:forEach var="producer" items="${requestScope.producerList}">
                        <option value="${producer.id}" >
                                ${producer.name}
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="description" class="col-sm-4 control-label"><fmt:message key="price"/> $</label>
            <div class="col-sm-3">
                <input type="number" class="form-control" id="price" name="price" placeholder="<fmt:message key="price"/> $" required />
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-4 col-sm-3">
                <button type="submit" class="btn btn-success"> <fmt:message key="save"/> </button>
            </div>
        </div>
    </form>

</div>
