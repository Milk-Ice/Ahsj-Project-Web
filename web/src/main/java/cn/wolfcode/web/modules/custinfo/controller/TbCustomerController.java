package cn.wolfcode.web.modules.custinfo.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.CityUtils;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.commons.utils.PoiExportHelper;
import cn.wolfcode.web.commons.utils.SystemCheckUtils;
import cn.wolfcode.web.modules.BaseController;
import cn.wolfcode.web.modules.linkman.entity.TbCustLinkman;
import cn.wolfcode.web.modules.linkman.service.ITbCustLinkmanService;
import cn.wolfcode.web.modules.log.LogModules;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import cn.wolfcode.web.modules.sys.form.LoginForm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import cn.wolfcode.web.modules.custinfo.entity.TbCustomer;
import cn.wolfcode.web.modules.custinfo.service.ITbCustomerService;


import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.SameUrlData;
import link.ahsj.core.annotations.SysLog;
import link.ahsj.core.annotations.UpdateGroup;
import link.ahsj.core.entitys.ApiModel;
import link.ahsj.core.exception.AppServerException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 叶老师
 * @since 2023-06-25
 */
@Controller
@RequestMapping("custinfo")
public class TbCustomerController extends BaseController {

    @Autowired
    private ITbCustomerService entityService;

    @Autowired
    private ITbCustLinkmanService custLinkmanService;

    private static final String LogModule = "TbCustomer";

    @GetMapping("/list.html")
    public ModelAndView list(ModelAndView mv) {
        //组装省份信息
        mv.addObject("citys", CityUtils.citys); //最简单的理解就是把数据放在request域当中
        mv.setViewName("cust/custinfo/list");

        return mv;
    }

    @RequestMapping("/add.html")
    @PreAuthorize("hasAuthority('cust:custinfo:add')")
    public ModelAndView toAdd(ModelAndView mv) {

        //组装省份信息
        mv.addObject("citys", CityUtils.citys); //最简单的理解就是把数据放在request域当中

        mv.setViewName("cust/custinfo/add");
        return mv;
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('cust:custinfo:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {
        //组装省份信息
        mv.addObject("citys", CityUtils.citys); //最简单的理解就是把数据放在request域当中

        mv.setViewName("cust/custinfo/update");
        mv.addObject("obj", entityService.getById(id));
        mv.addObject("id", id);
        return mv;
    }

    //http://localhost:8888/nebula-web/custinfo/list?     page=1&limit=10&parameterName=1234
    @RequestMapping("list")
    @PreAuthorize("hasAuthority('cust:custinfo:list')")
    public ResponseEntity page(LayuiPage layuiPage,String parameterName,String province,String openStatus) {

        System.out.println(parameterName);

        SystemCheckUtils.getInstance().checkMaxPage(layuiPage);

        //分页对象
        IPage page = new Page<>(layuiPage.getPage(), layuiPage.getLimit());

        //具体数据分页
        page=entityService
                .lambdaQuery()
                .eq(StringUtils.isNotEmpty(openStatus),TbCustomer::getOpenStatus,openStatus)//经营状态
                .eq(StringUtils.isNotEmpty(province),TbCustomer::getProvince,province) //所属省份
                .like(!StringUtils.isEmpty(parameterName),TbCustomer::getCustomerName,parameterName)//企业名称
                .page(page);
       /* for (TbCustomer tbcustomer : records) {

        }*/
        List<TbCustomer> records = page.getRecords();
        //拿到分页列表
        records.forEach(item->{
            String provinceCode = item.getProvince();//拿到省份的编码
            String cityValue = CityUtils.getCityValue(provinceCode);//通过省份编码获取省份对应的名字
            item.setProvinceName(cityValue);

        });


        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(page));


    }

    @SameUrlData
    @PostMapping("save")
    @SysLog(value = LogModules.SAVE, module =LogModule)
    @PreAuthorize("hasAuthority('cust:custinfo:add')")
    public ResponseEntity<ApiModel> save(@Validated({AddGroup.class}) @RequestBody
                                                     TbCustomer entity,
                                         HttpServletRequest request
    ) {
        // 录入时间
        entity.setInputTime(LocalDateTime.now());//获取的当前时间
        // 录入人
        SysUser sysUser =(SysUser) request.getSession().getAttribute(LoginForm.LOGIN_USER_KEY);
        entity.setInputUserId(sysUser.getUserId());




        //1. 拿到从前端传递过来的企业名称
        //2. 通过得到的名称进行与数据库比较
        int count = entityService.lambdaQuery()
                .eq(StringUtils.isNotEmpty(entity.getCustomerName()),TbCustomer::getCustomerName,entity.getCustomerName())
                .count();
        //3. 如果受影响的行数大于0 就代表 已经存在 小于1 说明 可以添加
        if(count > 0){
                throw new AppServerException("该企业名称已经重复");
        }


        entityService.save(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SameUrlData
    @SysLog(value = LogModules.UPDATE, module = LogModule)
    @PutMapping("update")
    @PreAuthorize("hasAuthority('cust:custinfo:update')")
    public ResponseEntity<ApiModel> update(@Validated({UpdateGroup.class}) @RequestBody TbCustomer entity) {
        //修改时间
        entity.setUpdateTime(LocalDateTime.now());


        //1. 拿到从前端传递过来的企业名称
        //2. 通过得到的名称进行与数据库比较
        int count = entityService.lambdaQuery()
                .eq(StringUtils.isNotEmpty(entity.getCustomerName()),TbCustomer::getCustomerName,entity.getCustomerName())//等于
                .ne(StringUtils.isNotEmpty(entity.getId()),TbCustomer::getId,entity.getId())//不等于
                .count();
        //3. 如果受影响的行数大于0 就代表 已经存在 小于1 说明 可以添加
        if(count > 0){
            throw new AppServerException("该企业名称已经重复");
        }


        entityService.updateById(entity);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.DELETE, module = LogModule)
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('cust:custinfo:delete')")
    public ResponseEntity<ApiModel> delete(@PathVariable("id") String id) {

        //1. 发送一条sql语句 查询 当前id在客户联系人旗下是否还有数据 有的话进行删除
        //select * from tb_cust_linkman where cust_id = 'fa4b46bd7b38a3597525a18e2bba7600'
        custLinkmanService.lambdaUpdate()
                .eq(StringUtils.isNotEmpty(id), TbCustLinkman::getCustId,id).remove();


        entityService.removeById(id);
        return ResponseEntity.ok(ApiModel.ok());
    }

    @RequestMapping("export")
    public void export(HttpServletResponse response,String openStatus,String province,String parameterName) throws Exception{
        //1. 导出的内容
        List<TbCustomer> list = entityService
                .lambdaQuery()
                .eq(StringUtils.isNotEmpty(openStatus),TbCustomer::getOpenStatus,openStatus)//经营状态
                .eq(StringUtils.isNotEmpty(province),TbCustomer::getProvince,province) //所属省份
                .like(!StringUtils.isEmpty(parameterName),TbCustomer::getCustomerName,parameterName)//企业名称
                .list();
        //2. 导出前的准备 设置表格标题属性样式的意思
        ExportParams exportParams=new ExportParams();
        /**
         * 参数一: 表格标题属性
         * 参数二: 你需要导出的类的字节码 配合一个注解使用 @Excel(name = 'XXXX')
         * 参数三: 你需要导出的数据
         *
         * 返回回来是一个工作簿
         */
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, TbCustomer.class, list);

        //3. 导出 -> IO流  输出流   字节
        PoiExportHelper.exportExcel(response,"企业客户管理",workbook);

    }


}
