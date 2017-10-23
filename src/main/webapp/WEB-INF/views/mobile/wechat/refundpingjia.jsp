<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE HTML>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <title>
        <c:if test="${type==1}" var="pingjia">
            评价
        </c:if>

        <c:if test="${!pingjia}">
            问题反馈
        </c:if>
    </title>
    <link href="${ctxStatic}/styles/style.css" rel="stylesheet" type="text/css">
    <link href="${ctxStatic}/styles/framework.css" rel="stylesheet" type="text/css">
    <link href="${ctxStatic}/styles/owl.carousel.css" rel="stylesheet" type="text/css">
    <link href="${ctxStatic}/styles/owl.theme.css" rel="stylesheet" type="text/css">
    <link href="${ctxStatic}/styles/swipebox.css" rel="stylesheet" type="text/css">
    <link href="${ctxStatic}/styles/colorbox.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${ctxStatic}/scripts/jquery.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#submit").click(function () {
                //Ajax调用处理
                var orderNo =${orders.orderNo};
                var type =${type};
                var remark = $("#remark").val();
                if (remark == undefined || remark == null || remark == "") {
                    $("#tips").html("<b>内容不能为空</b>");
                    return false;
                }
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/api/wechat/refundPingjia",
                    data: {"orderNo": orderNo, "type": type, "remark": remark},
                    dataType: "text",
                    success: function (data) {
                        if(data=='success'){
                            $("#tips").html("<b>提交成功！</b>");
                        }
                    }
                });
            });
        });

    </script>
</head>
<body style="background:#ccc;">
<div style="margin-top:5px;margin-left:5px;" class="container no-bottom">
    <div class="section-title">
        <h4>订单号：${orders.orderNo}</h4>
        <c:if test="${type==1}" var="pingjia">
            评价
        </c:if>

        <c:if test="${!pingjia}">
            问题反馈
        </c:if>
        <textarea rows="3" cols="20" id="remark" name="remark"></textarea>
        <button type="button" id="submit">提交</button>
        <div id="tips"></div>
    </div>
</div>

</body>
</html>

























