<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>菜品评价管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            //清空按钮
            $("#btnClear").on("click", function() {
                $("li >input").val("");
                $("li >select").val("");
                $("select").each(function(index){
                    $(".select2-chosen").eq(index).html($(this).children("option:selected").text());
                });
            });
        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/wx/comment/list">菜品评价列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="foodComment" action="${ctx}/wx/comment/list" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <c:choose>
            <c:when test="${foodComment.isShopowner}"><%-- 店长 --%>
                <form:hidden path="storeId"/>
            </c:when>
            <c:otherwise><%-- 管理员 --%>
                <li><label>选择店铺：</label>
                    <form:select path="storeId" class="input-medium required">
                        <form:option value="">--请选择--</form:option>
                        <c:forEach items="${storeMap}" var="store">
                            <form:option value="${store.key}">${store.value}</form:option>
                        </c:forEach>
                    </form:select>
                </li>
            </c:otherwise>
        </c:choose>
        <li><label>星级：</label>
            <form:select path="star" class="input-medium required">
                <form:option value="">--请选择--</form:option>
                <form:option value="1">一星</form:option>
                <form:option value="2">二星</form:option>
                <form:option value="3">三星</form:option>
                <form:option value="4">四星</form:option>
                <form:option value="5">五星</form:option>
            </form:select>
        </li>
        <li class="btns">
            <div class="btn-group">
                <button id="btnSubmit" type="submit" class="btn btn-primary">查询</button>
                <button id="btnClear" type="button" class="btn btn-primary" style="background: #999999;">清空</button>
            </div>
        </li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>菜品名称</th>
        <c:if test="${!foodComment.isShopowner}">
            <th>所在店铺</th>
        </c:if>
        <th>评价内容</th>
        <th>评价星级</th>
        <th>用户的wxOpenId</th>
        <th>用户微信昵称</th>
        <th>评价时间</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${commentList}" var="commentva">
        <tr>
            <td>${commentva.foodName}</td>
            <c:if test="${!foodComment.isShopowner}">
                <td>${storeMap.get(commentva.storeId)}</td>
            </c:if>
            <td>${commentva.content}</td>
            <td>${commentva.star}</td>
            <td>${commentva.customerWxId}</td>
            <td>${commentva.customerName}</td>
            <td><fmt:formatDate value="${commentva.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>