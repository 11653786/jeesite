<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>yt管理</title>
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
		<li class="active"><a href="${ctx}/manager/orders/orders/">yt列表</a></li>
		<shiro:hasPermission name="manager:orders:orders:edit"><li><a href="${ctx}/manager/orders/orders/form">yt添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="orders" action="${ctx}/manager/orders/orders/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>订单号：</label>
				<form:input path="orderNo" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>订单状态,0,创建,1支付完成等待回调,2支付失败,3支付成功：</label>
				<form:select path="orderStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>微信订单号：</label>
				<form:input path="wechatTradeNo" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>支付宝订单号：</label>
				<form:input path="alipayTradeNo" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="beginCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${orders.beginCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${orders.endCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>支付时间：</label>
				<input name="beginPaymentTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${orders.beginPaymentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endPaymentTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${orders.endPaymentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
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
				<th>订单金额</th>
				<th>订单实际支付金额</th>
				<th>订单状态,0,创建,1支付完成等待回调,2支付失败,3支付成功</th>
				<th>微信订单号</th>
				<th>支付宝订单号</th>
				<th>创建时间</th>
				<th>update_time</th>
				<th>备注</th>
				<th>支付时间</th>
				<shiro:hasPermission name="manager:orders:orders:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orders">
			<tr>
				<td><a href="${ctx}/manager/orders/orders/form?id=${orders.id}">
					${orders.orderNo}
				</a></td>
				<td>
					${orders.payMoney}
				</td>
				<td>
					${orders.actualPayMoney}
				</td>
				<td>
					${fns:getDictLabel(orders.orderStatus, 'order_status', '')}
				</td>
				<td>
					${orders.wechatTradeNo}
				</td>
				<td>
					${orders.alipayTradeNo}
				</td>
				<td>
					<fmt:formatDate value="${orders.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${orders.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${orders.remark}
				</td>
				<td>
					<fmt:formatDate value="${orders.paymentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="manager:orders:orders:edit"><td>
    				<a href="${ctx}/manager/orders/orders/form?id=${orders.id}">修改</a>
					<a href="${ctx}/manager/orders/orders/delete?id=${orders.id}" onclick="return confirmx('确认要删除该yt吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>