<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单详情管理</title>
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
		<li class="active"><a href="${ctx}/ordergoods/orderGoods/">订单详情列表</a></li>
		<shiro:hasPermission name="ordergoods:orderGoods:edit"><li><a href="${ctx}/ordergoods/orderGoods/form">订单详情添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="orderGoods" action="${ctx}/ordergoods/orderGoods/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>订单号：</label>
				<form:input path="orderNo" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订单号</th>
				<th>商品名称</th>
				<th>商品价格</th>
				<th>商品数量</th>
				<th>区域名称</th>
				<th>柜子编号</th>
				<th>抽屉编号</th>
				<th>创建时间</th>
				<th>支付时间</th>
				<shiro:hasPermission name="ordergoods:orderGoods:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderGoods">
			<tr>
				<td><a href="${ctx}/ordergoods/orderGoods/form?id=${orderGoods.id}">
					${orderGoods.orderNo}
				</a></td>
				<td>
					${orderGoods.productName}
				</td>
				<td>
					${orderGoods.productActualPrice}
				</td>
				<td>
					${orderGoods.productNum}
				</td>
				<td>
					${orderGoods.areaName}
				</td>
				<td>
					${orderGoods.cabinetNo}
				</td>
				<td>
					${orderGoods.drawerNo}
				</td>
				<td>
					<fmt:formatDate value="${orderGoods.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${orderGoods.paymentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="ordergoods:orderGoods:edit"><td>
    				<a href="${ctx}/ordergoods/orderGoods/form?id=${orderGoods.id}">修改</a>
					<a href="${ctx}/ordergoods/orderGoods/delete?id=${orderGoods.id}" onclick="return confirmx('确认要删除该订单详情吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>