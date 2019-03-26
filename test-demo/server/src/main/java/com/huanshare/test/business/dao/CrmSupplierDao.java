package com.huanshare.test.business.dao;

import com.huanshare.test.business.dto.ContactsSupplierQueryDto;
import com.huanshare.test.business.entity.testSupplier;
import com.huanshare.test.client.common.support.base.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description 金融机构--供应商相应的数据操作
 * @Version 1.0
 *
 * @Date 2017/12/11
 * @ProjectName huanshare-test
 * @PackageName com.huanshare.test.server.dao
 */
@Repository
@Mapper
public interface testSupplierDao extends BaseDao<testSupplier, Long> {
    /***
     * 根据编码查询机构
     * @param code
     * @return
     */
    testSupplier selectByCode(String code);

    testSupplier isExitsName(String name);

    testSupplier isExitsByNameAndId(@Param(value = "name") String name, @Param(value = "id") Long id);

    List<testSupplier> selectByParentCode(@Param(value = "parentCode") String parentCode);

    int countByParamAsEdit(@Param(value = "param") testSupplier model);

    List<testSupplier> selectContactsSupplier(@Param(value = "param") ContactsSupplierQueryDto contactsSupplierQueryDto);
}
