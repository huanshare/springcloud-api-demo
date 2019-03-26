package com.huanshare.test.business.input;

import com.huanshare.test.client.common.support.dto.BaseRemoteInput;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 *
 * @description <p> 流程实例ID </p>
 * @date created in 2018/8/16 14:56
 */
@Getter
@Setter
@ToString
public class ProcessInstanceIdInput extends BaseRemoteInput {
    private static final long serialVersionUID = 2183073781673297860L;

    /**
     * 流程实例ID
     */
    @NotNull(message = "流程实例ID不能为空")
    private String processInstanceId;
}
