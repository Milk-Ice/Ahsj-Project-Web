package cn.wolfcode.web.modules.contractinfo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 合同信息
 * </p>
 *
 * @author hmy
 * @since 2023-06-29
 */
@Data
public class TbContract implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 客户id
     */
    private String custId;

    @TableField(exist = false)
    private String custName;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 合同编码
     */
    private String contractCode;

    /**
     * 合同金额
     */
    private Integer amounts;

    /**
     * 合同生效开始时间
     */
    private LocalDate startDate;

    /**
     * 合同生效结束时间
     */
    private LocalDate endDate;

    /**
     * 合同内容
     */
    private String content;

    /**
     * 是否盖章确认 0 否 1 是
     */
    private Integer affixSealStatus;

    /**
     * 审核状态 0 未审核 1 审核通过 -1 审核不通过
     */
    private Integer auditStatus;

    /**
     * 是否作废 1 作废 0 在用
     */
    private Integer nullifyStatus;

    /**
     * 录入人
     */
    private String inputUser;

    /**
     * 录入时间
     */
    private LocalDateTime inputTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

}
