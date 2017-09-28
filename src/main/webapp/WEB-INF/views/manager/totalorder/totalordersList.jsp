<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>统计</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            //地区列表变化的时候


            $("#areaName").change(function(){
                var areaId=$("#areaId").val();
                $.ajax({
                    //要用post方式
                    type: "post",
                    //方法所在页面和方法名
                    url: "${ctx}/totalorder/totalorder/getCabinetByAreaId?areaId="+areaId,
                    dataType: "json",
                    success: function(data) {
                        //返回的数据用data.d获取内容
                        var selectValue="<option value=''>请选择</option>";
                        for(var a in data){
                            selectValue=selectValue+"<option value="+data[a]['cabinetNos']+">"+data[a]['cabinetName']+"</option>";
                        }

                       $("#cabinetNo").html(selectValue);
                    },
                    error: function(err) {
                        alert(err);
                    }
                });

            });

            $("#btnExport").click(function(){
                top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
                    if(v=="ok"){
                        $("#searchForm").attr("action","${ctx}/totalorder/totalorder/export");
                        var areaName=$("#areaName").val();
                        if(areaName==null || areaName==undefined){
                            alert("请选择区域");
                            return false;
                        }
                        $("#searchForm").submit();
                    }
                },{buttonsFocus:1});
                top.$('.jbox-body .jbox-icon').css('top','55px');
            });

		});

	</script>
</head>
<body>
	<form id="searchForm" modelAttribute="orders" action="${ctx}/totalorder/totalorder/export" method="post" class="breadcrumb form-search">
        <li>
            <label>时间区域：</label>
            <input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${orders.beginPaymentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
            <input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${orders.endPaymentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        <li><label>区：</label>
            <sys:treeselect  id="area" name="areaId" value="" labelName="" labelValue=""
                            title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true"
                            notAllowSelectParent="true"/>
        </li>
        <li><label>柜子：</label>
        <select id="cabinetNo" name="cabinetNo" class="input-medium">
        </select>
        </li>
        <input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
    </form>
    <sys:message content="${message}"/>
</body>
</html>