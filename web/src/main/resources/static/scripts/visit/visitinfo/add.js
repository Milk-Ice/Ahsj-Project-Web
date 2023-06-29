layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery;

    form.on('submit(Add-filter)', function (data) {
        $.ajax({
            url: web.rootPath() + "visitinfo/save",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(data.field),
            dataType: 'json',
            traditional: true,
            success: function (data) {
                layer.msg("操作成功", {
                    icon: 1,
                    success: function () {
                        reloadTb("Save-frame", "#SearchBtn");
                    }
                });
            },
            error: function (e) {
                layer.msg(e.responseJSON.message, {icon: 2});
            }

        });
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
                $("#linkmanId").empty(); //每一次数据渲染的时候做清空
                //组装数据
                var optionHtml = `<option value="">--请选择--</option>`
                if(e.data.length>0){
                    e.data.forEach(item=>{
                        optionHtml+= `<option value="${item.id}">${item.linkman}</option>`
                    })
                }
                //设置选择信息
                $("#linkmanId").html(optionHtml);
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
