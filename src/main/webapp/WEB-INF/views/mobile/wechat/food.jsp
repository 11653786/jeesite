<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<title>美食介绍</title>

<link href="${ctxStatic}/styles/style.css"     		rel="stylesheet" type="text/css">
<link href="${ctxStatic}/styles/framework.css" 		rel="stylesheet" type="text/css">
<link href="${ctxStatic}/styles/owl.carousel.css" 	 rel="stylesheet" type="text/css">
<link href="${ctxStatic}/styles/owl.theme.css" 		rel="stylesheet" type="text/css">
<link href="${ctxStatic}/styles/swipebox.css"		 rel="stylesheet" type="text/css">
<link href="${ctxStatic}/styles/colorbox.css"		 rel="stylesheet" type="text/css">

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
            <div class="container-fluid">
                <div class="slider-controls" data-snap-ignore="true">
                    <c:forEach items="${products}" var="product">
                        <div>
                            <img src="/${product.imgurl}" class="responsive-image" alt="img">
                            <p class="title-slider-caption">
                                <strong>${product.productName}</strong>
                                <em>${product.remark}</em>
                            </p>
                        </div>
                    </c:forEach>

                </div>
                <a href="#" class="next-slider"></a>
                <a href="#" class="prev-slider"></a>
            </div>
                   
            <div class="decoration"></div>
            
            <div class="container no-bottom">
            	<div class="section-title">
                	<h4>套餐宣传!</h4>
                    <em>中华美食 健康你我。</em>
                    <strong><img src="${ctxStatic}/images/wechat/leaf.png" width="20" alt="img"></strong>
                </div>
                <p>。。。。。。。。。。。。。。。。</p>
            </div>
            
            <div class="decoration"></div>  
        </div>                
</body>
</html>
























