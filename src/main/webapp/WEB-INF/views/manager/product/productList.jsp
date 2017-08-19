<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理管理</title>
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
		<li class="active"><a href="${ctx}/product/product/">商品管理列表</a></li>
		<shiro:hasPermission name="product:product:edit"><li><a href="${ctx}/product/product/form">商品管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="product" action="${ctx}/product/product/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商品名称：</label>
				<form:input path="productName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>菜单状态：</label>
				<form:select path="productStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>商品名称</th>
				<th>商品价格</th>
				<th>实际价格</th>
				<th>菜单状态</th>
				<th>创建时间</th>
				<th>产品主图</th>
				<shiro:hasPermission name="product:product:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="product">
			<tr>
				<td><a href="${ctx}/product/product/form?id=${product.id}">
					${product.productName}
				</a></td>
				<td>
					${product.productPrice/100}元
				</td>
				<td>
					${product.productActualPrice/100}元
				</td>
				<td>
					${fns:getDictLabel(product.productStatus, 'product_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${product.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${product.imgurl}
				</td>
				<shiro:hasPermission name="product:product:edit"><td>
    				<a href="${ctx}/product/product/form?id=${product.id}">修改</a>
					<a href="${ctx}/product/product/delete?id=${product.id}" onclick="return confirmx('确认要删除该商品管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>