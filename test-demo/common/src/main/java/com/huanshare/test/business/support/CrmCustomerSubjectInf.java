package com.huanshare.test.business.support;

import lombok.Getter;
import lombok.Setter;

/**
 * 查询客户的权限
 *
 * created on 2018/7/4 10:42
 *
 *
 * @version 1.0.0
 */
@Getter
@Setter
public class testCustomerSubjectInf {
    /**
     * 业务线负者人
     */
    private Long subjectSaler;

    /**
     * 集团负者人
     */
    private Long groupUserId;

    //业务线
    private String subject;

    private String professionCode;
}
