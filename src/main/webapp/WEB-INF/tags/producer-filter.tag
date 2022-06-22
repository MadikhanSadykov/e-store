<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="producers" required="true" type="java.util.Collection"%>
<%@ attribute name="searchForm" required="true" type="com.madikhan.estore.model.form.SearchForm"%>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />

<div class="panel-heading"><fmt:message key="producer.filters" /> </div>
<div class="panel-body categories">
    <label><input type="checkbox" id="allProducers"> <fmt:message key="all"/></label>
    <c:forEach var="producer" items="${producers }">
        <div class="form-group">
            <div class="checkbox">
                <label><input type="checkbox" name="producer" value="${producer.id }"
                              class="search-option"
                    ${searchForm.producers.contains(producer.id) ? 'checked' : ''} >
                        ${producer.name } (${producer.productCount })
                </label>
            </div>
        </div>
    </c:forEach>
</div>