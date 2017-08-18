<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>快餐柜管理管理</title>
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
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cabinet/cabinet/">快餐柜管理列表</a></li>
		<li class="active"><a href="${ctx}/cabinet/cabinet/form?id=${cabinet.id}">快餐柜管理<shiro:hasPermission name="cabinet:cabinet:edit">${not empty cabinet.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cabinet:cabinet:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cabinet" action="${ctx}/cabinet/cabinet/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">柜子名称：</label>
			<div class="controls">
				<form:input path="cabinetName" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">省：</label>
			<div class="controls">
				<sys:treeselect id="province" name="province" value="${cabinet.province}" labelName="" labelValue="${cabinet.province}"
					title="区域" url="/sys/area/treeData" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市：</label>
			<div class="controls">
				<sys:treeselect id="city" name="city" value="${cabinet.city}" labelName="" labelValue="${cabinet.city}"
					title="区域" url="/sys/area/treeData" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">区：</label>
			<div class="controls">
				<sys:treeselect id="area" name="area" value="${cabinet.area}" labelName="" labelValue="${cabinet.area}"
					title="区域" url="/sys/area/treeData" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">详细地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">柜子状态：</label>
			<div class="controls">
				<form:select path="cabinetStatus" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('cabinet_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input name="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${cabinet.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">柜子编号：</label>
			<div class="controls">
				<form:input path="cabinetNo" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">柜子对应的抽屉：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>抽屉编号</th>
								<th>柜子名称</th>
								<th>抽屉状态</th>
								<th>放餐状态</th>
								<th>创建时间</th>
								<shiro:hasPermission name="cabinet:cabinet:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="drawerList">
						</tbody>
						<shiro:hasPermission name="cabinet:cabinet:edit"><tfoot>
							<tr><td colspan="7"><a href="javascript:" onclick="addRow('#drawerList', drawerRowIdx, drawerTpl);drawerRowIdx = drawerRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="drawerTpl">//<!--
						<tr id="drawerList{{idx}}">
							<td class="hide">
								<input id="drawerList{{idx}}_id" name="drawerList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="drawerList{{idx}}_delFlag" name="drawerList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="drawerList{{idx}}_drawerNo" name="drawerList[{{idx}}].drawerNo" type="text" value="{{row.drawerNo}}" maxlength="11" class="input-small required"/>
							</td>
							<td>
								<input id="drawerList{{idx}}_cabinetName" name="drawerList[{{idx}}].cabinetName" type="text" value="{{row.cabinetName}}" maxlength="50" class="input-small "/>
							</td>
							<td>
								<select id="drawerList{{idx}}_drawerStatus" name="drawerList[{{idx}}].drawerStatus" data-value="{{row.drawerStatus}}" class="input-small required">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('drawer_status')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<select id="drawerList{{idx}}_foodStatus" name="drawerList[{{idx}}].foodStatus" data-value="{{row.foodStatus}}" class="input-small required">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('food_status')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="drawerList{{idx}}_createTime" name="drawerList[{{idx}}].createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
									value="{{row.createTime}}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</td>
							<shiro:hasPermission name="cabinet:cabinet:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#drawerList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var drawerRowIdx = 0, drawerTpl = $("#drawerTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(cabinet.drawerList)};
							for (var i=0; i<data.length; i++){
								addRow('#drawerList', drawerRowIdx, drawerTpl, data[i]);
								drawerRowIdx = drawerRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="cabinet:cabinet:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>