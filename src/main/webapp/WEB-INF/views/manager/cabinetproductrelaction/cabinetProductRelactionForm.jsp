<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>柜子商品配置表管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
        function addRow(list, idx, tpl, row) {
            $(list).append(Mustache.render(tpl, {
                idx: idx, delBtn: true, row: row
            }));
            $(list + idx).find("select").each(function () {
                $(this).val($(this).attr("data-value"));
            });
            $(list + idx).find("input[type='checkbox'], input[type='radio']").each(function () {
                var ss = $(this).attr("data-value").split(',');
                for (var i = 0; i < ss.length; i++) {
                    if ($(this).val() == ss[i]) {
                        $(this).attr("checked", "checked");
                    }
                }
            });
        }
        function delRow(obj, prefix) {
            var id = $(prefix + "_id");
            var delFlag = $(prefix + "_delFlag");
            if (id.val() == "") {
                $(obj).parent().parent().remove();
            } else if (delFlag.val() == "0") {
                delFlag.val("1");
                $(obj).html("&divide;").attr("title", "撤销删除");
                $(obj).parent().parent().addClass("error");
            } else if (delFlag.val() == "1") {
                delFlag.val("0");
                $(obj).html("&times;").attr("title", "删除");
                $(obj).parent().parent().removeClass("error");
            }
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/drawer/drawer/">抽屉管理</a></li>
    <shiro:hasPermission name="cabinetproductrelaction:cabinetProductRelaction:edit">
        <li class="active">
            <a href="${ctx}/cabinetproductrelaction/cabinetProductRelaction/form?id=${cabinet.id}">配置商品</a>
        </li>
    </shiro:hasPermission>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="cabinetProductRelactionList"
           action="${ctx}/cabinetproductrelaction/cabinetProductRelaction/save" method="post" class="form-horizontal">
    <input type="hidden" name="drawerId" value="${drawer.id}">
    <input type="hidden" name="cabinetId" value="${drawer.cabinetId}">
    <sys:message content="${message}"/>

    <div class="control-group">
        <label class="control-label">商品：</label>
        <div class="controls">
            <sys:treeselect id="productId" name="productId" value="" labelName="" labelValue=""
                            title="商品" url="/product/product/getProductList" cssClass="required" allowClear="true"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">商品列表：</label>
        <div class="controls">
            <table id="contentTable" class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th class="hide"></th>
                    <th>柜子名称</th>
                    <th>抽屉编号</th>
                    <th>商品名称</th>
                    <shiro:hasPermission name="cabinet:cabinet:edit">
                        <th width="10">&nbsp;</th>
                    </shiro:hasPermission>
                </tr>
                </thead>
                <tbody id="cabinetProductRelactionList">
                </tbody>
                    <%--<shiro:hasPermission name="cabinet:cabinet:edit">--%>
                    <%--<tfoot>--%>
                    <%--<tr>--%>
                    <%--<td colspan="7"><a href="javascript:"--%>
                    <%--onclick="addRow('#cabinetProductRelactionList', drawerRowIdx, drawerTpl);drawerRowIdx = drawerRowIdx + 1;"--%>
                    <%--class="btn">新增</a></td>--%>
                    <%--</tr>--%>
                    <%--</tfoot>--%>
                    <%--</shiro:hasPermission>--%>
            </table>
            <script type="text/template" id="drawerTpl">//<!--
						<tr id="cabinetProductRelactionList{{idx}}">
							<td class="hide">
								<input id="cabinetProductRelactionList{{idx}}_id" name="cabinetProductRelactionList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						        			<input id="cabinetProductRelactionList{{idx}}_productId" name="cabinetProductRelactionList[{{idx}}].productId" type="hidden" value="{{row.productId}}"/>
								</td>
							<td>
								<input id="cabinetProductRelactionList{{idx}}_cabinetName" name="cabinetProductRelactionList[{{idx}}].cabinetName" type="text" readonly="readonly" maxlength="20" class="input-medium required"
									value="${cabinet.cabinetName}" />
							</td>
                            <td>
								<input id="cabinetProductRelactionList{{idx}}_cabinetName" name="cabinetProductRelactionList[{{idx}}].cabinetName" type="text" readonly="readonly" maxlength="20" class="input-medium required"
									value="${drawer.drawerNo}" />
							</td>
							<td>
							<input id="cabinetProductRelactionList{{idx}}_productName" name="cabinetProductRelactionList[{{idx}}].productName" type="text" readonly="readonly" maxlength="20" class="input-medium required"
									value="{{row.productName}}" />
							</td>
							<td>
								<shiro:hasPermission name="cabinetproductrelaction:cabinetProductRelaction:edit">
							   <a href="${ctx}/cabinetproductrelaction/cabinetProductRelaction/delete?id={{row.id}}" onclick="return confirmx('确认要删除该快餐柜管理吗？', this.href)">删除</a>
                            </shiro:hasPermission>
							</td>
						</tr>//-->
            </script>
            <script type="text/javascript">
                var drawerRowIdx = 0, drawerTpl = $("#drawerTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
                $(document).ready(function () {
                    var data = ${fns:toJson(cabinetProductRelactionList)};
                    for (var i = 0; i < data.length; i++) {
                        addRow('#cabinetProductRelactionList', drawerRowIdx, drawerTpl, data[i]);
                        drawerRowIdx = drawerRowIdx + 1;
                    }
                });
            </script>
        </div>
    </div>


    <div class="form-actions">
        <shiro:hasPermission name="cabinetproductrelaction:cabinetProductRelaction:edit"><input id="btnSubmit"
                                                                                                class="btn btn-primary"
                                                                                                type="submit"
                                                                                                value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>

</form:form>
</body>
</html>