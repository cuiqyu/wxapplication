<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>桌号管理管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //清空按钮
            $("#btnClear").on("click", function() {
                $("li >input").val("");
                $("li >select").val("");
                $("select").each(function(index){
                    $(".select2-chosen").eq(index).html($(this).children("option:selected").text());
                });
            });
        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/wx/wxTable/">桌号管理列表</a></li>
    <shiro:hasPermission name="wx:wxTable:edit">
        <li><a href="${ctx}/wx/wxTable/form">桌号管理添加</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="wxTable" action="${ctx}/wx/wxTable/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>桌号：</label>
            <form:input path="tableId" class="input-medium required"/>
        </li>
        <li><label>桌名：</label>
            <form:input path="tableName" class="input-medium required"/>
        </li>
        <c:choose>
            <c:when test="${wxTable.isShopowner}"><%-- 店长 --%>
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
            <th>桌号</th>
            <th>桌名</th>
            <c:if test="${!wxTable.isShopowner}">
                <th>所在店铺</th>
            </c:if>
            <th>小程序码</th>
            <th>创建时间</th>
            <th>更新时间</th>
        </tr>
    </thead>
    <tbody
    <c:forEach items="${page.list}" var="wxTable">
        <tr>
            <td>${wxTable.tableId}</td>
            <td>${wxTable.tableName}</td>
            <c:if test="${!wxTable.isShopowner}">
                <td class="show_2">${storeMap.get(wxTable.storeId)}</td>
            </c:if>
            <td><img src="${wxTable.url}"/></td>
            <td><fmt:formatDate value="${wxTable.createAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td><fmt:formatDate value="${wxTable.updateAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>