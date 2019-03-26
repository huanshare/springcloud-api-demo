package com.huanshare.test.business;

import com.huanshare.test.business.dao.testObtainChanceDao;
import com.huanshare.test.business.dto.ChanceAddDto;
import com.huanshare.test.business.entity.testObtainChance;
import com.huanshare.test.business.service.testChanceService;
import com.huanshare.test.client.common.vo.LoginUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 测试用例
 * <p>
 *
 *
 * created on 2018/9/26 17:13
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BootTest {
    /**
     * @LocalServerPort 提供了 @Value("${local.server.port}") 的代替
     */
    @LocalServerPort
    private int port;

    private URL base;

    @Resource
    private testObtainChanceDao testObtainChanceDao;
    @Autowired
    private testChanceService testChanceService;

    @Before
    public void setUp() throws Exception {
        String url = String.format("http://localhost:%d/", port);
        System.out.println(String.format("port is : [%d]", port));
        this.base = new URL(url);
    }

    @Test
    public void test1() throws Exception {
        testObtainChance testObtainChance = new testObtainChance();
        testObtainChance.setCustomerObtainId(0L);
        testObtainChance.setChanceName("111");
        testObtainChance.setFlowMonth(new BigDecimal("0"));
        testObtainChance.setCustomerCode("");
        testObtainChance.setCustomerName("");
        testObtainChance.setSubject("");
        testObtainChance.setMerchantName("");
        testObtainChance.setMerchantCode("");
        testObtainChance.setUserName("");
        testObtainChance.setUserId(0L);
        testObtainChance.setOriginName("");
        testObtainChance.setOriginCode("");
        testObtainChance.setTypeName("");
        testObtainChance.setTypeCode("");
        testObtainChance.setDescription("");
        testObtainChance.setProductCode("");
        testObtainChance.setRate("");
        testObtainChance.setMarketActivityId(0L);
        testObtainChance.setMarketActivityName("");
        testObtainChance.setCreateTime(new Date());
        testObtainChance.setUpdateTime(new Date());
        testObtainChance.setStatus("");
        testObtainChance.setCreateUserId(0L);
        testObtainChance.setUpdateUserId(0L);
//        testObtainChanceDao.insert(testObtainChance);

    }

    @Test
    public void testCreateChance() throws InterruptedException {
        Set<String> proList = new HashSet<>();
        proList.add("DF");
        proList.add("DK");
        proList.add("DKAPI");
        ChanceAddDto dto = new ChanceAddDto();
        dto.setChanceName("王翔测试新建商机");
        dto.setUserId(2872L);
        dto.setUserName("王翔");
        dto.setSubject("BF");
        dto.setProductCodeList(proList);
        dto.setMerchantName("");
        dto.setMerchantCode("");
        dto.setCustomerCode("CU01151");
        dto.setCustomerName("小额贷款对接客户");
        dto.setTypeCode("OLD");
        dto.setOriginCode("OTHER");
        dto.setFlowMonth(new BigDecimal("0"));
        dto.setRate("11");
        dto.setMarketActivityId(0L);
        dto.setDescription("xxxx");
       /* dto.setIntelligences(Lists.newArrayList());
        dto.setInformationCenters(Lists.newArrayList());*/
        LoginUser loginUser = new LoginUser(2872L, "王翔");
       /* try {
            Long id = testChanceService.insert(dto, loginUser);
            System.out.println("id=" + id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        Thread.sleep(10000);
        System.out.println("测试结束");*/
    }
}
