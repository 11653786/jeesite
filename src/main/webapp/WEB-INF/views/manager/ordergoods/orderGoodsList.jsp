<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>子订单管理</title>
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
		<li class="active"><a href="${ctx}/ordergoods/orderGoods/">子订单列表</a></li>
		<shiro:hasPermission name="ordergoods:orderGoods:edit"><li><a href="${ctx}/ordergoods/orderGoods/form">子订单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="orderGoods" action="${ctx}/ordergoods/orderGoods/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>order_no：</label>
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
				<th>order_no</th>
				<th>商品id</th>
				<th>商品价格</th>
				<th>实际支付金额</th>
				<th>柜子</th>
				<th>几号门</th>
				<th>柜子所在地址</th>
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
					${orderGoods.productId}
				</td>
				<td>
					${orderGoods.productPrice}
				</td>
				<td>
					${orderGoods.productActualPrice}
				</td>
				<td>
					${orderGoods.cabinet}
				</td>
				<td>
					${orderGoods.door}
				</td>
				<td>
					${orderGoods.address}
				</td>
				<td>
					<fmt:formatDate value="${orderGoods.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${orderGoods.paymentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="ordergoods:orderGoods:edit"><td>
    				<a href="${ctx}/ordergoods/orderGoods/form?id=${orderGoods.id}">修改</a>
					<a href="${ctx}/ordergoods/orderGoods/delete?id=${orderGoods.id}" onclick="return confirmx('确认要删除该子订单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>