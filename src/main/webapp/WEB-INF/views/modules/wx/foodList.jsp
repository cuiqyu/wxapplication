<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>菜品管理</title>
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
        <li class="active"><a href="${ctx}/wx/food/">菜品列表</a></li>
        <shiro:hasPermission name="wx:food:edit"><li><a href="${ctx}/wx/food/form">菜品添加</a></li></shiro:hasPermission>
    </ul>
    <form:form id="searchForm" modelAttribute="food" action="${ctx}/wx/food/" method="post" class="breadcrumb form-search">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <ul class="ul-form">
            <li><label>name：</label>
                <form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
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
                <th>菜品id</th>
                <th>菜品分类</th>
                <th>菜品名称</th>
                <th>菜品图片</th>
                <th>菜品价格</th>
                <th>是否商家推荐</th>
                <th>是否上架</th>
                <shiro:hasPermission name="wx:food:edit"><th>操作</th></shiro:hasPermission>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.list}" var="food">
            <tr>
                <td><a href="${ctx}/wx/food/form?id=${food.id}">${food.id}</a></td>
                <td>${food.categoryName}</td>
                <td>${food.name}</td>
                <td><img src="${food.picture}"></td>
                <td>${food.price}</td>
                <td>${food.recommend ? '是' : '否'}</td>
                <td>${food.state ? '上架中' : '已下架'}</td>
                <shiro:hasPermission name="wx:food:edit"><td>
                    <a href="${ctx}/wx/food/form?id=${food.id}">修改</a>

                </td></shiro:hasPermission>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagination">${page}</div>
</body>
</html>