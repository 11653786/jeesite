<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户红包关系管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/userredpacketrelaction/userRedpacketRelaction/">用户红包关系管理列表</a></li>
		<li class="active"><a href="${ctx}/userredpacketrelaction/userRedpacketRelaction/form?id=${userRedpacketRelaction.id}">用户红包关系管理<shiro:hasPermission name="userredpacketrelaction:userRedpacketRelaction:edit">${not empty userRedpacketRelaction.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="userredpacketrelaction:userRedpacketRelaction:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="userRedpacketRelaction" action="${ctx}/userredpacketrelaction/userRedpacketRelaction/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">优惠卷id：</label>
			<div class="controls">
				<form:input path="redpacketId" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">优惠卷名称：</label>
			<div class="controls">
				<form:input path="redpacketName" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">领取用户：</label>
			<div class="controls">
				<sys:treeselect id="userid" name="userid" value="${userRedpacketRelaction.userid}" labelName="" labelValue="${userRedpacketRelaction.}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户名称：</label>
			<div class="controls">
				<form:input path="userName" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">优惠金额：</label>
			<div class="controls">
				<form:input path="redpacketPrice" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">领取时间：</label>
			<div class="controls">
				<input name="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${userRedpacketRelaction.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">红包类型：</label>
			<div class="controls">
				<form:select path="redpacketType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('redpacket_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">折扣比例：</label>
			<div class="controls">
				<form:input path="discountRatio" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="userredpacketrelaction:userRedpacketRelaction:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>