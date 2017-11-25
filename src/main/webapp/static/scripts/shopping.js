$(function () {

    var path = $("#path").val();

    //填充区域列表,获取西安市的列表信息
    $.ajax({
        type: "POST",
        url: path + "/api/wechat/getAreas",
        data: {},
        dataType: "json",
        success: function (data) {
            if (data['code'] == '0') {
                var list = data['data'];
                var areaValues = "<option value=''>请选择</option>";
                for (var a in list) {
                    areaValues += "<option value=" + list[a]['id'] + ">" + list[a]['name'] + "</option>";
                }

                $("#areaId").html(areaValues);
            }
        }
    });


    //修改地区显示不同柜子!
    $("#areaId").change(function () {
        var areaId = $("#areaId").val();
        $.ajax({
            type: "POST",
            url: path + "/api/wechat/getCabinetByAreaId",
            data: {"areaId": areaId},
            dataType: "json",
            success: function (data) {
                if (data['code'] == '0') {
                    var list = data['data'];
                    var cabinetValue = "<option value=''>请选择</option>";
                    for (var a in list) {
                        cabinetValue += "<option value=" + list[a]['id'] + ">" + list[a]['address'] + "</option>";
                    }

                    $("#cabinetId").html(cabinetValue);
                }
            }
        });
    });

    //柜子变化,查询出当前柜子可售的商品
    $("#cabinetId").change(function () {
        var cabinetId = $("#cabinetId").val();
        $.ajax({
            type: "POST",
            url: path + "/api/wechat/getSaleProductByCabinetId",
            data: {"cabinetId": cabinetId},
            dataType: "json",
            success: function (data) {
                if (data['code'] == '0') {
                    var list = data['data'];
                    var products = "";
                    for (var a in list) {

                        products += "<li><div class=shop-info>" +
                            "<input type=checkbox name=ids value=" + list[a]['id'] + " class='check goods-check goodsCheck'>" +
                            "<div class=shop-info-img><a href=#>" +
                            "<img src=" + path+"/"+list[a]['imgurl']+" /></a></div>" +
                            "<div class=shop-info-text>" +
                            "<h4>" + list[a]['productName'] + "</h4>" +
                            "<div class=shop-brief>" +
                            "<span>" + list[a]['remark'] + "</span>" +
                            "</div>" +
                            "<div class=shop-price>" +
                            "<div class=shop-pices>￥<b class=price>" + list[a]['productActualPrice'] / 100 + "</b></div>" + "<div class=shop-arithmetic>" +
                            "<input type='button' value=-  class=minus>" + "<span class=num>1</span><input type='button' value=+  class=plus></a><input type=hidden class=nums   value=1 /></div></div></div></div></li>";
                    }


                    $("#products").html(products);
                }
            }
        });


    });


    // 数量减
    $(".minus").live('click', function () {
        var t = $(this).parent().find('.num');
        var nums = $(this).parent().find('.nums');
        t.text(parseInt(t.text()) - 1);
        if (t.text() <= 1) {
            t.text(1);
        }

        if (t.text() >= 4) {
            t.text(4);
        }

        nums.val(t.text());
        TotalPrice();
    });


    // 数量加
    $(".plus").live('click', function () {
        var t = $(this).parent().find('.num');
        var nums = $(this).parent().find('.nums');
        t.text(parseInt(t.text()) + 1);
        if (t.text() <= 1) {
            t.text(1);
        }
        if (t.text() >= 4) {
            t.text(4);
        }

        nums.val(t.text());
        TotalPrice();
    });
    /******------------分割线-----------------******/
    // 点击商品按钮
    $(".goodsCheck").live('click', function () {
        var checked = $(this).attr("checked");
        if (checked == undefined || checked == null || checked == '') {
            $(this).parent().children(".shop-info-text").children(".shop-price").children(".shop-arithmetic").children(".nums").removeAttr("name");
        } else {
            //
            $(this).parent().children(".shop-info-text").children(".shop-price").children(".shop-arithmetic").children(".nums").attr("name", "nums");
        }


        // 计算
        TotalPrice();
    });

    //红包点击事件
    $(".redCheck").live('click', function () {
        var reds = $(this).closest(".red-shop-group-item").find(".redCheck"); //获取本店铺的所有商品
        var redId = reds.val();
        TotalPrice();
    });

    //计算
    function TotalPrice() {
        var allprice = 0; //总价
        var redprice = 0;  //见面金额
        $(".shop-group-item").each(function () { //循环每个店铺
            var oprice = 0.00; //店铺总价

            $(this).find("input[type=checkbox]").each(function () { //循环店铺里面的商品
                if ($(this).is(":checked")) { //如果该商品被选中
                    var num = parseInt($(this).parents(".shop-info").find(".num").text()); //得到商品的数量
                    var price = parseFloat($(this).parents(".shop-info").find(".price").text()); //得到商品的单价
                    var total = price * num; //计算单个商品的总价
                    oprice += total; //计算该店铺的总价
                }
                $(this).closest(".shop-group-item").find(".ShopTotal").text(oprice); //显示被选中商品的店铺总价
            });
            var oneprice = parseFloat($(this).find(".ShopTotal").text()); //得到每个店铺的总价
            allprice += oneprice; //计算所有店铺的总价
        });


        //红包金额显示的问题
        $(".red-shop-group-item").each(function () {
            $(this).find("input[type=radio]").each(function () { //循环店铺里面的商品
                if ($(this).is(":checked")) { //如果该商品被选中
                    redprice = parseFloat($(this).parents(".shop-info").find(".redprice").text()); //得到商品的单价
                    $("#allfree").text(redprice.toFixed(2));
                }
            });
        });

        $("#AllTotal").text((allprice - redprice).toFixed(2)); //输出全部总价

    }


    $("#form").submit(function () {

        var isTrue = true;

        $.ajax({
            type: "POST",
            url: path + "/api/wechat/validPreOrder",
            data: $("#form").serialize(),
            dataType: "json",
            async: false,      //取消異步通知
            success: function (data) {
                if (data['code'] != '0') {
                    alert(data['message']);
                    isTrue = false;
                }

            }
        });
        return isTrue;

    });

    //$(".settlement").click(function () {
    //    var ids = "";
    //    var nums = "";
    //    $("input[type=checkbox]").each(function () { //循环店铺里面的商品
    //        if ($(this).is(":checked")) { //如果该商品被选中
    //            var num = parseInt($(this).parents(".shop-info").find(".num").text()); //得到商品的数量
    //            var productId = $(this).val();
    //            ids = ids + "," + productId;
    //            nums = nums + "," + num;
    //        }
    //    });
    //
    //    var redpackgetId = $("input[type=radio]:checked").val();
    //    var cabinetId = $("#cabinetId").val();
    //
    //    if (ids != '' && nums != '') {
    //        //ajax验证是否可以下单这么多商品
    //        $.ajax({
    //            type: "POST",
    //            url: path + "/api/order/validPreOrder",
    //            data: {"ids": ids, "nums": nums, "cabinetId": cabinetId},
    //            dataType: "json",
    //            success: function (data) {
    //
    //            }
    //        });
    //
    //    }
    //
    //});


});
