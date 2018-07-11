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
<form:form id="searchForm" modelAttribute="order" action="${ctx}/wx/order/achievementList" method="post" class="breadcrumb form-search">
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <tr>
        <th rowspan="2" colspan="2">店铺名称</th>
        <th rowspan="2" width="50px;">店铺总营业额</th>
        <th colspan="32" style="text-align:center;">
            查询方式：
            <select name="orderTimeType" id="orderTimeType">
                <option value="year">按年查</option>
                <option value="month">按月查</option>
            </select>
            <input id="yearInput" name="createYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${order.createYear}" pattern="yyyy-MM"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:true});"/>
            <input id="monthInput" name="createMonth" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${order.createMonth}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
            <input type="button" class="btn btn-primary" value="查询" onclick=""/>
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
    <tr>
        <td rowspan="2" width="100px;">店铺B</td>
        <th class="green" width="50px;">总已支付RMB</th>
        <td class="green">10000000000</td>
        <td class="tdl green"></td>
        <td class="tdl green"></td>
        <td class="tdl green"></td>
        <td class="tdl green"></td>
        <td class="tdl green"></td>
        <td class="tdl green"></td>
        <td class="tdl green"></td>
        <td class="tdl green"></td>
        <td class="tdl green"></td>
        <td class="tdl green"></td>
        <td class="tdl green"></td>
        <td class="tdl green"></td>
        <td class="tdl day green"></td>
        <td class="tdl day green"></td>
        <td class="tdl day green"></td>
        <td class="tdl day green"></td>
        <td class="tdl day green"></td>
        <td class="tdl day green"></td>
        <td class="tdl day green"></td>
        <td class="tdl day green"></td>
        <td class="tdl day green"></td>
        <td class="tdl day green"></td>
        <td class="tdl day green"></td>
        <td class="tdl day green"></td>
        <td class="tdl day green"></td>
        <td class="tdl day green"></td>
        <td class="tdl day green"></td>
        <td class="tdl day green"></td>
        <td class="tdl day green"></td>
        <td class="tdl day green"></td>
        <td class="tdl day green"></td>
        <td class="tdl green"></td>
    </tr>
    <tr style="color: red;">
        <th>总待支付RMB</th>
        <td>100000000</td>
        <td class="tdl"></td>
        <td class="tdl"></td>
        <td class="tdl"></td>
        <td class="tdl"></td>
        <td class="tdl"></td>
        <td class="tdl"></td>
        <td class="tdl"></td>
        <td class="tdl"></td>
        <td class="tdl"></td>
        <td class="tdl"></td>
        <td class="tdl"></td>
        <td class="tdl"></td>
        <td class="day tdl"></td>
        <td class="day tdl"></td>
        <td class="day tdl"></td>
        <td class="day tdl"></td>
        <td class="day tdl"></td>
        <td class="day tdl"></td>
        <td class="day tdl"></td>
        <td class="day tdl"></td>
        <td class="day tdl"></td>
        <td class="day tdl"></td>
        <td class="day tdl"></td>
        <td class="day tdl"></td>
        <td class="day tdl"></td>
        <td class="day tdl"></td>
        <td class="day tdl"></td>
        <td class="day tdl"></td>
        <td class="day tdl"></td>
        <td class="day tdl"></td>
        <td class="day tdl"></td>
        <td class="tdl"></td>
    </tr>
</table>
</body>
</html>