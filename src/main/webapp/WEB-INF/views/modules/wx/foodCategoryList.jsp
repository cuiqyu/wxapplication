<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
    <head>
        <title>菜品分类管理</title>
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
            <li class="active"><a href="${ctx}/wx/foodCategory/">菜品分类列表</a></li>
            <shiro:hasPermission name="wx:foodCategory:edit">
                <li><a href="${ctx}/wx/foodCategory/form">菜品分类添加</a></li>
            </shiro:hasPermission>
        </ul>
        <form:form id="searchForm" modelAttribute="foodCategory" action="${ctx}/wx/foodCategory/" method="post" class="breadcrumb form-search">
            <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
            <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
            <ul class="ul-form">
                <li>
                    <label>分类名称：</label>
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
                    <th>分类名称</th>
                    <shiro:hasPermission name="wx:foodCategory:edit">
                        <th>操作</th>
                    </shiro:hasPermission>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.list}" var="foodCategory">
                    <tr>
                        <td>${foodCategory.name}</td>
                        <shiro:hasPermission name="wx:foodCategory:edit">
                            <td>
                                <a href="${ctx}/wx/foodCategory/form?id=${foodCategory.id}">修改</a>
                                <a href="${ctx}/wx/foodCategory/delete?id=${foodCategory.id}" onclick="return confirmx('确认要删除该菜品分类吗？', this.href)">删除</a>
                            </td>
                        </shiro:hasPermission>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div class="pagination">${page}</div>
    </body>
</html>