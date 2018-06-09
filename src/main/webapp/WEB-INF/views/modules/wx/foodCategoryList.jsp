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
            <li>
                <a href="${ctx}/wx/foodCategory/form?id=${foodCategory.id}">
                    <shiro:hasPermission name="wx:foodCategory:edit">${not empty foodCategory.id?'菜品分类修改':'菜品分类添加'}</shiro:hasPermission>
                    <shiro:lacksPermission name="wx:foodCategory:edit">菜品分类查看</shiro:lacksPermission>
                </a>
            </li>
        </ul>
        <form:form id="searchForm" modelAttribute="foodCategory" action="${ctx}/wx/foodCategory/" method="post" class="breadcrumb form-search">
            <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
            <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
            <ul class="ul-form">
                <li>
                    <label>分类名称：</label>
                    <form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
                </li>
                <c:choose>
                    <c:when test="${foodCategory.isShopowner}"><%-- 店长 --%>
                        <input id="storeId" name="storeId" type="hidden" value="${foodCategory.storeId}"/>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <label>所在店铺：</label>
                            <form:select path="storeId" class="input-medium">
                                <form:option value="">--请选择--</form:option>
                                <c:forEach items="${storeMap}" var="store">
                                    <form:option value="${store.key}">${store.value}</form:option>
                                </c:forEach>
                            </form:select>
                        </li>
                    </c:otherwise><%-- 管理员 --%>
                </c:choose>
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
                    <th>分类名称</th>
                    <c:if test="${!foodCategory.isShopowner}"><%-- 店长 --%>
                        <th>所在店铺</th>
                    </c:if>
                    <th>排序</th>
                    <th>创建时间</th>
                    <th>更新时间</th>
                    <shiro:hasPermission name="wx:foodCategory:edit">
                        <th>操作</th>
                    </shiro:hasPermission>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.list}" var="food">
                    <tr>
                        <td>${food.name}</td>
                        <c:if test="${!foodCategory.isShopowner}"><%-- 店长 --%>
                            <td>${storeMap.get(food.storeId)}</td>
                        </c:if>
                        <td>${food.sort}</td>
                        <td><fmt:formatDate value="${food.createAt}" pattern="yyyy-MM-mm HH:mm:ss"/></td>
                        <td><fmt:formatDate value="${food.updateAt}" pattern="yyyy-MM-mm HH:mm:ss"/></td>
                        <shiro:hasPermission name="wx:foodCategory:edit">
                            <td>
                                <a href="${ctx}/wx/foodCategory/form?id=${food.id}">修改</a>
                                <a href="${ctx}/wx/foodCategory/delete?id=${food.id}" onclick="return confirmx('确认要删除该菜品分类吗？', this.href)">删除</a>
                            </td>
                        </shiro:hasPermission>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div class="pagination">${page}</div>
    </body>
</html>