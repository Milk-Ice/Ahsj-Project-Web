layui.use(['form', 'layer', 'laydate'], function () {
    var form = layui.form,
        layer = layui.layer,
        laydate = layui.laydate, //日期时间插件
        $ = layui.jquery;
    // 渲染
    laydate.render({
        elem: '#deliverTime'
    });
    laydate.render({
        elem: '#receiveTime'
    });

    form.on('submit(Add-filter)', function (data) {
        $.ajax({
            url: web.rootPath() + "orderinfo/update",
            contentType: "application/json",
            type: "put",
            data: JSON.stringify(data.field),
            dataType: 'json',
            success: function (data) {
                layer.msg("操作成功", {
                    icon: 1,
                    success: function () {
                        reloadTb("Update-frame", "#SearchBtn");
                    }
                });
            },
            error: function (e) {
                layer.msg(e.responseJSON.message, {icon: 2});
            }

        })
        return false;
    });

    form.on('select(custSelectFormLinkman)',function (data){
        // var value = data.value; // 获得被选中的值
        // console.log(value)
        //select * from tb_cust_linkman where cust_id = 'b3f65b2d929e48dd5838721d84400625'
        $.ajax({
            url: web.rootPath() + "linkman/queryByCustIdList?custId="+data.value,
            type: "POST",
            contentType: "application/json",
            // data: data.value,
            dataType: "JSON",
            //成功回调方法
            success: function (e){
                $("#receiver").empty(); //每一次数据渲染的时候做清空
                //组装数据
                // var optionHtml = `<option value="">--请选择--</option>`
                var optionHtml = ``

                if(e.data.length>0){
                    e.data.forEach(item=>{
                        optionHtml+= `<option value="${item.id}">${item.linkman}</option>`
                    })
                }
                //设置选择信息
                $("#receiver").html(optionHtml);
                //渲染数据
                form.render('select')

            },
            //错误的方法回调方法
            error: function (e){
                layer.msg(e.responseJSON.message, {icon: 2});
            }

        })


    })

});
