<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>订单业绩统计管理</title>
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

            orderTimeType();
            $("#orderTimeType").on("change", function() {
                orderTimeType();
                $(".tdl").html("");
            });

        });

        function orderTimeType() {
            var type = $("#orderTimeType").val();
            if (type == 'year') {
                $(".day").hide();
                $(".tipCount").html("年");
                $(".tipTd").html("月");
                $("#monthInput").val("");
                $("#yearInput").show();
                $("#monthInput").hide();
            } else {
                $(".day").show();
                $(".tipCount").html("月");
                $(".tipTd").html("日");
                $("#yearInput").val("");
                $("#yearInput").hide();
                $("#monthInput").show();
            }
        }
    </script>
    <style type="text/css">
        .green {color: green}
    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/wx/order/achievementList">订单业绩统计</a></li>
    <li><a href="${ctx}/wx/order/list">订单列表信息</a></li>
</ul>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <tr>
        <th rowspan="2" colspan="2">店铺名称</th>
        <th rowspan="2" width="50px;">店铺总营业额</th>
        <th colspan="32" style="text-align:center;">
            <form:form id="searchForm" modelAttribute="order" action="${ctx}/wx/order/achievementList" method="post" class="breadcrumb form-search">
                查询方式：
                <form:select path="orderTimeType" id="orderTimeType">
                    <form:option value="year">按年查</form:option>
                    <form:option value="month">按月查</form:option>
                </form:select>
                <input id="yearInput" name="createYear" type="text" maxlength="20" class="input-medium Wdate"
                       value="<fmt:formatDate value="${order.createYear}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-01-01 00:00:00',isShowClear:true});"/>
                <input id="monthInput" name="createMonth" type="text" maxlength="20" class="input-medium Wdate"
                       value="<fmt:formatDate value="${order.createMonth}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-01 00:00:00',isShowClear:true});"/>
                <input type="submit" class="btn btn-primary" value="查询"/>
            </form:form>
        </th>
    </tr>
    <tr>
        <th>1<span class="tipTd"></span></th>
        <th>2<span class="tipTd"></span></th>
        <th>3<span class="tipTd"></span></th>
        <th>4<span class="tipTd"></span></th>
        <th>5<span class="tipTd"></span></th>
        <th>6<span class="tipTd"></span></th>
        <th>7<span class="tipTd"></span></th>
        <th>8<span class="tipTd"></span></th>
        <th>9<span class="tipTd"></span></th>
        <th>10<span class="tipTd"></span></th>
        <th>11<span class="tipTd"></span></th>
        <th>12<span class="tipTd"></span></th>
        <th class="day">13日</th>
        <th class="day">14日</th>
        <th class="day">15日</th>
        <th class="day">16日</th>
        <th class="day">17日</th>
        <th class="day">18日</th>
        <th class="day">19日</th>
        <th class="day">20日</th>
        <th class="day">21日</th>
        <th class="day">22日</th>
        <th class="day">23日</th>
        <th class="day">24日</th>
        <th class="day">25日</th>
        <th class="day">26日</th>
        <th class="day">27日</th>
        <th class="day">28日</th>
        <th class="day">29日</th>
        <th class="day">30日</th>
        <th class="day">31日</th>
        <th>按<span class="tipCount"></span>总计</th>
    </tr>
    <c:forEach items="${storeMap}" var="storeMapVar">
        <tr>
            <td rowspan="2" width="100px;">${storeMapVar.value}</td>
            <th class="green" width="50px;">总已支付RMB</th>
            <td class="green">
                <c:choose>
                    <c:when test="${not empty orderTotalAmountMap.get(storeMapVar.key) and not empty orderTotalAmountMap.get(storeMapVar.key).get(orderStateMap.get('paid'))}">
                        ${orderTotalAmountMap.get(storeMapVar.key).get(orderStateMap.get('paid'))}
                    </c:when>
                    <c:otherwise>0.00</c:otherwise>
                </c:choose>
            </td>
            <c:forEach items="${oneTo12}" var="oneTo12Var">
                <td class="tdl green">
                    ${orderTotalDetailAmountMap.get(storeMapVar.key).get(orderStateMap.get('paid')).get(oneTo12Var)}
                </td>
            </c:forEach>
            <c:forEach items="${ttTo31}" var="ttTo31Var">
                <td class="tdl day green">
                    ${orderTotalDetailAmountMap.get(storeMapVar.key).get(orderStateMap.get('paid')).get(ttTo31Var)}
                </td>
            </c:forEach>
            <td class="tdl green">
                ${orderTotalDetailAmountMap.get(storeMapVar.key).get(orderStateMap.get('paid')).get('32')}
            </td>
        </tr>
        <tr style="color: red;">
            <th>总待支付RMB</th>
            <td>
                <c:choose>
                    <c:when test="${not empty orderTotalAmountMap.get(storeMapVar.key) and not empty orderTotalAmountMap.get(storeMapVar.key).get(orderStateMap.get('unpaid'))}">
                        ${orderTotalAmountMap.get(storeMapVar.key).get(orderStateMap.get('unpaid'))}
                    </c:when>
                    <c:otherwise>0.00</c:otherwise>
                </c:choose>
            </td>
            <c:forEach items="${oneTo12}" var="oneTo12Var">
                <td class="tdl">
                    ${orderTotalDetailAmountMap.get(storeMapVar.key).get(orderStateMap.get('unpaid')).get(oneTo12Var)}
                </td>
            </c:forEach>
            <c:forEach items="${ttTo31}" var="ttTo31Var">
                <td class="tdl day">
                    ${orderTotalDetailAmountMap.get(storeMapVar.key).get(orderStateMap.get('unpaid')).get(ttTo31Var)}
                </td>
            </c:forEach>
            <td class="tdl">
                ${orderTotalDetailAmountMap.get(storeMapVar.key).get(orderStateMap.get('unpaid')).get('32')}
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>