package com.huanshare.test.business.provider;

import com.alibaba.fastjson.JSON;
import com.huanshare.test.client.common.support.ResponseEntity;
import com.huanshare.test.client.common.support.dto.BaseRemoteInput;
import com.huanshare.test.client.common.support.enums.GenCodeEnum;
import com.huanshare.test.client.common.support.exception.testServiceException;
import com.huanshare.test.client.common.support.msgcode.MsgCode;
import com.huanshare.test.client.common.support.vo.EnumVo;
import com.huanshare.test.client.common.vo.LoginUser;
import com.huanshare.test.user.client.UserClient;
import com.huanshare.test.user.input.CheckIndustryInput;
import com.huanshare.test.user.input.CheckUserCusSubjectAuth;
import com.huanshare.test.user.input.UserIdInput;
import com.huanshare.test.user.output.BigCustomerUserOutput;
import com.huanshare.test.user.output.UserIndustryOutput;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 商机
 *
 * created on 2018/6/13 16:36
 *
 *
 * @version 1.0.0
 */
@Slf4j
@Service("userProvider")
public class UserProvider {

    @Resource
    private UserClient userClient;

    public Boolean checkIndustry(Long userId, String professionCode, String subject) {
        CheckIndustryInput input = new CheckIndustryInput();
        input.setUserId(userId);
        input.setProfessionCode(professionCode);
        input.setSubject(subject);
        return checkIndustry(input);
    }

    public Boolean checkIndustry(CheckIndustryInput input) {
        input.setTraceLogId(MDC.get(SystemMarker.TRACE_LOG_ID));
        ResponseEntity<Boolean> result = userClient.checkIndustry(input);
        if (!result.isSuccess()) {
            log.error("userClient.checkIndustry input:【{}】 error:【{}】}", JSON.toJSONString(input), result.getMessage());
            throw new testServiceException(MsgCode.CALL_REMOTE_SERVICE_EXCEPTION);
        }
        return result.getData();
    }
}
