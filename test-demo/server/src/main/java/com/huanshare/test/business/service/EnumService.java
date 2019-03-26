package com.huanshare.test.business.service;

import com.huanshare.test.client.common.support.enums.LookupTypeEnum;
import com.huanshare.test.client.common.support.vo.EnumVo;

import java.util.List;

/**
 * 本地服务缓存值集服务
 *
 * created on 2018/6/5 20:26
 *
 *
 * @version 1.0.0
 */
public interface EnumService {

    /**
     * 初始化系统内存缓存值集
     */
    void initLookupCodes();

    /**
     * 实时查询系统有效值集信息
     *
     * @param lookupTypeEnum
     * @return
     */
    List<EnumVo> listEnumByCode(LookupTypeEnum lookupTypeEnum);

    /**
     * 获取值集的中文描述
     *
     * @param lookupTypeEnum
     * @param code
     * @return
     */
    String getEnumValue(LookupTypeEnum lookupTypeEnum, String code);
}
