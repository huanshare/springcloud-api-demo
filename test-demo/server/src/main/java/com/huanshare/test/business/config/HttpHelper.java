package com.huanshare.test.business.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 解决body数据只能读取一次问题
 *
 * created on 2018/7/25 10:35
 *
 *
 * @version 1.0.0
 */
public class HttpHelper {
    /**
     * 获取请求Body
     *
     * @param request
     * @return
     */
    private static Logger logger = LoggerFactory.getLogger(HttpHelper.class);

    static String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            logger.warn("getBodyString出现问题！");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("inputStream  close exception", e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.error("reader close exception", e);
                }
            }
        }
        return sb.toString();
    }
}
