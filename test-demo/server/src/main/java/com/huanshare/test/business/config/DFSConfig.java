package com.huanshare.test.business.config;


import com.huanshare.dfs.client.core.DfsConfig;
import com.huanshare.dfs.client.util.FastDFSUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.annotation.PostConstruct;

/**
 * dfs初始化配置
 * User: 香克斯 Date:2016/6/13 ProjectName: huanshare-admin Version: 1.0
 */
@Slf4j
@Setter
@Getter
@ToString
@Lazy(false)
@Configuration
public class DFSConfig {


    /**
     * zookeeper 服务地址
     */
    @Value("${dfs.zookeeper}")
    private String zookeeperAddress;

    /**
     * 服务连接超时时间
     */
    private int connectTimeout = 30000;

    /**
     * DFS tracker 地址列表
     */
    @Value("${dfs.trackers}")
    private String trackerAdds;


    /**
     * DFS tracker http 端口
     */

    private int trackerHttpPort;

    /**
     * DFS http 服务地址
     */
    private String httpServer;

    /**
     * DFS 密钥
     */
    private String secretKey = "1qazXsw28080";

    /**
     * DFS网络超时时间
     */
    private int networkTimeout = 30000;

    /**
     * DFS 最大连接数
     */
    private int maxIdle = 50;

    /**
     * DFS 最小连接数
     */
    private int minIdle = 1;

    /**
     * DFS 总连接数
     */
    private int maxTotal = 50;

    /**
     * DFS 临时文件目录
     */
    private String uploadTempDir;


    /**
     * 初始化dfs配置
     */
    @PostConstruct
    public void init() {
        log.info("dfs 系统参数初始化开始 ........");
        log.info("connectTimeout:{}", connectTimeout);
        log.info("httpServer:{}", httpServer);
        log.info("maxIdle:{}", maxIdle);
        log.info("minIdle:{}", minIdle);
        log.info("maxTotal:{}", maxTotal);
        log.info("networkTimeout:{}", networkTimeout);
        log.info("secretKey:{}", secretKey);
        log.info("trackerAdds:{}", trackerAdds);
        log.info("uploadTempDir:{}", uploadTempDir);
        log.info("trackerHttpPort:{}", trackerHttpPort);
        log.info("zookeeperAddress:{}", zookeeperAddress);


        DfsConfig.set_connect_timeout(connectTimeout);
        DfsConfig.set_http_server(httpServer);
        DfsConfig.set_max_idle(maxIdle);
        DfsConfig.set_min_idle(minIdle);
        DfsConfig.set_max_total(maxTotal);
        DfsConfig.set_network_timeout(connectTimeout);
        DfsConfig.set_secret_key(secretKey);
        DfsConfig.set_tracker_adds(trackerAdds);
        DfsConfig.set_upload_temp_dir(uploadTempDir);
        DfsConfig.set_tracker_http_port(trackerHttpPort);
        DfsConfig.set_zookeeper_address(zookeeperAddress);
        FastDFSUtil.init();

        log.info("dfs 系统参数初始化 结束....... ");
    }
}
