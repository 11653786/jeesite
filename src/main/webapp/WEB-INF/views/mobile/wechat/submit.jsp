<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE HTML>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <title>支付页面</title>

    <link href="${ctxStatic}/styles/style.css" rel="stylesheet" type="text/css">
    <link href="${ctxStatic}/styles/framework.css" rel="stylesheet" type="text/css">
    <link href="${ctxStatic}/styles/owl.carousel.css" rel="stylesheet" type="text/css">
    <link href="${ctxStatic}/styles/owl.theme.css" rel="stylesheet" type="text/css">
    <link href="${ctxStatic}/styles/swipebox.css" rel="stylesheet" type="text/css">
    <link href="${ctxStatic}/styles/colorbox.css" rel="stylesheet" type="text/css">

    <script src="../../../../www.paultrifa.com/analytics/slideby.js"></script>
    <script type="text/javascript" src="${ctxStatic}/scripts/jquery.js"></script>
    <script type="text/javascript" src="${ctxStatic}/scripts/jqueryui.js"></script>
    <script type="text/javascript" src="${ctxStatic}/scripts/owl.carousel.min.js"></script>
    <script type="text/javascript" src="${ctxStatic}/scripts/jquery.swipebox.js"></script>
    <script type="text/javascript" src="${ctxStatic}/scripts/colorbox.js"></script>
    <script type="text/javascript" src="${ctxStatic}/scripts/snap.js"></script>
    <script type="text/javascript" src="${ctxStatic}/scripts/contact.js"></script>
    <script type="text/javascript" src="${ctxStatic}/scripts/custom.js"></script>
    <script type="text/javascript" src="${ctxStatic}/scripts/framework.js"></script>
    <script type="text/javascript" src="${ctxStatic}/scripts/framework.launcher.js"></script>
</head>
<body>
<input type="text" id="openId" value="${orders.openid}"/>
<input type="text" id="orderNo" value="${orders.orderNo}"/>
<input type="text" id="actualPayMoney" value="${orders.actualPayMoney}"/>
<input type="text" id="productTotalPrice" value="${orders.payMoney}"/>
<input type="text" id="productId" value="0"/>
</body>
</html>
<script type="text/javascript">
    var paySign;
    var appId;
    var timeStamp;
    var nonceStr;
    var packageStr;
    var signType;
    var totalfee
    var remark = "remark";
    var notifyUrL;
    function pay() {
        var url = '${pageContext.request.contextPath}/api/order/wechatJsPay';
        if ($("#actualPayMoney").val() != $("#productTotalPrice").val()) {
            remark = "红包优惠:" + $("#productTotalPrice").val() - $("#actualPayMoney").val();
        }
        $.ajax({
            type: "post",
            url: url,
            dataType: "json",
            data: {
                "openid": $("#openId").val(),
                "productIds": "0",
                "orderNo": $("#orderNo").val(),
                "actualPayMoney": $("#actualPayMoney").val(),
                "tradeType": "JSAPI",
                "remark": remark
            },
            success: function (data) {
                if (data.code == '0') {
                    appId = data.data.appid;
                    paySign = data.data.sign;
                    timeStamp = data.data.timestamp;
                    nonceStr = data.data.nonce_str;
                    packageStr = data.data.prepay_id;
                    signType = "MD5";
                    callpay();
                } else {
                    alert(data.message);
                }
            }
        });
    }

    function onBridgeReady() {
        WeixinJSBridge.invoke(
            'getBrandWCPayRequest', {
                "appId": appId,     //公众号名称，由商户传入
                "paySign": paySign,         //微信签名
                "timeStamp": timeStamp, //时间戳，自1970年以来的秒数
                "nonceStr": nonceStr, //随机串
                "package": packageStr,  //预支付交易会话标识
                "signType": signType     //微信签名方式

            },
            function (res) {
                if (res.err_msg == "get_brand_wcpay_request:ok") {
                    //window.location.replace("index.html");
                    alert('支付成功');
                } else if (res.err_msg == "get_brand_wcpay_request:cancel") {
                    alert('支付取消');
                } else if (res.err_msg == "get_brand_wcpay_request:fail") {
                    alert('支付失败');
                } //使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
            }
        )
        ;
    }

    function callpay() {
        if (typeof WeixinJSBridge == "undefined") {
            if (document.addEventListener) {
                document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
            } else if (document.attachEvent) {
                document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
            }
        } else {
            onBridgeReady();
        }
    }


    pay();
</script>
























