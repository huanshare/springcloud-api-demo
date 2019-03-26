package com.huanshare.test.business.param;

import com.huanshare.test.business.entity.testCustomer;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description 描述
 * @Version 1.0
 *
 * @Date 2018/6/6
 * @ProjectName test-business
 * @PackageName com.huanshare.test.business
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerSubjectQueryParam extends testCustomer {

    //主体
    private String subject;

    //模糊查询
    private String keywords;

    //所有数据
    private Integer all;

    //一周内掉公海的数据
    private Integer isWeekBack;

    //已签约的客户
    private Integer isSign;

    //未认领的客户
    private Integer isNoPicked;

    //已认领未签约
    private Integer isPickedNoSign;


}
