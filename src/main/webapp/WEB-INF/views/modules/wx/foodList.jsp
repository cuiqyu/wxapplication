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

            // 监控店铺变化
            $("#storeId").on("change", function() {
                $("#categoryId").empty();
                $("#categoryId").append("<option value = \"\">--请选择--</option>");
                $(".categoryDiv .select2-chosen").html("--请选择--");

                var storeId = $("#storeId").val();
                if ("" != storeId) {
                    // 查询改店铺下的所有分类信息
                    var requestUrl = "${ctx}/wx/food/getCategoryList?storeId=" + encodeURI(encodeURI(storeId));
                    var data = $.ajax({url: requestUrl, dataType: 'json', async: false});
                    data = eval('(' + data.responseText + ')');
                    if (data != null && data != undefined) {
                        for (var i = 0; i < data.length; i++) {
                            $("#categoryId").append("<option value = \"" + data[i].id + "\">" + data[i].name + "</option>");
                        }
                    }
                }
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
        <li>
            <a href="${ctx}/wx/food/form?id=${food.id}">
                <shiro:hasPermission name="wx:food:edit">${not empty food.id?'菜品修改':'菜品添加'}</shiro:hasPermission>
                <shiro:lacksPermission name="wx:food:edit">菜品查看</shiro:lacksPermission>
            </a>
        </li>
    </ul>
    <form:form id="searchForm" modelAttribute="food" action="${ctx}/wx/food/" method="post" class="breadcrumb form-search">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <ul class="ul-form">
            <li><label>菜品名称：</label>
                <form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
            </li>
            <c:choose>
                <c:when test="${food.isShopowner}"><%-- 店长 --%>
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
            <li class="categoryDiv"><label>菜品分类：</label>
                <form:select path="categoryId" class="input-small required">
                    <form:option value="">--请选择--</form:option>
                    <c:forEach items="${categoryList}" var="category">
                        <form:option value="${category.id}">${category.name}</form:option>
                    </c:forEach>
                </form:select>
            </li>
            <li><label>是否推荐：</label>
                <form:select path="recommend" class="input-small required">
                    <form:option value="">全部</form:option>
                    <form:option value="1">是</form:option>
                    <form:option value="0">否</form:option>
                </form:select>
            </li>
            <li><label>上架状态：</label>
                <form:select path="state" class="input-small required">
                    <form:option value="">全部</form:option>
                    <form:option value="1">已上架</form:option>
                    <form:option value="0">已下架</form:option>
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
                <c:if test="${!food.isShopowner}">
                    <th>所在店铺</th>
                </c:if>
                <th>菜品分类</th>
                <th>菜品图片</th>
                <th>菜品价格</th>
                <th>是否推荐</th>
                <th>上架状态</th>
                <th>单位</th>
                <th>销量</th>
                <shiro:hasPermission name="wx:food:edit"><th>操作</th></shiro:hasPermission>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="foodva">
            <tr>
                <td>${foodva.name}</td>
                <c:if test="${!food.isShopowner}">
                    <td>${storeMap.get(foodva.storeId)}</td>
                </c:if>
                <td>${foodva.categoryName}</td>
                <td><img src="${foodva.picture}" style="max-width:120px; max-height: 100px"></td>
                <td>${foodva.price}</td>
                <td>${foodva.recommend ? '是' : '否'}</td>
                <td>${foodva.state ? '已上架' : '已下架'}</td>
                <td>${foodva.unit}</td>
                <td>${foodva.sale}</td>
                <shiro:hasPermission name="wx:food:edit">
                    <td>
                        <a href="${ctx}/wx/food/form?id=${foodva.id}">修改</a>
                        <c:choose>
                            <c:when test="${foodva.recommend}"><a href="${ctx}/wx/food/cancelRecommend?id=${foodva.id}" onclick="return confirmx('确认是否取消该商品推荐？', this.href)">取消推荐</a></c:when>
                            <c:otherwise><a href="${ctx}/wx/food/recommend?id=${foodva.id}" onclick="return confirmx('确认推荐该商品？', this.href)">推荐</a></c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${foodva.state}"><a href="${ctx}/wx/food/undercarriage?id=${foodva.id}" onclick="return confirmx('确认下架该商品，下架后前台将不展示该商品？', this.href)">下架</a></c:when>
                            <c:otherwise><a href="${ctx}/wx/food/grounding?id=${foodva.id}" onclick="return confirmx('确认上架该商品，上架后台该商品将会在前端展示？', this.href)">上架</a></c:otherwise>
                        </c:choose>
                    </td>
                </shiro:hasPermission>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagination">${page}</div>
</body>
</html>