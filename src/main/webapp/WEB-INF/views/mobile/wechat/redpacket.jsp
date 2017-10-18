<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>会员福利</title>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/styles/shop/base.css"/>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/styles/shop/module.css"/>
</head>

<body style="background: #5c9ccc">

<div class="shopping">

    <div class="shop-group-item">
        <ul>
            <c:forEach items="${redpackgets}" var="red">
                <li style="border-bottom:1px greenyellow dashed;">
                    <div style="margin:5px 0px 10px 0px">
                        <div>
                            <h4>
                                <b>${red.redpacketName}</b>
                            </h4>
                            <div class="shop-brief">
                                优惠金额:${red.redpacketPrice/100}元<br>
                                过期时间:<fmt:formatDate value="${red.outTime}" pattern="yyyy-MM-dd"/>
                            </div>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
</body>
</html>
