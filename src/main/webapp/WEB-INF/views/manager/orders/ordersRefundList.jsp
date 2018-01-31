<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>订单管理管理</title>
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
    <li class="active"><a href="${ctx}/orders/orders/">订单管理列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="orders" action="${ctx}/orders/orders/refund" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>订单号：</label>
            <form:input path="orderNo" htmlEscape="false" maxlength="40" class="input-medium"/>
        </li>
        <li><label>退款状态：</label>
            <form:select path="refundStatus" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('refund_status')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>支付渠道：</label>
            <form:select path="paymentStatus" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('payment_status')}" itemLabel="label" itemValue="value"
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
        <th>订单号</th>
        <th>订单金额</th>
        <th>实际支付金额</th>
        <th>订单状态</th>
        <th>付款渠道</th>
        <th>退款状态</th>
        <th>微信流水</th>
        <th>支付宝流水</th>
        <th>创建时间</th>
        <th>支付时间</th>
        <th>手机号</th>
        <th>柜子编号</th>
        <th>用户反馈</th>
        <shiro:hasPermission name="orders:orders:refund">
            <th>操作</th>
        </shiro:hasPermission>
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
                    ${fns:getDictLabel(orders.paymentStatus, 'payment_status', '')}
            </td>
            <td>
                    ${fns:getDictLabel(orders.refundStatus, 'refund_status', '')}
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
                    ${orders.phone}
            </td>
            <td>
                    ${orders.cabinetNo}
            </td>
            <td>${orders.remark}</td>
            <shiro:hasPermission name="orders:orders:refund">
                <td>
                    <c:if test="${orders.refundStatus==0}">
                        <a href="${ctx}/orders/orders/queryorder?orderNo=${orders.orderNo}&queryType=1"
                           onclick="return confirmx('确认要退款吗？', this.href)">退款</a>
                    </c:if>
                    <c:if test="${orders.refundStatus==1}">
                        <a href="${ctx}/orders/orders/queryorder?orderNo=${orders.orderNo}&queryType=2">退款查看</a>
                    </c:if>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>