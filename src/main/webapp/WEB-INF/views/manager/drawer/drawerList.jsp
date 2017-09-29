<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>快餐柜管理管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

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
    <li class="active"><a href="${ctx}/drawer/drawer/">抽屉管理列表</a></li>
    <shiro:hasPermission name="drawer:drawer:edit">
        <li><a href="${ctx}/drawer/drawer/form">抽屉管理添加</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="drawer" action="${ctx}/drawer/drawer/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>柜子编号：</label>
            <form:select path="cabinetNo" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${cabinetList}" itemLabel="cabinetNos" itemValue="cabinetNos"
                              htmlEscape="false"/>
            </form:select>
        <li><label>抽屉状态：</label>
            <form:select path="drawerStatus" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('cabinet_status')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>放餐状态：</label>
            <form:select path="foodStatus" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('food_status')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>抽屉编号：</label>
            <form:input path="drawerNo" htmlEscape="false" maxlength="50" class="input-medium"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>所在省市区</th>
        <th>抽屉编号</th>
        <th>柜子编号</th>
        <th>抽屉状态</th>
        <th>放餐状态</th>
        <th>放餐商品名称</th>
        <th>放餐时间</th>
        <th>过期时间</th>
        <shiro:hasPermission name="drawer:drawer:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="drawer">
        <tr>
            <td>${drawer.cabinetProvinceName},${drawer.cabinetCityName},${drawer.cabinetAreaName}</td>
            <td>
                    ${drawer.drawerNo}
            </td>
            <td>
                    ${drawer.cabinetNo}
            </td>
            <td>
                    ${fns:getDictLabel(drawer.drawerStatus, 'drawer_status', '')}
            </td>
            <td>
                    ${fns:getDictLabel(drawer.foodStatus,'food_status','')}
            </td>
            <td>
                    ${drawer.productName}
            </td>
            <td>
                <fmt:formatDate value="${drawer.inTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                <fmt:formatDate value="${drawer.inOutTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <shiro:hasPermission name="drawer:drawer:edit">
                <td>
                    <a href="${ctx}/drawer/drawer/form?id=${drawer.id}">修改</a>
                    <a href="${ctx}/drawer/drawer/delete?id=${drawer.id}"
                       onclick="return confirmx('确认要删除该快餐柜管理吗？', this.href)">删除</a>
                    <shiro:hasPermission name="cabinetproductrelaction:cabinetProductRelaction:edit">
                        <a href="${ctx}/cabinetproductrelaction/cabinetProductRelaction/form?id=${drawer.id}">配置商品</a>
                    </shiro:hasPermission>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>