package cn.wolfcode.web.modules.custinfo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import link.ahsj.core.annotations.AddGroup;
import link.ahsj.core.annotations.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 客户信息
 * </p>
 *
 * @author 叶老师
 * @since 2023-06-25
 */
@Data
public class TbCustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 企业名称
     */
    @NotBlank(message = "企业名称不能为空",groups = {AddGroup.class, UpdateGroup.class})
    @Length(max = 10,message = "企业名称不能超过10个字",groups = {AddGroup.class, UpdateGroup.class})
    @Excel(name = "企业名称")
    private String customerName;

    /**
     * 法定代表人
     */
    @Excel(name = "法定代表人")
    private String legalLeader;

    /**
     * 成立时间
     */
    private LocalDate registerDate;

    /**
     * 经营状态, 0 开业、1 注销、2 破产
     */
    @Excel(name = "经营状态",replace = {"开业_0","注销_1","破产_2"})
    private Integer openStatus;

    /**
     * 所属地区省份
     */
    private String province;


    /**
     * 所属地区省份
     */
    @TableField(exist = false) //代表表中不存在该字段 简单理解就是 忽略表中有该字段
    private String provinceName;


    /**
     * 注册资本,(万元)
     */
    private String regCapital;

    /**
     * 所属行业
     */
    private String industry;

    /**
     * 经营范围
     */
    private String scope;

    /**
     * 注册地址
     */
    private String regAddr;

    /**
     * 录入时间
     */
    private LocalDateTime inputTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 录入人
     */
    private String inputUserId;


}
