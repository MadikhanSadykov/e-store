<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />

<div id="addProductPopup" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><fmt:message key="add.product.to.shopping.cart"/></h4>
            </div>
            <div class="modal-body row">
                <div class="col-xs-12 col-sm-6">
                    <div class="thumbnail">
                        <img class="product-image" src="data:image/gif;base64,R0lGODlhAQABAAD/ACwAAAAAAQABAAACADs=" alt="Product image">
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6">
                    <h4 class="name text-center"><fmt:message key="name" /></h4>
                    <div class="list-group hidden-xs adv-chars">
                        <span class="list-group-item"> <small><fmt:message key="category"/>:</small> <span class="category">?</span></span>
                        <span class="list-group-item"> <small><fmt:message key="producer" />:</small> <span class="producer">?</span></span>
                    </div>
                    <div class="list-group">
                        <span class="list-group-item"> <small><fmt:message key="price"/>:</small> <span class="price">0</span></span>
                        <span class="list-group-item"> <small><fmt:message key="count"/>:</small> <input type="number" class="productCount" value="1" min="1" max="10"></span>
                        <span class="list-group-item"> <small><fmt:message key="cost"/>:</small> <span class="cost">0</span></span>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button id="addToCart" type="button" class="btn btn-primary"><fmt:message key="add.to.cart"/></button>
                <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="close"/></button>
            </div>
        </div>
    </div>
</div>