<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>统计</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //地区列表变化的时候


            $("#areaName").change(function () {
                var areaId = $("#areaId").val();
                $.ajax({
                    //要用post方式
                    type: "post",
                    //方法所在页面和方法名
                    url: "${ctx}/orderlog/orderlog/getCabinetByAreaId?areaId=" + areaId,
                    dataType: "json",
                    success: function (data) {
                        //返回的数据用data.d获取内容
                        var selectValue = "<option value=''>请选择</option>";
                        for (var a in data) {
                            selectValue = selectValue + "<option value=" + data[a]['cabinetNos'] + ">" + data[a]['cabinetName'] + "</option>";
                        }

                        $("#cabinetNo").html(selectValue);
                    },
                    error: function (err) {
                        alert(err);
                    }
                });

            });

            $("#btnExport").click(function () {
                top.$.jBox.confirm("确认要导出数据吗？", "系统提示", function (v, h, f) {
                    if (v == "ok") {
                        $("#searchForm").attr("action", "${ctx}/orderlog/orderlog/export");
                        var areaName = $("#areaName").val();
                        if (areaName == null || areaName == undefined || areaName == '') {
                            alert("请选择区域");
                            return false;
                        }
                        $("#searchForm").submit();
                    }
                }, {buttonsFocus: 1});
                top.$('.jbox-body .jbox-icon').css('top', '55px');
            });

            //查询
            $("#btnExport1").click(function () {
                $("#searchForm").attr("action", "${ctx}/orderlog/orderlog");
                var areaName = $("#areaName").val();
                if (areaName == null || areaName == undefined || areaName == '') {
                    alert("请选择区域");
                    return false;
                }
                $("#searchForm").submit();
                top.$('.jbox-body .jbox-icon').css('top', '55px');
            });

        });

    </script>
</head>
<body>
<form id="searchForm" modelAttribute="orders" action="${ctx}/orderlog/orderlog/export" method="post"
      class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <li>
        <label>时间区域：</label>
        <input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
               value="<fmt:formatDate value="${startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
               onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
        <input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
               value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
               onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
    <li><label>区：</label>
        <sys:treeselect id="area" name="areaId" value="" labelName="" labelValue=""
                        title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true"
                        notAllowSelectParent="true"/>
    </li>
    <li><label>柜子：</label>
        <select id="cabinetNo" name="cabinetNo" class="input-medium">
        </select>
    </li>
    <li><label>下单类型：</label>
        <select name="submitOrderType" path="submitOrderType" class="input-medium">
            <option value="">请选择</option>
            <option value="0">非会员</option>
            <option value="1">会员</option>
        </select>
    </li>
    <input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
    <input id="btnExport1" class="btn btn-primary" type="button" value="查询"/>
</form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
<thead>
<tr>
    <th>区域名称</th>
    <th>柜子编号</th>
    <th>菜品价格统计</th>
    <th>菜品数量统计</th>
    <th>总数量</th>
    <th>总金额</th>
</tr>
</thead>
<tbody>
<c:forEach items="${page.list}" var="orderLog">
    <tr>
        <td>
                ${orderLog.areaName}
        </td>
        <td>
                ${orderLog.cabinetNo}
        </td>
        <td>
                ${orderLog.totalPrice}
        </td>
        <td>
                ${orderLog.productNum}
        </td>
        <td>
                ${orderLog.productTotalPrice}
        </td>
        <td>
                ${orderLog.totalProductNum}
        </td>
    </tr>
</c:forEach>
</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>