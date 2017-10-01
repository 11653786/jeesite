<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>退款</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });
    </script>
</head>
<body>
<ul class="ul-form">
    <form id="searchForm" action="${ctx}/orders/orders/queryorder" method="post" class="breadcrumb form-search">


        <ul class="ul-form">
            <li><label>订单号：</label>
                <input value="${orderNo}" name="orderNo" htmlEscape="false" maxlength="40" class="input-medium"/>
            </li>
            <li><label>订单状态：</label>
                <select id="queryType" name="queryType">
                    <option value="0">查询订单</option>
                    <option value="1">退款</option>
                    <option value="2">退款查询</option>
                </select>
            </li>
            <li class="btns">
                <input type="submit" class="btn btn-primary" value="查询">
            </li>
            <li class="clearfix"></li>
        </ul>
    </form>
</ul>

<c:if test="${order!=null && order!=''}">
    code:${order.code},<br>
    message:${order.message},<br>
    <c:if test="${queryType==0}" var="isQueryOrder">
        <c:if test="${order.data!=null}">
            订单号：${order.data.orderNo},<br>
            订单状态：${fns:getDictLabel(order.data.orderStatus, 'order_status', '')}<br>
            付款状态：${fns:getDictLabel(order.data.paymentStatus, 'payment_status', '')}<br>
            退款状态：    ${fns:getDictLabel(order.data.refundStatus, 'refund_status', '')}<br>
            订单金额：${order.data.payMoney},<br>
            订单实际支付金额：${order.data.actualPayMoney},<br>
            柜子编号：${order.data.cabinetNo},<br>

        </c:if>
    </c:if>

    <c:if test="${!isQueryOrder}">
        data:${order.data}
    </c:if>

</c:if>

</body>
</html>