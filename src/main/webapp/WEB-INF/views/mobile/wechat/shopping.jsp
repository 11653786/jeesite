<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <title>下单</title>
    <script type="text/javascript" src="${ctxStatic}/scripts/jquery.js"></script>
    <script type="text/javascript" src="${ctxStatic}/scripts/shopping.js"></script>

    <link type="text/css" rel="stylesheet" href="${ctxStatic}/styles/shop/base.css"/>
    <link type="text/css" rel="stylesheet" href="${ctxStatic}/styles/shop/module.css"/>
    <style type="text/css">
        input{
            background: transparent
        }
    </style>
</head>
<body>
<input type="hidden" id="path" value="${pageContext.request.contextPath}"/>
<!--头部开始-->
<div class="header">
    <h1>下单</h1>
    <a href="#" class="back"><span></span></a>
    <a href="#" class=""></a>
</div>
<!--头部结束-->
<form id="form" action="${pageContext.request.contextPath}/api/wechat/shopping" method="post">
    <input type="hidden" id="openId" name="openid" value="${openid}"/>
    <div class="shopping" style="font-size:14px;">

        <div class="shop-group-item">
            <div class="shop-name">
                区域:<select id="areaId" style="width:100px;">
                <option value=''>请选择</option>
            </select>&nbsp
                柜子:<select id="cabinetId" name="cabinetId" style="width:100px;">
                <option value=''>请选择</option>
            </select>
                <div id="address"></div>
            </div>
            <ul id="products">
            </ul>
            <div class="shopPrice">总计金额：￥<span class="shop-total-amount ShopTotal">0.00</span></div>
        </div>
    </div>

    <c:if test="${redpacketRelactions!=null}">
        <div class="red-shop-group-item">
            <div class="shop-name">
                <h4><a href="#">红包</a></h4>
            </div>
            <ul>
                <c:forEach items="${redpacketRelactions}" var="redpacket">
                    <li>
                        <div class="shop-info">
                            <input value="${redpacket.id}" type="radio" name="red" class="check goods-check redCheck">
                            <div class="shop-info-text">
                                <h4>${redpacket.redpacketName}</h4>
                                <div class="shop-brief">${redpacket.remark}</div>
                                <div class="shop-price">
                                    <div class="shop-pices">红包金额:￥<b
                                            class="redprice">${redpacket.redpacketPrice/100}</b></div>
                                </div>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </c:if>


    <div class="payment-bar">
        <div class="shop-total">
            <strong>实付金额：<i class="total" id="AllTotal">0.00</i></strong>
            <span>优惠金额：<i class="total" id="allfree">0.00</i></span>
        </div>
        <input type="submit" class="settlement" value="结算"/>
    </div>
</form>
</body>
</html>

