package com.huanshare.test.business.web;

import com.huanshare.Response;
import com.huanshare.dfs.client.DfsClient;
import com.huanshare.dfs.client.core.DfsException;
import com.huanshare.dfs.client.enums.ErrorCode;
import com.huanshare.dfs.client.enums.Operation;
import com.huanshare.dfs.client.model.CommandResDTO;
import com.huanshare.dfs.client.model.QueryReqDTO;
import com.huanshare.dfs.client.util.SocketUtil;
import com.huanshare.test.business.dto.CustomerModelQueryDto;
import com.huanshare.test.business.entity.testCommonUpload;
import com.huanshare.test.business.output.CustomerModelOutput;
import com.huanshare.test.business.service.testCommonUploadService;
import com.huanshare.test.business.service.testCustomerService;
import com.huanshare.test.business.service.EnumService;
import com.huanshare.test.client.common.support.ResponseEntity;
import com.huanshare.test.client.common.support.base.controller.BaseController;
import com.huanshare.test.client.common.util.PageResult;
import com.huanshare.test.client.common.vo.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Description 描述
 * @Version 1.0
 *
 * @Date 2018/6/5
 * @ProjectName test-business
 * @PackageName com.huanshare.test.business.web
 */
@Api(tags = "公共查询管理")
@RestController
@RequestMapping("common")
@Slf4j
public class CommonController extends BaseController {

    @Resource
    private testCustomerService testCustomerService;

    @Resource
    private EnumService enumService;

    @Resource
    private testCommonUploadService testCommonUploadService;

    @ApiOperation(notes = "公共查询客户", value = "公共查询客户")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功")
    })
    @PostMapping("/customer")
    public ResponseEntity<PageResult<CustomerModelOutput>> listCustomerModel(HttpServletRequest request,
                                                                             @RequestBody CustomerModelQueryDto customerModelQueryDto) {
        LoginUser user = getLoginUser(request);
        log.info("--------------用户：【{}】正在新建客户信息-------", user.getUserName());
        PageResult<CustomerModelOutput> result = testCustomerService.listCustomerModel(customerModelQueryDto);
        return ResponseEntity.ok(result);
    }

    /****
     * 文件下载
     */
    @ApiOperation(notes = "下载文件", value = "下载文件")
    @GetMapping(value = "download")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, @PathParam("fid") Long fid) {

        testCommonUpload testCommonUpload = testCommonUploadService.selectByFid(fid);
        QueryReqDTO reqDTO = new QueryReqDTO();
        reqDTO.setFileId(Long.valueOf(testCommonUpload.getDfsId()));
        reqDTO.setOperation(Operation.QUERY);
        log.info("downloadExcel QueryReqDTO:" + ToStringBuilder.reflectionToString(reqDTO));

        Response res = SocketUtil.sendMessage(reqDTO);
        if (!res.isSuccess()) {
            throw new DfsException(ErrorCode.GET_FILE_INFO_ERROR, res.getErrorMsg());
        }
        CommandResDTO resDTO = (CommandResDTO) res.getResult();

        String fileName = resDTO.getFileName();
        String downloadUrl = "/data" + "/";
        DfsClient.download(reqDTO, downloadUrl);
        response.reset(); // 清空缓存区
        log.info("下载文件名称：{}", testCommonUpload.getFileName());
        InputStream in = null;
        OutputStream out = null;
        try {
            // 文件名称的编码
            // 设置文件头
            response.setHeader("Content-Disposition", "attachment;fileName=" +
                    new String(testCommonUpload.getFileName().getBytes("Gb2312"),
                            "ISO-8859-1"));
            File file = new File(downloadUrl + "/" + fileName);
            in = new FileInputStream(file);
            int len;
            byte[] buffer = new byte[1024];
            out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            log.error("call requestQueryManager downloadFile Exception:{}", e.getMessage());
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }

    }

}
