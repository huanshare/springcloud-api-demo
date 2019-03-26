package com.huanshare.test.business.client;

import com.huanshare.test.business.entity.testChance;
import com.huanshare.test.business.entity.testProcessChance;
import com.huanshare.test.business.input.AuditChanceInput;
import com.huanshare.test.business.input.GetChanceInput;
import com.huanshare.test.client.common.support.ResponseEntity;
import com.huanshare.test.client.common.support.msgcode.MsgCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 商机内部调用接口
 *
 * created on 2018/6/14 11:11
 *
 *
 * @version 1.0.0
 */
@FeignClient(name = "business", fallback = ChanceClient.ChanceClientFallback.class)
public interface ChanceClient {
    @PostMapping("/api/chance/get")
    ResponseEntity<testChance> selectById(@RequestBody GetChanceInput input);

    /**
     * test
     *
     * @param input
     * @return
     */
    @PostMapping("/api/chance/disagree")
    ResponseEntity<Boolean> disagree(@RequestBody AuditChanceInput input);

    /**
     * 商机审批同意
     *
     * @param input
     * @return
     */
    @PostMapping("/api/chance/agree")
    ResponseEntity<Boolean> agree(@RequestBody AuditChanceInput input);

    /**
     * 商机创建拒绝
     *
     * @param input
     * @return
     */
    @PostMapping("/api/chance/disagreeCreate")
    ResponseEntity<Boolean> disagreeCreate(@RequestBody AuditChanceInput input);

    /**
     * 商机创建同意
     *
     * @param input
     * @return
     */
    @PostMapping("/api/chance/agreeCreate")
    ResponseEntity<Boolean> agreeCreate(@RequestBody AuditChanceInput input);

    /**
     * 获取流程申请中的缓存商机信息
     *
     * @param input
     * @return
     */
    @PostMapping("/api/chance/getProcessChance")
    ResponseEntity<testProcessChance> selectProcessChanceById(@RequestBody GetChanceInput input);

    @Component
    @Slf4j
    static class ChanceClientFallback implements ChanceClient {

        @Override
        public ResponseEntity<testChance> selectById(GetChanceInput input) {
            log.error("ChanceClient.selectById error args:{}", input.toString());
            return ResponseEntity.code(MsgCode.CALL_REMOTE_SERVICE_EXCEPTION).build();
        }

        @Override
        public ResponseEntity<Boolean> disagree(AuditChanceInput input) {
            log.error("ChanceClient.disagree error args:{}", input.toString());
            return ResponseEntity.code(MsgCode.CALL_REMOTE_SERVICE_EXCEPTION).build();
        }

        @Override
        public ResponseEntity<Boolean> agree(AuditChanceInput input) {
            log.error("ChanceClient.agree error args:{}", input.toString());
            return ResponseEntity.code(MsgCode.CALL_REMOTE_SERVICE_EXCEPTION).build();
        }

        @Override
        public ResponseEntity<Boolean> disagreeCreate(AuditChanceInput input) {
            log.error("ChanceClient.disagreeCreate error args:{}", input.toString());
            return ResponseEntity.code(MsgCode.CALL_REMOTE_SERVICE_EXCEPTION).build();
        }

        @Override
        public ResponseEntity<Boolean> agreeCreate(AuditChanceInput input) {
            log.error("ChanceClient.agreeCreate error args:{}", input.toString());
            return ResponseEntity.code(MsgCode.CALL_REMOTE_SERVICE_EXCEPTION).build();
        }

        @Override
        public ResponseEntity<testProcessChance> selectProcessChanceById(GetChanceInput input) {
            log.error("ChanceClient.selectProcessChanceById error args:{}", input.toString());
            return ResponseEntity.code(MsgCode.CALL_REMOTE_SERVICE_EXCEPTION).build();
        }
    }
}