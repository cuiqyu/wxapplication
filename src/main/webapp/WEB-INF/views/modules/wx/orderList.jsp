<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>订单管理</title>
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

            orderTimeTypeChange();
            $("#orderTimeType").on("change", function() {
                orderTimeTypeChange();
            });

            // 列表显示隐藏
            $('.showcol').toggle(function () {
                $(this).val("展开更多显示");
                $('.showul').hide();
            }, function() {
                $('.showul').show();
                $(this).val("隐藏更多显示");
            })

            // 显示隐藏列
            $(".toggle-vis").on("click", function() {
                if ($(this).is(':checked')) {
                    $(".show_" + $(this).attr("data-column")).show();
                } else {
                    $(".show_" + $(this).attr("data-column")).hide();
                }
            });
            $(".toggle-vis").each(function() {
                if (!$(this).is(':checked')) {
                    $(".show_" + $(this).attr("data-column")).hide();
                }
            });
        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        /**
         * 订单时间查询方式
         */
        function orderTimeTypeChange() {
            var orderTimeType = $("#orderTimeType").val();
            if (orderTimeType == '') {
                $("#monthInput").val("");
                $("#dayInput").val("");
                $("#monthInput").hide();
                $("#dayInput").hide();
            } else if (orderTimeType == 'month') {
                $("#dayInput").val("");
                $("#monthInput").show();
                $("#dayInput").hide();
            } else if (orderTimeType == 'day') {
                $("#monthInput").val("");
                $("#monthInput").hide();
                $("#dayInput").show();
            }
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/wx/order/list">订单列表信息</a></li>
</ul>
<form:form id="searchForm" modelAttribute="order" action="${ctx}/wx/order/list" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>订单号：</label>
            <form:input path="id" class="input-medium required"/>
        </li>
        <c:choose>
            <c:when test="${order.isShopowner}"><%-- 店长 --%>
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
        <li><label>订单状态：</label>
            <form:select path="state" class="input-medium required">
                <form:option value="">--请选择--</form:option>
                <form:option value="PAID">支付完成</form:option>
                <form:option value="UNPAID">等待支付</form:option>
            </form:select>
        </li>
        <li><label>订单时间：</label>
            <form:select path="orderTimeType">
                <form:option value="">请选择查询方式</form:option>
                <form:option value="month">按月查</form:option>
                <form:option value="day">按日查</form:option>
            </form:select>
            <input id="monthInput" name="createMonth" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${order.createMonth}" pattern="yyyy-MM"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:true});"/>
            <input id="dayInput" name="createDay" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${order.createMonth}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
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
<div> <%-- 显示更多与缩进 --%>
    <input class="btn btn-small btn-warning showcol" type="button" value="隐藏更多显示" style="margin-bottom: 6px; float:left;"/>
    <label class="checkbox-inline showul"><input type="checkbox" class="toggle-vis" data-column="1" checked> 订单号</label>
    <c:if test="${!order.isShopowner}">
        <label class="checkbox-inline showul"><input type="checkbox" class="toggle-vis" data-column="2" checked> 所在店铺</label>
    </c:if>
    <label class="checkbox-inline showul"><input type="checkbox" class="toggle-vis" data-column="3" checked> 订单状态</label>
    <label class="checkbox-inline showul"><input type="checkbox" class="toggle-vis" data-column="4" checked> 用户的wxOpenId</label>
    <label class="checkbox-inline showul"><input type="checkbox" class="toggle-vis" data-column="5" checked> 用户微信昵称</label>
    <label class="checkbox-inline showul"><input type="checkbox" class="toggle-vis" data-column="6" checked> 订单总金额（元）</label>
    <label class="checkbox-inline showul"><input type="checkbox" class="toggle-vis" data-column="7" checked> 订单月</label>
    <label class="checkbox-inline showul"><input type="checkbox" class="toggle-vis" data-column="8" checked> 订单日</label>
    <label class="checkbox-inline showul"><input type="checkbox" class="toggle-vis" data-column="9" checked> 订单时间</label>
    <label class="checkbox-inline showul"><input type="checkbox" class="toggle-vis" data-column="10" checked> 消费明细</label>
</div>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th class="show_1">订单号</th>
        <c:if test="${!order.isShopowner}">
            <th class="show_2">所在店铺</th>
        </c:if>
        <th class="show_3">订单状态</th>
        <th class="show_4">用户的wxOpenId</th>
        <th class="show_5">用户微信昵称</th>
        <th class="show_6">订单总金额（元）</th>
        <th class="show_7">订单月</th>
        <th class="show_8">订单日</th>
        <th class="show_9">订单时间</th>
        <th class="show_10">消费明细</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${orderList}" var="orderva">
        <tr>
            <td class="show_1">${orderva.id}</td>
            <c:if test="${!order.isShopowner}">
                <td class="show_2">${storeMap.get(orderva.storeId)}</td>
            </c:if>
            <td class="show_3">
                <c:if test="${orderva.state == 'PAID'}">支付完成</c:if>
                <c:if test="${orderva.state == 'UNPAID'}">等待完成</c:if>
            </td>
            <td class="show_4">${orderva.customerWxId}</td>
            <td class="show_5">${orderva.customerName}</td>
            <td class="show_6">${orderva.amount}</td>
            <td class="show_7"><fmt:formatDate value="${orderva.createAt}" pattern="yyyy-MM"/></td>
            <td class="show_8"><fmt:formatDate value="${orderva.createAt}" pattern="dd"/></td>
            <td class="show_9"><fmt:formatDate value="${orderva.createAt}" pattern="HH:mm:ss"/></td>
            <td class="show_10">
                <table>
                    <tr>
                        <td>菜品名称</td>
                        <td>所属分类</td>
                        <td>单价</td>
                        <td>数量</td>
                    </tr>
                    <c:forEach items="${orderva.foodDetailInfoList}" var="foodDetailInfo">
                        <tr>
                            <td>${foodDetailInfo.foodName}</td>
                            <td>${foodDetailInfo.foodCategoryName}</td>
                            <td>${foodDetailInfo.foodPrice}</td>
                            <td>${foodDetailInfo.foodCount}</td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>