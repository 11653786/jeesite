<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理管理</title>
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
		<li class="active"><a href="${ctx}/orders/orders/">订单管理列表</a></li>
		<%--<shiro:hasPermission name="orders:orders:edit"><li><a href="${ctx}/orders/orders/form">订单管理添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="orders" action="${ctx}/orders/orders/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>订单号：</label>
				<form:input path="orderNo" htmlEscape="false" maxlength="40" class="input-medium"/>
			</li>
			<li><label>订单状态：</label>
				<form:select path="orderStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>微信流水：</label>
				<form:input path="wechatTradeNo" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>支付宝流水：</label>
				<form:input path="alipayTradeNo" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>支付时间：</label>
				<input name="beginPaymentTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${orders.beginPaymentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endPaymentTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${orders.endPaymentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>微信标志：</label>
				<form:input path="openid" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>alipay标志：</label>
				<form:input path="alipayid" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="phone" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>柜子名称：</label>
				<form:input path="cabinetName" htmlEscape="false" maxlength="40" class="input-medium"/>
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
				<th>实际支付金额</th>
				<th>订单状态</th>
				<th>微信流水</th>
				<th>支付宝流水</th>
				<th>创建时间</th>
				<th>支付时间</th>
				<th>微信标志</th>
				<th>alipay标志</th>
				<th>手机号</th>
				<th>柜子名称</th>
				<shiro:hasPermission name="orders:orders:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orders">
			<tr>
				<td><a href="${ctx}/orders/orders/form?id=${orders.id}">
					${orders.orderNo}
				</a></td>
				<td>
					${orders.payMoney/100}元
				</td>
				<td>
					${orders.actualPayMoney/100}元
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
					<fmt:formatDate value="${orders.paymentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${orders.openid}
				</td>
				<td>
					${orders.alipayid}
				</td>
				<td>
					${orders.phone}
				</td>
				<td>
					${orders.cabinetName}
				</td>
				<shiro:hasPermission name="orders:orders:edit"><td>
    				<a href="${ctx}/orders/orders/form?id=${orders.id}">查看</a>
					<a href="${ctx}/orders/orders/delete?id=${orders.id}" onclick="return confirmx('确认要删除该订单管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>