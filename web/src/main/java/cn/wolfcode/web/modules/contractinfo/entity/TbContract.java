package cn.wolfcode.web.modules.contractinfo.entity;

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
public class TbContract implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 客户id
     */
    private String custId;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }
    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }
    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }
    public Integer getAmounts() {
        return amounts;
    }

    public void setAmounts(Integer amounts) {
        this.amounts = amounts;
    }
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Integer getAffixSealStatus() {
        return affixSealStatus;
    }

    public void setAffixSealStatus(Integer affixSealStatus) {
        this.affixSealStatus = affixSealStatus;
    }
    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }
    public Integer getNullifyStatus() {
        return nullifyStatus;
    }

    public void setNullifyStatus(Integer nullifyStatus) {
        this.nullifyStatus = nullifyStatus;
    }
    public String getInputUser() {
        return inputUser;
    }

    public void setInputUser(String inputUser) {
        this.inputUser = inputUser;
    }
    public LocalDateTime getInputTime() {
        return inputTime;
    }

    public void setInputTime(LocalDateTime inputTime) {
        this.inputTime = inputTime;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "TbContract{" +
            "id=" + id +
            ", custId=" + custId +
            ", contractName=" + contractName +
            ", contractCode=" + contractCode +
            ", amounts=" + amounts +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", content=" + content +
            ", affixSealStatus=" + affixSealStatus +
            ", auditStatus=" + auditStatus +
            ", nullifyStatus=" + nullifyStatus +
            ", inputUser=" + inputUser +
            ", inputTime=" + inputTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
