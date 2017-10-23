<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE HTML>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <title>我的订单</title>
    <link href="${ctxStatic}/styles/style.css" rel="stylesheet" type="text/css">
    <link href="${ctxStatic}/styles/framework.css" rel="stylesheet" type="text/css">
    <link href="${ctxStatic}/styles/owl.carousel.css" rel="stylesheet" type="text/css">
    <link href="${ctxStatic}/styles/owl.theme.css" rel="stylesheet" type="text/css">
    <link href="${ctxStatic}/styles/swipebox.css" rel="stylesheet" type="text/css">
    <link href="${ctxStatic}/styles/colorbox.css" rel="stylesheet" type="text/css">


</head>
<body>
<c:forEach items="${orders}" var="order">
    <div style="margin-top:5px;margin-left:5px;" class="container no-bottom">
        <div class="section-title">
            <h4>订单号:${order.orderNo}</h4>
            <em>订单密码：${order.putPassword}</em><br>
            <em>订单金额：${order.payMoney/100}元</em><br>
            <em>订单状态:
                <c:choose>
                    <c:when test="${order.orderStatus==1}">
                        已支付
                    </c:when>
                    <c:when test="${order.orderStatus==3}">
                        已取餐
                    </c:when>
                </c:choose></em><br>
            <c:if test="${order.remark!=null && order.remark!=''}">
                <c:if test="${order.refundStatus!=null}">
                    <em>反馈问题:
                        <c:choose>
                            <c:when test="${order.refundStatus==0}">
                                已受理
                            </c:when>
                            <c:when test="${order.refundStatus==1}">
                                受理成功
                            </c:when>
                            <c:when test="${order.refundStatus==2}">
                                受理失败
                            </c:when>
                        </c:choose>
                    </em>
                </c:if>
                <em>用户评论：${order.remark}</em><br>
            </c:if>


            <c:if test="${order.redpacketPrice!=null}">
                <em>红包优惠:${order.redpacketPrice/100}元</em><br>
            </c:if>
            <em>订单详情：${order.phone}</em><br>

            <em style="float:right;margin-right:5px;">
                <c:if test="${order.orderStatus==1 || order.orderStatus=3}">
                    <c:if test="${order.remark==null && order.remark==''}">
                        <a style="color:#000000;"
                           href="${pageContext.request.contextPath}/api/wechat/refundOrder?type=1&orderNo=${order.orderNo}">评价</a>
                        <a style="color:#000000;"
                           href="${pageContext.request.contextPath}/api/wechat/refundOrder?type=2&orderNo=${order.orderNo}">问题反馈</a>
                    </c:if>
                </c:if>
                实付金额：${order.actualPayMoney/100}元
            </em>
        </div>
    </div>
    <div class="decoration"></div>
</c:forEach>
</body>
</html>
























