<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信菜单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/wechatmenu/wechatMenu/">微信菜单列表</a></li>
		<%--<shiro:hasPermission name="wechatmenu:wechatMenu:edit"><li><a href="${ctx}/wechatmenu/wechatMenu/form">微信菜单添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="wechatMenu" action="${ctx}/wechatmenu/wechatMenu/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>menu</th>
				<shiro:hasPermission name="wechatmenu:wechatMenu:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wechatMenu">
			<tr>
				<td><a href="${ctx}/wechatmenu/wechatMenu/form?id=${wechatMenu.id}">
					${wechatMenu.menu}
				</a></td>
				<shiro:hasPermission name="wechatmenu:wechatMenu:edit"><td>
    				<a href="${ctx}/wechatmenu/wechatMenu/form?id=${wechatMenu.id}">修改</a>
					<%--<a href="${ctx}/wechatmenu/wechatMenu/delete?id=${wechatMenu.id}" onclick="return confirmx('确认要删除该微信菜单吗？', this.href)">删除</a>--%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>