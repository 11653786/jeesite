<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>用户红包关系管理管理</title>
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
    <li class="active"><a href="${ctx}/userredpacketrelaction/userRedpacketRelaction/">用户红包关系管理列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="userRedpacketRelaction"
           action="${ctx}/userredpacketrelaction/userRedpacketRelaction/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>优惠卷名称：</label>
            <form:input path="redpacketName" htmlEscape="false" maxlength="255" class="input-medium"/>
        </li>
        <li><label>用户名称：</label>
            <form:input path="userName" htmlEscape="false" maxlength="255" class="input-medium"/>
        </li>
        <li><label>领取时间：</label>
            <input name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${userRedpacketRelaction.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
            <input name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${userRedpacketRelaction.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </li>
        <li><label>红包类型：</label>
            <form:select path="redpacketType" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('redpacket_type')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>优惠卷名称</th>
        <th>用户名称</th>
        <th>优惠金额</th>
        <th>领取时间</th>
        <th>红包类型</th>
        <th>折扣比例</th>
        <th>是否使用</th>
        <shiro:hasPermission name="userredpacketrelaction:userRedpacketRelaction:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="userRedpacketRelaction">
        <tr>
            <td>
                    ${userRedpacketRelaction.redpacketName}
            </td>
            <td>
                    ${userRedpacketRelaction.userName}
            </td>
            <td>
                <c:if test="${userRedpacketRelaction.redpacketPrice!=null}">
                    ${userRedpacketRelaction.redpacketPrice/100}元
                </c:if>
            </td>
            <td>
                <fmt:formatDate value="${userRedpacketRelaction.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                    ${fns:getDictLabel(userRedpacketRelaction.redpacketType, 'redpacket_type', '')}
            </td>
            <td>
                    ${userRedpacketRelaction.discountRatio}
            </td>
            <td>
                    ${fns:getDictLabel(userRedpacketRelaction.inUse, 'in_use', '')}
            </td>
            <shiro:hasPermission name="userredpacketrelaction:userRedpacketRelaction:edit">
                <td>
                    <a href="${ctx}/userredpacketrelaction/userRedpacketRelaction/form?id=${userRedpacketRelaction.id}">修改</a>
                    <a href="${ctx}/userredpacketrelaction/userRedpacketRelaction/delete?id=${userRedpacketRelaction.id}"
                       onclick="return confirmx('确认要删除该用户红包吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>