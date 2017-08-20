<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客户管理管理</title>
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
		<li class="active"><a href="${ctx}/users/user/">客户管理列表</a></li>
		<shiro:hasPermission name="users:user:edit"><li><a href="${ctx}/users/user/form">客户管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/users/user/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户名：</label>
				<form:input path="userName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="phone" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>登录状态：</label>
				<form:select path="userStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('user_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>微信标志：</label>
				<form:input path="openid" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>支付宝标志：</label>
				<form:input path="alipayid" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户名</th>
				<th>手机号</th>
				<th>登录状态</th>
				<th>微信标志</th>
				<th>支付宝标志</th>
				<th>会员积分</th>
				<shiro:hasPermission name="users:user:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td><a href="${ctx}/users/user/form?id=${user.id}">
					${user.userName}
				</a></td>
				<td>
					${user.phone}
				</td>
				<td>
					${fns:getDictLabel(user.userStatus, 'user_status', '')}
				</td>
				<td>
					${user.openid}
				</td>
				<td>
					${user.alipayid}
				</td>
				<td>
					${user.integral}
				</td>
				<shiro:hasPermission name="users:user:edit"><td>
    				<a href="${ctx}/users/user/form?id=${user.id}">修改</a>
					<a href="${ctx}/users/user/delete?id=${user.id}" onclick="return confirmx('确认要删除该客户管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>