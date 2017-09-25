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
    <li class="active"><a href="${ctx}/cabinet/cabinet/">快餐柜管理列表</a></li>
    <shiro:hasPermission name="cabinet:cabinet:edit">
        <li><a href="${ctx}/cabinet/cabinet/form">快餐柜管理添加</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="cabinet" action="${ctx}/cabinet/cabinet/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>柜子名称：</label>
            <form:input path="cabinetName" htmlEscape="false" maxlength="50" class="input-medium"/>
        </li>
        <li><label>省：</label>
            <sys:treeselect id="province" name="province" value="${cabinet.province}" labelName=""
                            labelValue="${cabinet.province1}"
                            title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true"
                            notAllowSelectParent="true"/>
        </li>
        <li><label>市：</label>
            <sys:treeselect id="city" name="city" value="${cabinet.city}" labelName="" labelValue="${cabinet.city1}"
                            title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true"
                            notAllowSelectParent="true"/>
        </li>
        <li><label>区：</label>
            <sys:treeselect id="area" name="area" value="${cabinet.area}" labelName="" labelValue="${cabinet.area1}"
                            title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true"
                            notAllowSelectParent="true"/>
        </li>
        <li><label>柜子状态：</label>
            <form:select path="cabinetStatus" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('cabinet_status')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>柜子编号：</label>
            <form:input path="cabinetNos" htmlEscape="false" maxlength="50" class="input-medium"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>柜子名称</th>
        <th>省</th>
        <th>市</th>
        <th>区</th>
        <th>详细地址</th>
        <th>通信状态</th>
        <th>创建时间</th>
        <th>柜子编号</th>
        <th>菜品统计</th>
        <th>库存情况</th>
        <shiro:hasPermission name="cabinet:cabinet:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="cabinet">
        <tr>
            <td><a href="${ctx}/cabinet/cabinet/form?id=${cabinet.id}">
                    ${cabinet.cabinetName}
            </a></td>
            <td>
                    ${cabinet.province1}
            </td>
            <td>
                    ${cabinet.city1}
            </td>
            <td>
                    ${cabinet.area1}
            </td>
            <td>
                    ${cabinet.address}
            </td>
            <td>
                    ${fns:getDictLabel(cabinet.cabinetStatus, 'cabinet_status', '')}
            </td>
            <td>
                <fmt:formatDate value="${cabinet.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                    ${cabinet.cabinetNos}
            </td>
            <td>
                <c:forEach items="${groupByCabinet}"  var="groupcabinet">
                    <c:if test="${groupcabinet.cabinetNos==cabinet.cabinetNos}">
                        ${groupcabinet.province}
                    </c:if>
                </c:forEach>
            </td>
            <td>
                未放餐数量:
                <c:forEach items="${food0}"  var="food00">
                    <c:if test="${food00.cabinetNos==cabinet.cabinetNos}">
                     ${food00.total}
                    </c:if>
                </c:forEach>
                ,放餐数量:
                <c:forEach items="${food1}"  var="food01">

                    <c:if test="${food01.cabinetNos==cabinet.cabinetNos}">
                        ${food01.total}
                    </c:if>
                </c:forEach>
                ,过期数量:
                <c:forEach items="${food2}"  var="food02">

                    <c:if test="${food02.cabinetNos==cabinet.cabinetNos}">
                        ${food02.total}
                    </c:if>
                </c:forEach>

            </td>
            <shiro:hasPermission name="cabinet:cabinet:edit">
            <td>
                <a href="${ctx}/cabinet/cabinet/form?id=${cabinet.id}">修改</a>
                <a href="${ctx}/cabinet/cabinet/delete?id=${cabinet.id}"
                   onclick="return confirmx('确认要删除该快餐柜管理吗？', this.href)">删除</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="cabinetproductrelaction:cabinetProductRelaction:edit">
                    <a href="${ctx}/cabinetproductrelaction/cabinetProductRelaction/form?id=${cabinet.id}">配置商品</a>
                </shiro:hasPermission>

            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>