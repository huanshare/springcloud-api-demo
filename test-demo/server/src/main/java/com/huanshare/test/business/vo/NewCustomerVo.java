package com.huanshare.test.business.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 新建客户返回数据
 * <p>
 *
 *
 * created on 2018/9/20 11:08
 */
@Data
@ApiModel(description = "新建客户返回数据")
public class NewCustomerVo implements Serializable {
    private static final long serialVersionUID = -7470917291388368616L;
    @ApiModelProperty(value = "客户ID")
    private Long customerId;
    @ApiModelProperty(value = "客户代码")
    private String customerCode;
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    @ApiModelProperty(value = "业务线代码")
    private String subject;
    @ApiModelProperty(value = "业务线描述")
    private String subjectName;

    public NewCustomerVo() {
    }

    public NewCustomerVo(Long customerId, String customerCode, String customerName, String subject, String subjectName) {
        this.customerId = customerId;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.subject = subject;
        this.subjectName = subjectName;
    }
}