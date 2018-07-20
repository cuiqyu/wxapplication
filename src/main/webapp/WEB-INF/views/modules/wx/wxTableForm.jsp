<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>桌号管理管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/wx/wxTable/">桌号管理列表</a></li>
    <li class="active"><a href="${ctx}/wx/wxTable/form?id=${wxTable.id}">桌号管理<shiro:hasPermission
            name="wx:wxTable:edit">${not empty wxTable.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="wx:wxTable:edit">查看</shiro:lacksPermission></a></li>
</ul>
<form:form id="inputForm" modelAttribute="wxTable" action="${ctx}/wx/wxTable/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">桌号：</label>
        <div class="controls">
            <form:input path="tableId" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">桌名：</label>
        <div class="controls">
            <form:input path="tableName" htmlEscape="false" maxlength="20" class="input-xlarge "/>
        </div>
    </div>
    <c:choose>
        <c:when test="${!wxTable.isShopowner}">
            <div class="control-group">
                <label class="control-label">选择店铺：</label>
                <div class="controls">
                    <form:select path="storeId" class="input-medium required">
                        <form:option value="">--请选择--</form:option>
                        <c:forEach items="${storeMap}" var="store">
                            <form:option value="${store.key}">${store.value}</form:option>
                        </c:forEach>
                    </form:select>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <form:hidden path="storeId"/>
        </c:otherwise>
    </c:choose>
    <div class="form-actions">
        <shiro:hasPermission name="wx:wxTable:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>