package com.huanshare.test.business.dto;

import com.huanshare.test.client.common.support.base.BaseRequest;
import lombok.Data;

/**
 * 重新激活输单的销售机会请求体
 *
 * created on 2017/11/23 17:32
 *
 *
 * @version 1.0.0
 */
@Data
public class ChanceActivateDto extends BaseRequest {
    private static final long serialVersionUID = -1299240391904372213L;
    private Long chanceId;
}
