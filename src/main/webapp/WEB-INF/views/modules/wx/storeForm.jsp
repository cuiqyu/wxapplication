<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>店铺管理管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            jQuery.validator.methods.checkStoreName = function(value, element) {
                var storeName = $("#name").val();
                if (storeName == "") {
                    return true;
                }

                // 查看当前分类名称是否已存在
                var id = $("#id").val();
                var isPass = false;
                // ajax后台校验
                var url = "${ctx}/wx/store/checkStoreName?storeName=" + encodeURI(encodeURI(storeName)) + "&id=" + encodeURI(encodeURI(id));
                $.ajax({
                    url: url,
                    cache: false,
                    async: false,
                    dataType: 'JSON',
                    success: function (result) {
                        if (result.state == '00') {
                            isPass = true;
                        } else {
                            isPass = false;
                        }
                    },
                    error: function() {
                        isPass = false;
                    }
                });
                return isPass;
            };


            $("#inputForm").validate({
                rules: {
                    name: {
                        required: true,
                        checkStoreName: "input[name='name']"
                    }
                },
                messages:{
                    name: {
                        required: "必填信息",
                        checkStoreName: "该店铺名称已存在，请换个名称吧"
                    }
                },
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
    <li><a href="${ctx}/wx/store/">店铺管理列表</a></li>
    <li class="active">
        <a href="${ctx}/wx/store/form?id=${store.id}">
        <shiro:hasPermission name="wx:store:edit">${not empty store.id?'店铺管理修改':'店铺管理添加'}</shiro:hasPermission>
        <shiro:lacksPermission name="wx:store:edit">店铺管理查看</shiro:lacksPermission>
    </a>
    </li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="store" action="${ctx}/wx/store/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">店铺名称：</label>
        <div class="controls">
            <form:input path="name" htmlEscape="false" maxlength="64" class="input-large required" placeholder="给你的店铺取一个好听的名字"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">选择店长：</label>
        <div class="controls">
            <form:select path="userId" class="input-large required">
                <form:option value="">--请选择店长--</form:option>
                <c:forEach items="${userList}" var="user">
                    <form:option value="${user.id}">${user.name}</form:option>
                </c:forEach>
            </form:select>
            <c:choose>
                <c:when test="${empty userList}">
                    <span class="help-inline"><font color="red">* 未查询到可以分配的人员，请先添加相应的人员信息</font> </span>
                </c:when>
                <c:otherwise>
                    <span class="help-inline"><font color="red">*</font> 给店铺选择一个店长来管理该店铺</span>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="wx:store:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
        </shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>