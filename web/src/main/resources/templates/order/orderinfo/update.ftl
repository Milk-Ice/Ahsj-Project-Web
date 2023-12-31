<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>通用后台管理模板系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/common.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/popup.css" media="all">
</head>
<body>
<script>
</script>
<div class="layui-card layui-content">
    <div class="layui-card-body">
        <form class="layui-form"  action="" lay-filter="component-form-element">
            <input type="hidden" id="id" name="id" value="${id}">
            <div class="layui-row layui-col-space10 layui-form-item">


<#--                <div class="layui-col-lg6">-->
<#--                        <label class="layui-form-label">id</label>-->
<#--                    <div class="layui-input-block">-->
<#--                        <input type="text"-->
<#--                               name="id"-->
<#--                               value="${obj.id}"-->
<#--                               autocomplete="off"-->
<#--                               class="layui-input">-->
<#--                    </div>-->
<#--                </div>-->


                <div class="layui-col-lg6">
                        <label class="layui-form-label">收货企业</label>
                    <div class="layui-input-block">
<#--                        <input type="text"-->
<#--                               name="custId"-->
<#--                               value="${obj.custId}"-->
<#--                               autocomplete="off"-->
<#--                               class="layui-input">-->
                        <select name="custId" lay-filter="custSelectFormLinkman">
                            <#list custList as list>

                                <option <#if list.id == obj.custId>selected</#if>  value="${list.id}">${list.customerName}</option>
                            </#list>

                        </select>
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">产品名称</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="prodName"
                               value="${obj.prodName}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">产品数量</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="amounts"
                               value="${obj.amounts}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">产品价格</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="price"
                               value="${obj.price}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">状态</label>
                    <div class="layui-input-block">
<#--                        <input type="text"-->
<#--                               name="status"-->
<#--                               value="${obj.status}"-->
<#--                               autocomplete="off"-->
<#--                               class="layui-input">-->
                        <select name="status">
                            <option <#if obj.status==0>selected</#if> value="0">未发货</option>
                            <option <#if obj.status==1>selected</#if> value="1">已发货</option>
                            <option <#if obj.status==2>selected</#if> value="2">已收货</option>
                            <option <#if obj.status==3>selected</#if> value="3">确认收货</option>
                        </select>
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">收货人</label>
                    <div class="layui-input-block">
<#--                        <input type="text"-->
<#--                               name="receiver"-->
<#--                               id="receiver"-->
<#--                               value="${obj.receiver}"-->
<#--                               autocomplete="off"-->
<#--                               class="layui-input">-->

                        <select name="receiver" id="receiver">
                            <option selected>${obj.receiver}</option>
                        </select>
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">收货人电话</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="linkPhone"
                               value="${obj.linkPhone}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">收货地址</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="address"
                               value="${obj.address}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">物流</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="logistcs"
                               value="${obj.logistcs}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">物流单号</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="logisticsCode"
                               value="${obj.logisticsCode}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">发货时间</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="deliverTime"
                               id="deliverTime"
                               value="${obj.deliverTime}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">收货时间</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="receiveTime"
                               id="receiveTime"
                               value="${obj.receiveTime}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn layui-btn-normal" lay-submit lay-filter="Add-filter">修改</button>
                        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

<script src="${request.contextPath}/layuiadmin/layui/layui.js"></script>
<script src="${request.contextPath}/layui-extend.js"></script>
<script src="${request.contextPath}/webjars/jquery/jquery.min.js"></script>
<script>

</script>
<script type="text/javascript" src="${request.contextPath}/scripts/order/orderinfo/update.js?_=${randomNum}"></script>
</body>
