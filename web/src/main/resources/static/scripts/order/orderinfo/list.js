layui.use(['form', 'layer', 'table', 'laytpl', 'laydate'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        laydate = layui.laydate,
        table = layui.table;

    // 渲染
    laydate.render({
        elem: '#startTime'
    });
    laydate.render({
        elem: '#endTime'
    });

    //用户列表
    var tableIns = table.render({
        elem: '#List',
        url: web.rootPath() + 'orderinfo/list',
        cellMinWidth: 95,
        page: true,
        height: "full-" + Math.round(Number($('.layui-card-header').height()) + 44),
        limits: [10, 13, 15, 20, 30, 50, 100, 200],
        limit: 10,
        toolbar: '#List-toolbar',
        id: "ListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            // {field: 'id', title:  'id', minWidth: 100, align: "center"},
            {field: 'custName', title: '所属企业', minWidth: 100, align: "center"},
            {field: 'prodName', title: '产品名称', minWidth: 100, align: "center"},
            {field: 'amounts', title: '产品数量', minWidth: 100, align: "center"},
            {field: 'price', title: '产品价格', minWidth: 100, align: "center"},
            {
                field: 'status', title: '状态', minWidth: 100, align: "center", templet:
                    function (e) {
                        if (e.status == 0) {
                            return " <button type=\"button\" class=\"layui-btn layui-btn-normal\">未发货</button>";
                        } else if (e.status == 1) {
                            return " <button type=\"button\" class=\"layui-btn layui-btn-warm\">已发货</button>";
                        } else if (e.status == 2) {
                            return " <button type=\"button\" class=\"layui-btn layui-btn-danger\">已收货</button>";
                        } else if (e.status == 3) {
                            return " <button type=\"button\" class=\"layui-btn layui-btn-disabled\">确认收货</button>";
                        } else {
                            return '未知数据';
                        }

                    }
            },
            {field: 'inputTime', title: '录入时间', minWidth: 130, align: "center"},
            {field: 'receiverName', title: '收货人', minWidth: 100, align: "center"},
            {field: 'linkPhone', title: '收货人电话', minWidth: 100, align: "center"},
            {field: 'address', title: '收货地址', minWidth: 100, align: "center"},
            {field: 'logistcs', title: '物流', minWidth: 100, align: "center"},
            {field: 'logisticsCode', title: '物流单号', minWidth: 100, align: "center"},
            {field: 'deliverTime', title: '发货时间', minWidth: 100, align: "center"},
            {field: 'receiveTime', title: '收货时间', minWidth: 100, align: "center"},

            {title: '操作', width: 160, templet: '#List-editBar', fixed: "right", align: "center"}
        ]],

    });

    //头工具栏事件
    table.on('toolbar(List-toolbar)', function (obj) {
        switch (obj.event) {
            case 'add':
                layer.msg("add");
                break;
            case 'update':
                layer.msg("update");
                break;
            case 'delete':
                layer.msg("delete");
                break;
            case 'export':
                layer.msg("export");
                break;
        }
        ;
    });

    var $ = layui.$, active = {
        reload: function () {
            //获取搜索条件值
            var parameterName = $("#searchForm").find("input[name='parameterName']").val().trim();
            var startTime = $("#searchForm").find("input[name='startTime']").val().trim();
            var endTime = $("#searchForm").find("input[name='endTime']").val().trim();
            //表格重载
            tableIns.reload({
                where: { //设定异步数据接口的额外参数，任意设
                    parameterName: parameterName,
                    startTime: startTime,
                    endTime: endTime
                }
            });
        }
    };

    $('#SearchBtn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });


    //头工具栏事件
    table.on('toolbar(List-toolbar)', function (obj) {
        if (obj.event == 'add') {
            layer.open({
                id: "Save-frame",
                type: 2,
                area: ['600px', '501px'],
                title: '新增',
                fixed: false,
                maxmin: true,
                content: web.rootPath() + 'orderinfo/add.html'
            });
        }
        if (obj.event == 'export') {

            //获取搜索条件值
            var parameterName = $("#searchForm").find("input[name='parameterName']").val().trim();
            var startTime = $("#searchForm").find("input[name='startTime']").val().trim();
            var endTime = $("#searchForm").find("input[name='endTime']").val().trim();

            var eix;
            $.fileDownload(web.rootPath() + "orderinfo/export?parameterName=" + parameterName + "&startTime=" + startTime + "&endTime=" + endTime, {

                httpMethod: 'POST',
                // data: {'parameterName':parameterName,'province':province,'openStatus':openStatus},
                // data: buildCondition(),
                prepareCallback: function (url) {
                    eix = layer.load(2);
                },
                successCallback: function (url) {
                    layer.close(eix)
                },
                failCallback: function (html, url) {
                    layer.close(eix)
                    layer.msg("导出失败", {icon: 2});
                }
            });
        }

    });
    //监听工具条
    table.on('tool(List-toolbar)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'update':
                layer.open({
                    id: "Update-frame",
                    type: 2,
                    resize: false,
                    area: ['550px', '500px'],
                    title: '修改',
                    fixed: false,
                    maxmin: true,
                    content: web.rootPath() + "orderinfo/" + data.id + ".html?_"
                });
                break;
            case 'delete':
                var index = layer.confirm('确定要删除?', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    $.ajax({
                        url: web.rootPath() + "orderinfo/delete/" + data.id,
                        type: "delete",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(data.field),
                        dataType: 'json',
                        success: function (data) {
                            layer.msg("操作成功", {
                                icon: 1,
                                success: function () {
                                    $('#SearchBtn').trigger("click");
                                }
                            });
                        },
                        error: function (e) {
                            layer.msg(e.responseJSON.message, {icon: 2});
                        }
                    })
                }, function () {
                });
                break;
        }
        ;
    });

    $(window).resize(function () {
        $('div[lay-id="ListTable"]').height(document.body.offsetHeight - Math.round(Number($('.layui-card-header').height()) + 47));
    });
});
