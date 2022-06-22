<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="ishop" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />

    <c:forEach var="p" items="${products }">
        <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3 col-xlg-2">
                <div id="product${p.id}" class="panel panel-default product">
                    <div class="panel-body">
                        <div class="thumbnail">
                            <img src="${p.imageLink}" alt="${p.name}">
                            <div class="desc">
                                <div class="cell">
                                    <p>
                                        <span class="title"><fmt:message key="details"/></span> ${p.description}
                                    </p>
                                </div>
                            </div>
                        </div>
                        <h4 class="name">${p.name}</h4>
                        <div class="code"><fmt:message key="code" />: ${p.id}</div>
                        <div class="price">$ ${p.price}</div>
                        <a class="btn btn-primary pull-right buy-btn" data-id-product="${p.id}" ><fmt:message key="buy" /></a>
                        <div class="list-group">
                            <span class="list-group-item"> <small><fmt:message key="category" />:</small> <span class="category">${p.category}</span></span>
                            <span class="list-group-item"> <small><fmt:message key="producer" />:</small> <span class="producer">${p.producer}</span></span>
                        </div>
                    </div>
                </div>
        </div>
    </c:forEach>
