package cn.wolfcode.web.modules.orderinfo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hmy
 * @since 2023-06-29
 */
@Data
public class TbOrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 所属客户id
     */
    private String custId;

    @TableField(exist = false)
    private String custName;

    /**
     * 产品名称
     */
    @Excel(name = "产品名称")
    private String prodName;

    /**
     * 产品数量
     */
    @Excel(name = "产品数量")
    private Integer amounts;

    /**
     * 产品价格
     */
    @Excel(name = "产品价格")
    private Integer price;

    /**
     * 状态 0 未发货 1 已发货 2 已收货 3 确认收货
     */
    private Integer status;

    /**
     * 收货人
     */
    private String receiver;

    @TableField(exist = false)
    private String receiverName;


    /**
     * 收货人电话
     */
    private String linkPhone;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 物流
     */
    private String logistcs;

    /**
     * 物流单号
     */
    private String logisticsCode;

    /**
     * 发货时间
     */
    private LocalDate deliverTime;

    /**
     * 收货时间
     */
    private LocalDate receiveTime;

    /**
     * 录入时间
     */
    private LocalDateTime inputTime;


}
