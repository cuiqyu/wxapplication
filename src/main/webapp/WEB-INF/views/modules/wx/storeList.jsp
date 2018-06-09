<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>店铺管理管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(function() {
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
    <li class="active"><a href="${ctx}/wx/store/">店铺管理列表</a></li>
    <li>
        <a href="${ctx}/wx/store/form?id=${store.id}">
            <shiro:hasPermission name="wx:store:edit">${not empty store.id?'店铺管理修改':'店铺管理添加'}</shiro:hasPermission>
            <shiro:lacksPermission name="wx:store:edit">店铺管理查看</shiro:lacksPermission>
        </a>
    </li>
</ul>
<form:form id="searchForm" modelAttribute="store" action="${ctx}/wx/store/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>店铺名称：</label>
            <form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li><label>店长：</label>
            <form:select path="userId" class="input-large">
                <form:option value="">--请选择--</form:option>
                <c:forEach items="${userMap}" var="user">
                    <form:option value="${user.key}">${user.value}</form:option>
                </c:forEach>
            </form:select>
        </li>
        <li class="btns">
            <div class="btn-group">
                <button id="btnSubmit" type="submit" class="btn btn-primary" onclick="return page();">查询</button>
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
        <th>店铺名称</th>
        <th>店长</th>
        <shiro:hasPermission name="wx:store:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="store">
        <tr>
            <td><a href="${ctx}/wx/store/form?id=${store.id}">${store.name}</a></td>
            <td>${userMap.get(store.userId)}</td>
            <shiro:hasPermission name="wx:store:edit">
                <td>
                    <a href="${ctx}/wx/store/form?id=${store.id}">修改</a>
                    <a href="${ctx}/wx/store/delete?id=${store.id}"
                       onclick="return confirmx('确认要删除该店铺管理吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>