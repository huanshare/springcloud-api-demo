package com.huanshare.test.business.output;

import com.huanshare.test.client.common.support.enums.LookupTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description 描述
 * @Version 1.0
 *
 * @Date 2018/6/6
 * @ProjectName test-business
 * @PackageName com.huanshare.test.business.output
 */
@Getter
@Setter
@ApiModel(description = "客户全局模式业务线数据")
public class SubjectOutput {

    @ApiModelProperty(value = "业务线名称")
    private String subjectName;

    @ApiModelProperty(value = "业务线编码")
    private String subjectCode;

    @ApiModelProperty(value = "产品Code")
    private String productCode;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "业务线状态")
    private String busStatus;

    @ApiModelProperty(value = "业务线所有人")
    private Long userId;

    @ApiModelProperty(value = "是否被认领")
    private Integer isPicked;

    @ApiModelProperty(value = "是否被冻结 Y-冻结   N/null - 未冻结")
    private String isFrozenFlag;

    @ApiModelProperty(value = "是否签约 Y-已签约   N/null - 未签约")
    private String isSignFlag;

    public String getProductName() {
        if (StringUtils.isNotBlank(this.productCode)) {
            return LookupTypeEnum.getEnumName(LookupTypeEnum.test_PRODUCT, this.productCode);
        }
        return "";
    }

}
