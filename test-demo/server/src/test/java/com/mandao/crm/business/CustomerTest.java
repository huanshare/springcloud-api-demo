package com.huanshare.test.business;

import com.alibaba.fastjson.JSON;
import com.huanshare.test.business.dto.CustomerPickingDto;
import com.huanshare.test.business.service.testCustomerService;
import com.huanshare.test.client.common.support.ResponseEntity;
import com.huanshare.test.client.common.vo.LoginUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuhuan on 2018/11/5 14:17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerTest {
    @Resource
    private testCustomerService testCustomerService;

    @Test
    public void customerPicking() {
       /* LoginUser user = new LoginUser();
        user.setUserName("张守婷");
        user.setUserId(3151L);
        CustomerPickingDto customerPickingDto=JSON.parseObject("{\"token\":\"1770550f3cbacb4bbf0eeb787282289afd7fed6aec8aca61afb0f1e0e9847b2f8b9a66e7a11979db6dbda15ce4e87486eb7a539c6ce625a0785a34bb970a7538\",\"professionCode\":\"HJHY\",\"dailyTradingVolume\":null,\"grossProfit\":\"B\",\"cusDemand\":\"客户需求\",\"progress\":\"当天进行\",\"workPlan\":\"工作计划\",\"source\":\"SEARCH\",\"productCodes\":\"CZKZF\",\"grade\":\"\",\"contacts\":[{\"duties\":\"dfa\",\"remarks\":\"\",\"id\":\"2322\",\"email\":\"\",\"telephone\":\"\",\"department\":\"范德萨\",\"name\":\"Admin\"}],\"intelligences\":[{\"myCompanyShort\":\"我司解释\",\"opponentLong\":\"对方优势\",\"price\":\"15\",\"productName\":\"服务\",\"myCompanyLong\":\"我司优势\",\"opponentName\":\"竞争对手\",\"opponentGen\":\"需求\",\"opponentShort\":\"对方劣势\",\"shareNum\":\"15\"}],\"chance\":{\"description\":\"阿而已\",\"subject\":\"BF\",\"customerName\":\"RRRR\",\"typeCode\":\"NEW\",\"productCodeList\":[\"DF\"],\"merchantCode\":\"100020207\",\"marketActivityId\":41,\"chanceName\":\"机会难得\",\"informationCenters\":[],\"originCode\":\"RECOMMEND\",\"merchantName\":\"商户2\",\"userId\":3151,\"userName\":\"张守婷\",\"customerCode\":\"CU01319\",\"flowMonth\":\"15\",\"rate\":\"16\",\"intelligences\":[]},\"subject\":\"BF\",\"customerCode\":\"CU01319\",\"informationCenters\":[{\"id\":\"\",\"subject\":\"BF\",\"customerCode\":\"CU01319\",\"competitor\":\"金正对人\",\"scale\":\"对手回我\"}]}"
        ,CustomerPickingDto.class);
        List<MultipartFile> list =null;
        Integer returnData=testCustomerService.customerPicking(customerPickingDto, user, null);*/

        //System.out.println(testCustomerService.checkCustomerCountFull(3166L,"P6","S"));

    }
}
