package cn.wolfcode.web.modules.orderinfo.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.commons.utils.PoiExportHelper;
import cn.wolfcode.web.commons.utils.SystemCheckUtils;
import cn.wolfcode.web.modules.BaseController;
import cn.wolfcode.web.modules.custinfo.entity.TbCustomer;
import cn.wolfcode.web.modules.custinfo.service.ITbCustomerService;
import cn.wolfcode.web.modules.linkman.entity.TbCustLinkman;
import cn.wolfcode.web.modules.linkman.service.ITbCustLinkmanService;
import cn.wolfcode.web.modules.log.LogModules;
import cn.wolfcode.web.modules.orderinfo.entity.TbOrderInfo;
import cn.wolfcode.web.modules.orderinfo.service.ITbOrderInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.SameUrlData;
import link.ahsj.core.annotations.SysLog;
import link.ahsj.core.annotations.UpdateGroup;
import link.ahsj.core.entitys.ApiModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author hmy
 * @since 2023-06-29
 */
@Controller
@RequestMapping("orderinfo")
public class TbOrderInfoController extends BaseController {

    @Autowired
    private ITbOrderInfoService entityService;

    @Autowired
    private ITbCustomerService customerService;
    @Autowired
    private ITbCustLinkmanService custLinkmanService;

    private static final String LogModule = "TbOrderInfo";

    @GetMapping("/list.html")
    public ModelAndView list(ModelAndView mv) {

        List<TbCustomer> custList = customerService.list(); //拿到企业客户对象中的所有数据

        mv.addObject("custList", custList);
        mv.setViewName("order/orderinfo/list");
        return mv;
    }

    @RequestMapping("/add.html")
    @PreAuthorize("hasAuthority('order:orderinfo:add')")
    public ModelAndView toAdd(ModelAndView mv) {
        List<TbCustomer> list = customerService.list();
        mv.addObject("custList", list);
        mv.setViewName("order/orderinfo/add");
        return mv;
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('order:orderinfo:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {

        List<TbCustomer> custList = customerService.list(); //拿到企业客户对象中的所有数据
        mv.addObject("custList", custList);

        mv.setViewName("order/orderinfo/update");
        mv.addObject("obj", entityService.getById(id));
        mv.addObject("id", id);
        return mv;
    }

    @RequestMapping("list")
    @PreAuthorize("hasAuthority('order:orderinfo:list')")
    public ResponseEntity page(LayuiPage layuiPage, String parameterName, String startTime, String endTime) {
        //检查分页的参数的
        SystemCheckUtils.getInstance().checkMaxPage(layuiPage);
        //分页的对象
        IPage<TbOrderInfo> page = new Page<>(layuiPage.getPage(), layuiPage.getLimit());

        page = entityService.
                lambdaQuery()
                .ge(!StringUtils.isEmpty(startTime), TbOrderInfo::getInputTime, startTime)
                .le(!StringUtils.isEmpty(endTime), TbOrderInfo::getInputTime, endTime)
                .like(!StringUtils.isEmpty(parameterName), TbOrderInfo::getProdName, parameterName)
                .page(page);

        //拿到分页列表
        List<TbOrderInfo> records = page.getRecords();
        //循环分页列表
        records.forEach(item -> {
            String id = item.getCustId();//拿到客户id
            String receiverId = item.getReceiver();
            TbCustomer tbCustomer = customerService.getById(id);//根据客户id查询客户对象
            TbCustLinkman tbCustLinkman = custLinkmanService.getById(receiverId);
            if (tbCustomer != null) {
                item.setCustName(tbCustomer.getCustomerName());//赋值客户名字
                item.setReceiverName(tbCustLinkman.getLinkman());
            }
        });
        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(page));
    }

    @SameUrlData
    @PostMapping("save")
    @SysLog(value = LogModules.SAVE, module = LogModule)
    @PreAuthorize("hasAuthority('order:orderinfo:add')")
    public ResponseEntity<ApiModel> save(@Validated({AddGroup.class}) @RequestBody TbOrderInfo entity) {
        entity.setInputTime(LocalDateTime.now());
        entityService.save(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SameUrlData
    @SysLog(value = LogModules.UPDATE, module = LogModule)
    @PutMapping("update")
    @PreAuthorize("hasAuthority('order:orderinfo:update')")
    public ResponseEntity<ApiModel> update(@Validated({UpdateGroup.class}) @RequestBody TbOrderInfo entity) {
        entityService.updateById(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.DELETE, module = LogModule)
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('order:orderinfo:delete')")
    public ResponseEntity<ApiModel> delete(@PathVariable("id") String id) {
        entityService.removeById(id);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @RequestMapping("export")
    public void export(HttpServletResponse response, String startTime, String endTime, String parameterName) throws Exception {
        //1. 导出的内容
        List<TbOrderInfo> list = entityService
                .lambdaQuery()
                .le(StringUtils.isNotEmpty(startTime), TbOrderInfo::getDeliverTime, startTime)
                .ge(StringUtils.isNotEmpty(endTime), TbOrderInfo::getReceiverName, endTime)
                .like(!StringUtils.isEmpty(parameterName), TbOrderInfo::getProdName, parameterName)//企业名称
                .list();
        //2. 导出前的准备 设置表格标题属性样式的意思
        ExportParams exportParams = new ExportParams();
        /**
         * 参数一: 表格标题属性
         * 参数二: 你需要导出的类的字节码 配合一个注解使用 @Excel(name = 'XXXX')
         * 参数三: 你需要导出的数据
         *
         * 返回回来是一个工作簿
         */
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, TbOrderInfo.class, list);

        //3. 导出 -> IO流  输出流   字节
        PoiExportHelper.exportExcel(response, "企业客户管理", workbook);

    }


}
