<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>优惠卷管理管理</title>
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
		<li class="active"><a href="${ctx}/redpacket/redpacket/">优惠卷管理列表</a></li>
		<shiro:hasPermission name="redpacket:redpacket:edit"><li><a href="${ctx}/redpacket/redpacket/form">优惠卷管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="redpacket" action="${ctx}/redpacket/redpacket/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>红包名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>红包状态：</label>
				<form:input path="status" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>红包类型：</label>
				<form:input path="redpacketType" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>红包名称</th>
				<th>优惠金额</th>
				<th>红包数量</th>
				<th>红包状态</th>
				<th>remark</th>
				<th>红包类型</th>
				<th>折扣比率</th>
				<th>过期天书</th>
				<shiro:hasPermission name="redpacket:redpacket:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="redpacket">
			<tr>
				<td><a href="${ctx}/redpacket/redpacket/form?id=${redpacket.id}">
					${redpacket.name}
				</a></td>
				<td>
					${redpacket.redpacketPrice}
				</td>
				<td>
					${redpacket.redpacketTotal}
				</td>
				<td>
					${redpacket.status}
				</td>
				<td>
					${redpacket.remark}
				</td>
				<td>
					${redpacket.redpacketType}
				</td>
				<td>
					${redpacket.discountRatio}
				</td>
				<td>
					${redpacket.limitDay}
				</td>
				<shiro:hasPermission name="redpacket:redpacket:edit"><td>
    				<a href="${ctx}/redpacket/redpacket/form?id=${redpacket.id}">修改</a>
					<a href="${ctx}/redpacket/redpacket/delete?id=${redpacket.id}" onclick="return confirmx('确认要删除该优惠卷管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>