package cn.wolfcode.web.modules.contractinfo.controller;

import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.commons.utils.SystemCheckUtils;
import cn.wolfcode.web.modules.BaseController;
import cn.wolfcode.web.modules.contractinfo.entity.TbContract;
import cn.wolfcode.web.modules.contractinfo.service.ITbContractService;
import cn.wolfcode.web.modules.custinfo.entity.TbCustomer;
import cn.wolfcode.web.modules.custinfo.service.ITbCustomerService;
import cn.wolfcode.web.modules.log.LogModules;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import cn.wolfcode.web.modules.sys.form.LoginForm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.SameUrlData;
import link.ahsj.core.annotations.SysLog;
import link.ahsj.core.annotations.UpdateGroup;
import link.ahsj.core.entitys.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author hmy
 * @since 2023-06-29
 */
@Controller
@RequestMapping("contractinfo")
public class TbContractController extends BaseController {

    @Autowired
    private ITbContractService entityService;
    @Autowired
    private ITbCustomerService customerService;

    private static final String LogModule = "TbContract";

    @GetMapping("/list.html")
    public String list() {
        return "contract/contractinfo/list";
    }

    @RequestMapping("/add.html")
    @PreAuthorize("hasAuthority('contract:contractinfo:add')")
    public ModelAndView toAdd(ModelAndView mv) {
        List<TbCustomer> custList = customerService.list(); //拿到企业客户对象中的所有数据
        mv.addObject("custList",custList);
        mv.setViewName("contract/contractinfo/add");
        return mv;
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('contract:contractinfo:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {
        List<TbCustomer> custList = customerService.list(); //拿到企业客户对象中的所有数据
        mv.addObject("custList",custList);
        mv.setViewName("contract/contractinfo/update");
        mv.addObject("obj", entityService.getById(id));
        mv.addObject("id", id);
        return mv;
    }

    @RequestMapping("list")
    @PreAuthorize("hasAuthority('contract:contractinfo:list')")
    public ResponseEntity page(LayuiPage layuiPage) {
        SystemCheckUtils.getInstance().checkMaxPage(layuiPage);
        IPage page = new Page<>(layuiPage.getPage(), layuiPage.getLimit());
        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(entityService.page(page)));
    }

    @SameUrlData
    @PostMapping("save")
    @SysLog(value = LogModules.SAVE, module =LogModule)
    @PreAuthorize("hasAuthority('contract:contractinfo:add')")
    public ResponseEntity<ApiModel> save(@Validated({AddGroup.class}) @RequestBody TbContract entity,HttpServletRequest request) {
        // 录入时间
        entity.setInputTime(LocalDateTime.now());//获取的当前时间
        // 录入人
        SysUser sysUser =(SysUser) request.getSession().getAttribute(LoginForm.LOGIN_USER_KEY);
        entity.setInputUser(sysUser.getUserId());
        entityService.save(entity);
        return ResponseEntity.ok(ApiModel.ok());

    }

    @SameUrlData
    @SysLog(value = LogModules.UPDATE, module = LogModule)
    @PutMapping("update")
    @PreAuthorize("hasAuthority('contract:contractinfo:update')")
    public ResponseEntity<ApiModel> update(@Validated({UpdateGroup.class}) @RequestBody TbContract entity) {

        entityService.updateById(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.DELETE, module = LogModule)
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('contract:contractinfo:delete')")
    public ResponseEntity<ApiModel> delete(@PathVariable("id") String id) {
        entityService.removeById(id);
        return ResponseEntity.ok(ApiModel.ok());
    }

}
