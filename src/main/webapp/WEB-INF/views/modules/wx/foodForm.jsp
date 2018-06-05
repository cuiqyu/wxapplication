<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>菜品管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            jQuery(function(){
                //校验输入的是否是金额格式
                jQuery.validator.methods.compareMoneyNumber = function(value, element, param) {
                    if (value == null || value == '') {
                        return true;
                    }
                    if(!/^\d*(\.\d{1,2})?$/.test(value)) {
                        return false;
                    } else {
                        return true;
                    }
                };
            });

            $("#inputForm").validate({
                rules: {
                    name: {
                        required: true
                    },
                    price: {
                        compareMoneyNumber: "input[name='price']"
                    },
                },
                messages:{
                    name: {
                        required: "必填信息"
                    },
                    price: {
                        compareMoneyNumber: "格式有误，请输入金额格式（整数或带两位小数的整数）"
                    },
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
    <li><a href="${ctx}/wx/food/">菜品列表</a></li>
    <li class="active">
        <a href="${ctx}/wx/food/form?id=${food.id}">菜品
            <shiro:hasPermission name="wx:food:edit">${not empty food.id?'修改':'添加'}</shiro:hasPermission>
            <shiro:lacksPermission name="wx:food:edit">查看</shiro:lacksPermission>
        </a>
    </li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="food" action="${ctx}/wx/food/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">菜品分类：</label>
        <div class="controls">
            <form:select path="categoryId" class="input-medium required">
                <form:option value="">--请选择--</form:option>
                <c:forEach items="${categoryList}" var="category">
                    <form:option value="${category.id}">${category.name}</form:option>
                </c:forEach>
            </form:select>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">菜品名称：</label>
        <div class="controls">
            <form:input path="name" htmlEscape="false" maxlength="64" class="input-large required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">菜品图片：</label>
        <div class="controls">
            <form:hidden path="picture" htmlEscape="false" maxlength="2000" class="input-large"/>
            <sys:ckfinder input="picture" type="images" uploadPath="/foodPictureList" selectMultiple="false" maxWidth="100" maxHeight="100" readonly="${readOnly}"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">菜品价格：</label>
        <div class="controls">
            <form:input path="price" htmlEscape="false" placeholder="菜品价格" class="input-large required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">是否推荐：</label>
        <div class="controls">
            <form:radiobuttons path="recommend" items="${recommendedStateMap}" class="input-medium required"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">上架状态：</label>
        <div class="controls">
            <form:radiobuttons path="state" items="${shelfStateMap}" class="input-medium required"/>
            <span class="help-inline"> 如果是下架状态，该菜品将不在前台展示 </span>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="wx:food:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
        </shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>