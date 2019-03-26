package com.huanshare.test.business.service.impl;

import com.huanshare.test.business.provider.UserProvider;
import com.huanshare.test.business.service.EnumService;
import com.huanshare.test.client.common.support.constant.testConstant;
import com.huanshare.test.client.common.support.enums.LookupTypeEnum;
import com.huanshare.test.client.common.support.vo.EnumVo;
import com.huanshare.test.client.common.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;

/**
 * 本服务缓存值集实现
 *
 * created on 2018/6/5 20:29
 *
 *
 * @version 1.0.0
 */
@Slf4j
@Service
public class EnumServiceImpl implements EnumService {
    @Resource
    private UserProvider userProvider;


    @Override
    public void initLookupCodes() {
        log.info("开始缓存内存值集信息");
        Map<String, List<EnumVo>> codes = userProvider.allEnums();
        if (null != codes) {
            if (testConstant.LOOKUP_MAP.size() > 0) {
                testConstant.LOOKUP_MAP.clear();
                log.info("--------------LOOKUP_MAP 已重置，重新加载...------------------");
            }
            codes.forEach(testConstant.LOOKUP_MAP::put);
            testConstant.LOOKUP_MAP.forEach((k, v) -> {
                String enums = v.stream().map(code -> code.getEnumCode() + ":" + code.getEnumName()).collect((joining(",")));
                log.info("----------- key：{}，enums =：{} -----------", k, enums);
            });
        }
        log.info("内存缓存值集信息加载完毕");
    }

    @Override
    public List<EnumVo> listEnumByCode(LookupTypeEnum lookupTypeEnum) {
        List<EnumVo> list = userProvider.selectEnums(lookupTypeEnum.getCode());
        list.forEach(enumVo -> {
            if (enumVo.getInvalidTime() == null) {
                enumVo.setInvalidTime(new Date());
            }
        });
        return list;
    }

    @Override
    public String getEnumValue(LookupTypeEnum lookupTypeEnum, String code) {
        List<EnumVo> values = this.listEnumByCode(lookupTypeEnum);
        if (!CollectionUtils.isEmpty(values)) {
            for (EnumVo value : values) {
                if (null != value && StringUtils.equals(value.getEnumCode(), code)) {
                    return value.getEnumName();
                }
            }
        }
        return "";
    }
}
