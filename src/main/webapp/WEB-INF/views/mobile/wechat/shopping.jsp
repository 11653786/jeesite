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
<div class="shopping">

    <div class="shop-group-item">
        <div class="shop-name">
            <input type="checkbox" class="check goods-check shopCheck">
            <h4><a href="#">牛上山套餐</a></h4>&nbsp
            区域:<select id="areaId" style="width:100px;">
            <option value=''>请选择</option>
        </select>&nbsp
            柜子:<select id="cabinetId" style="width:100px;">
            <option value=''>请选择</option>
        </select>
            <div id="address"></div>
        </div>
        <ul id="products">
            <%--<li>--%>
                <%--<div class="shop-info">--%>
                    <%--<input type="checkbox" class="check goods-check goodsCheck">--%>
                    <%--<div class="shop-info-img"><a href="#"><img src="${ctxStatic}/images/computer.jpg"/></a></div>--%>
                    <%--<div class="shop-info-text">--%>
                        <%--<h4>Apple MacBook Pro 13.3英寸笔记本电脑 银色(Core i5 处理器/8GB内存/128GB SSD闪存/Retina屏 MF839CH/A)</h4>--%>
                        <%--<div class="shop-brief"><span>重量:3.3kg</span><span>颜色:标配版</span><span>版本:13.3英寸</span></div>--%>
                        <%--<div class="shop-price">--%>
                            <%--<div class="shop-pices">￥<b class="price">100.00</b></div>--%>
                            <%--<div class="shop-arithmetic">--%>
                                <%--<a  class="minus">-</a>--%>
                                <%--<span class="num">1</span>--%>
                                <%--<a  class="plus">+</a>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</li>--%>
        </ul>
        <div class="shopPrice">本店总计：￥<span class="shop-total-amount ShopTotal">0.00</span></div>
    </div>
</div>

<div class="payment-bar">
    <div class="all-checkbox"><input type="checkbox" class="check goods-check" id="AllCheck">全选</div>
    <div class="shop-total">
        <strong>总价：<i class="total" id="AllTotal">0.00</i></strong>
        <span>减免：123.00</span>
    </div>
    <a href="#" class="settlement">结算</a>
</div>
</body>
</html>

