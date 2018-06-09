<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>菜品分类管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $(".nochangeSelect").attr("disabled", "disabled");
            jQuery.validator.methods.checkCategoryName = function (value, element) {
                var categoryName = $("#name").val();
                var storeId = $("#storeId").val();
                if (categoryName == "" || storeId == "") {
                    return true;
                }

                // 查看当前分类名称是否已存在
                var id = $("#id").val();
                var isPass = false;
                // ajax后台校验
                var url = "${ctx}/wx/foodCategory/checkCategoryName?categoryName="
                    + encodeURI(encodeURI(categoryName)) + "&id=" + encodeURI(encodeURI(id))
                    + "&storeId=" + encodeURI(encodeURI(storeId));
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
                    error: function () {
                        isPass = false;
                    }
                });
                return isPass;
            };

            $("#inputForm").validate({
                rules: {
                    name: {
                        required: true,
                        checkCategoryName: "input[name='name']"
                    }
                },
                messages: {
                    name: {
                        required: "必填信息",
                        checkCategoryName: "该店铺的该菜品分类已存在，请勿重复添加"
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
                        error.appendTo(element.parent());
                    } else if (element.is(".showSort")) {
                        error.insertAfter(element);
                    } else if (element.is(".maxNumber")) {
                        error.insertAfter(element);
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
    <li><a href="${ctx}/wx/foodCategory/">菜品分类列表</a></li>
    <li class="active">
        <a href="${ctx}/wx/foodCategory/form?id=${foodCategory.id}">
            <shiro:hasPermission
                    name="wx:foodCategory:edit">${not empty foodCategory.id?'菜品分类修改':'菜品分类添加'}</shiro:hasPermission>
            <shiro:lacksPermission name="wx:foodCategory:edit">菜品分类查看</shiro:lacksPermission>
        </a>
    </li>
</ul>
<form:form id="inputForm" modelAttribute="foodCategory" action="${ctx}/wx/foodCategory/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <c:choose>
        <c:when test="${foodCategory.isShopowner}"><%-- 店长 --%>
            <input type="hidden" id="storeId" name="storeId" value="${foodCategory.storeId}">
        </c:when>
        <c:otherwise><%-- 超级管理员 --%>
            <div class="control-group">
                <label class="control-label">店铺：</label>
                <div class="controls">
                    <c:choose>
                        <c:when test="${empty foodCategory.id}">
                            <form:select path="storeId" class="input-large required">
                                <form:option value="">--请选择店铺--</form:option>
                                <c:forEach items="${storeList}" var="store">
                                    <form:option value="${store.id}">${store.name}</form:option>
                                </c:forEach>
                            </form:select>
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" id="storeId" name="storeId" value="${foodCategory.storeId}">
                            <form:select path="storeId" class="input-large required nochangeSelect">
                                <form:option value="">--请选择店铺--</form:option>
                                <c:forEach items="${storeList}" var="store">
                                    <form:option value="${store.id}">${store.name}</form:option>
                                </c:forEach>
                            </form:select>
                        </c:otherwise>
                    </c:choose>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
    <div class="control-group">
        <label class="control-label">分类名称：</label>
        <div class="controls">
            <form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">指定排序：</label>
        <div class="controls">
            <form:input path="sort" htmlEscape="false" maxlength="11" class="input-medium number" placehodler="默认为0"/>
            <span class="help-inline"> 前台展示分类根据从小到大排序，如果值相同，则按照更新时间倒排</span>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="wx:foodCategory:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
        </shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>
