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
                    <em style="color:#e34e47;">反馈问题:
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
                <em style="color:#e34e47;">用户评论：${order.remark}</em><br>
            </c:if>


            <c:if test="${order.redpacketPrice!=null}">
                <em>红包优惠:${order.redpacketPrice/100}元</em><br>
            </c:if>
            <em>订单详情：${order.phone}</em><br>
            <em> 实付金额：${order.actualPayMoney/100}元</em><br>
            <em style="margin-right:5px;">

                <c:if test="${order.orderStatus==1 || order.orderStatus==3}">
                    <c:if test="${order.remark==null || order.remark==''}">

                        <a style="float:left;"
                                href="${pageContext.request.contextPath}/api/wechat/refundOrder?type=2&orderNo=${order.orderNo}">申请退款</a>

                        <a style="float:right;"
                           href="${pageContext.request.contextPath}/api/wechat/refundOrder?type=1&orderNo=${order.orderNo}">评价快餐</a>

                    </c:if>
                </c:if>

            </em>
        </div>
    </div>
    <div class="decoration"></div>
</c:forEach>
</body>
</html>
<style type="text/css">
    a{      /* 统一设置所以样式 */
        font-family:Arial;
        color:#000000;
        font-size:12px;
        text-align:center;
        margin:3px;
    }
    a:link,a:visited{  /* 超链接正常状态、被访问过的样式 */
        color:#A62020;
        padding:4px 10px 4px 10px;
        background-color:#ecd8bd;
        text-decoration:none;

        border-top:1px solid #EEEEEE; /* 边框实现阴影效果 */
        border-left:1px solid #EEEEEE;
        border-bottom:1px solid #717171;
        border-right:1px solid #717171;
    }
    a:hover{       /* 鼠标指针经过时的超链接 */
        color:#821818;     /* 改变文字颜色 */
        padding:5px 8px 3px 12px;  /* 改变文字位置 */
        background-color:#e2c4c9;  /* 改变背景色 */
        border-top:1px solid #717171; /* 边框变换，实现“按下去”的效果 */
        border-left:1px solid #717171;
        border-bottom:1px solid #EEEEEE;
        border-right:1px solid #EEEEEE;
    }
</style>
























