<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>柜子商品配置表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cabinetproductrelaction/cabinetProductRelaction/">柜子商品配置表列表</a></li>
		<%--<shiro:hasPermission name="cabinetproductrelaction:cabinetProductRelaction:edit"><li><a href="${ctx}/cabinetproductrelaction/cabinetProductRelaction/form">柜子商品配置表添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="cabinetProductRelaction" action="${ctx}/cabinetproductrelaction/cabinetProductRelaction/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>柜子编号：</label>
				<form:input path="cabinetNo" htmlEscape="false" maxlength="40" class="input-medium"/>
			</li>
			<li><label>柜子名称：</label>
				<form:input path="cabinetName" htmlEscape="false" maxlength="40" class="input-medium"/>
			</li>
			<li><label>商品名称：</label>
				<form:input path="productName" htmlEscape="false" maxlength="40" class="input-medium"/>
			</li>
			<li><label>启用状态：</label>
				<form:select path="cabinetProductStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('cabinet_product_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>柜子编号</th>
				<th>柜子名称</th>
				<th>商品名称</th>
				<th>启用状态</th>
				<th>创建时间</th>
				<shiro:hasPermission name="cabinetproductrelaction:cabinetProductRelaction:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cabinetProductRelaction">
			<tr>
				<td><a href="${ctx}/cabinetproductrelaction/cabinetProductRelaction/form?id=${cabinetProductRelaction.id}">
					${cabinetProductRelaction.cabinetNo}
				</a></td>
				<td>
					${cabinetProductRelaction.cabinetName}
				</td>
				<td>
					${cabinetProductRelaction.productName}
				</td>
				<td>
					${fns:getDictLabel(cabinetProductRelaction.cabinetProductStatus, 'cabinet_product_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${cabinetProductRelaction.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="cabinetproductrelaction:cabinetProductRelaction:edit"><td>
    				<a href="${ctx}/cabinetproductrelaction/cabinetProductRelaction/form1?id=${cabinetProductRelaction.id}">修改</a>
					<a href="${ctx}/cabinetproductrelaction/cabinetProductRelaction/delete?id=${cabinetProductRelaction.id}" onclick="return confirmx('确认要删除该柜子商品配置表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>