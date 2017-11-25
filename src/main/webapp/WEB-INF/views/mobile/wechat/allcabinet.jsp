<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>快餐柜</title>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/styles/style_cabinet.css">
</head>

<body>

<div id="_centent">

    <section class="mt-1">
        <div class="ps-lt">
            <c:forEach items="${cabinets}" var="cabinet">
            <div class="lt-dsb" >
                <p style="font-size: 14px;"><b>${cabinet.address}</b></p>
                <i class="arr-right"></i>
            </div>
            </c:forEach>


    </section>
    <div class="jg"></div>
</div>
<script>
    (function (doc, win) {
        var docEl = doc.documentElement,
                resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
                recalc = function () {
                    var clientWidth = docEl.clientWidth;
                    if (!clientWidth) return;
                    docEl.style.fontSize = 100 * (clientWidth / 750) + 'px';
                };

        if (!doc.addEventListener) return;
        win.addEventListener(resizeEvt, recalc, false);
        doc.addEventListener('DOMContentLoaded', recalc, false);
    })(document, window);
</script>
</body>
</html>
