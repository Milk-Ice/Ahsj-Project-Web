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


               <#-- <div class="layui-col-lg6">
                        <label class="layui-form-label">id</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="id"
                               value="${obj.id}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>-->


                <div class="layui-col-lg6">
                        <label class="layui-form-label">所属企业</label>
                    <div class="layui-input-block">
                     <#--   <input type="text"
                               name="custId"
                               value="${obj.custId}"
                               autocomplete="off"
                               class="layui-input">-->

                        <select name="custId">
                            <#list custList as list>

                                    <option <#if list.id == obj.custId>selected</#if>  value="${list.id}">${list.customerName}</option>
                            </#list>

                        </select>

                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">联系人名字</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="linkman"
                               value="${obj.linkman}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">性别</label>
                    <div class="layui-input-block">
                        <#--<input type="text"
                               name="sex"
                               value="${obj.sex}"
                               autocomplete="off"
                               class="layui-input">-->

                        <div class="layui-form">
                            <input type="radio" <#if obj.sex == 1>checked</#if> name="sex" value="1" title="男">
                            <div lay-radio>
                                <span style="color: blue;">男♂</span>
                            </div>
                            <input type="radio" <#if obj.sex == 0>checked</#if> name="sex" value="0" title="女">
                            <div lay-radio>
                                <span style="color: pink;">女♀</span>
                            </div>
                        </div>

                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">年龄</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="age"
                               value="${obj.age}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">联系人电话</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="phone"
                               value="${obj.phone}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">职位</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="position"
                               value="${obj.position}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">部门</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="department"
                               value="${obj.department}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">备注信息</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="remark"
                               value="${obj.remark}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>

                <div class="layui-col-lg6">
                    <label class="layui-form-label">任职状态</label>
                    <div class="layui-input-block">
                        <select name="officeStatus">
                            <option <#if obj.officeStatus==0>selected</#if> value="0">在职</option>
                            <option <#if obj.officeStatus==1>selected</#if> value="1">离职</option>
                        </select>
                    </div>
                </div>


<#--                <div class="layui-col-lg6">-->
<#--                        <label class="layui-form-label">录入人</label>-->
<#--                    <div class="layui-input-block">-->
<#--                        <input type="text"-->
<#--                               name="inputUser"-->
<#--                               value="${obj.inputUser}"-->
<#--                               autocomplete="off"-->
<#--                               class="layui-input">-->
<#--                    </div>-->
<#--                </div>-->


               <#-- <div class="layui-col-lg6">
                        <label class="layui-form-label">录入时间</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="inputTime"
                               value="${obj.inputTime}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>-->


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
<script type="text/javascript" src="${request.contextPath}/scripts/user/linkman/update.js?_=${randomNum}"></script>
</body>
