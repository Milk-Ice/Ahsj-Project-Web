package cn.wolfcode.web.modules.linkman.controller;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.commons.utils.SystemCheckUtils;
import cn.wolfcode.web.modules.BaseController;
import cn.wolfcode.web.modules.custinfo.entity.TbCustomer;
import cn.wolfcode.web.modules.custinfo.service.ITbCustomerService;
import cn.wolfcode.web.modules.log.LogModules;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import cn.wolfcode.web.modules.linkman.entity.TbCustLinkman;
import cn.wolfcode.web.modules.linkman.service.ITbCustLinkmanService;


import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.SameUrlData;
import link.ahsj.core.annotations.SysLog;
import link.ahsj.core.annotations.UpdateGroup;
import link.ahsj.core.entitys.ApiModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author 叶老师
 * @since 2023-06-27
 */
@Controller
@RequestMapping("linkman")
public class TbCustLinkmanController extends BaseController {

    @Autowired
    private ITbCustLinkmanService entityService;


    @Autowired
    private ITbCustomerService customerService;

    private static final String LogModule = "TbCustLinkman";

    @GetMapping("/list.html")
    public ModelAndView list(ModelAndView mv) {

        List<TbCustomer> custList = customerService.list(); //拿到企业客户对象中的所有数据

        mv.addObject("custList", custList);
        mv.setViewName("user/linkman/list");
        return mv;
    }

    @RequestMapping("/add.html")
    @PreAuthorize("hasAuthority('user:linkman:add')")
    public ModelAndView toAdd(ModelAndView mv) {

        List<TbCustomer> custList = customerService.list(); //拿到企业客户对象中的所有数据
        mv.addObject("custList",custList);

        mv.setViewName("user/linkman/add");
        return mv;
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('user:linkman:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {

        List<TbCustomer> custList = customerService.list(); //拿到企业客户对象中的所有数据
        mv.addObject("custList",custList);

        mv.setViewName("user/linkman/update");
        mv.addObject("obj", entityService.getById(id));
        mv.addObject("id", id);
        return mv;
    }

    @RequestMapping("list")
    @PreAuthorize("hasAuthority('user:linkman:list')")
    public ResponseEntity page(LayuiPage layuiPage,String parameterName,String custId) {
        //检查分页的参数的
        SystemCheckUtils.getInstance().checkMaxPage(layuiPage);
        //分页的对象
        IPage page = new Page<>(layuiPage.getPage(), layuiPage.getLimit());

        page= entityService.
                lambdaQuery()
                .eq(StringUtils.isNotEmpty(custId),TbCustLinkman::getCustId,custId)  //企业名称
                .like(!StringUtils.isEmpty(parameterName),TbCustLinkman::getLinkman,parameterName) //联系人姓名
                .or()
                .like(!StringUtils.isEmpty(parameterName),TbCustLinkman::getPhone,parameterName) //联系人电话
                .page(page);

        //拿到分页列表
        List<TbCustLinkman> records = page.getRecords();
        //循环分页列表
        records.forEach(item->{
            String id = item.getCustId();//拿到客户id
            TbCustomer tbCustomer = customerService.getById(id);//根据客户id查询客户对象
            if(tbCustomer!=null){
                item.setCustName(tbCustomer.getCustomerName());//赋值客户名字
            }


        });


        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(page));
    }

    @SameUrlData
    @PostMapping("save")
    @SysLog(value = LogModules.SAVE, module =LogModule)
    @PreAuthorize("hasAuthority('user:linkman:add')")
    public ResponseEntity<ApiModel> save(@Validated({AddGroup.class}) @RequestBody TbCustLinkman entity) {
        entityService.save(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SameUrlData
    @SysLog(value = LogModules.UPDATE, module = LogModule)
    @PutMapping("update")
    @PreAuthorize("hasAuthority('user:linkman:update')")
    public ResponseEntity<ApiModel> update(@Validated({UpdateGroup.class}) @RequestBody TbCustLinkman entity) {
        entityService.updateById(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.DELETE, module = LogModule)
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('user:linkman:delete')")
    public ResponseEntity<ApiModel> delete(@PathVariable("id") String id) {
        entityService.removeById(id);
        return ResponseEntity.ok(ApiModel.ok());
    }


    @RequestMapping("queryByCustIdList")
    public ResponseEntity<ApiModel> queryByCustIdList(String custId){


        List<TbCustLinkman> tbCustLinkmen = entityService.lambdaQuery()
                .eq(StringUtils.isNotEmpty(custId), TbCustLinkman::getCustId, custId)
                .list();
        return ResponseEntity.ok(ApiModel.data(tbCustLinkmen));

    }


}
