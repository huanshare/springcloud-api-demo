package com.huanshare.test.api.gateway.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 这个是最简单的配置形式，一般应用用这种形式就可以了，
 * 用来指示Apollo注入application namespace的配置到Spring环境中
 *
 * created on 2018/5/30 14:01
 *
 *
 * @version 1.0.0
 */
@Configuration
@EnableApolloConfig({"application", "FRONT.eureka", "FRONT.test-redis"})
@Slf4j
public class ApolloConfig {
    @Bean
    Config config() {
        Config appConfig = ConfigService.getAppConfig();
        appConfig.addChangeListener(new ConfigChangeListener() {
            @Override
            public void onChange(ConfigChangeEvent configChangeEvent) {
                log.info("Changes for namespace " + configChangeEvent.getNamespace());
                for (String key : configChangeEvent.changedKeys()) {
                    ConfigChange change = configChangeEvent.getChange(key);
                    log.info(String.format(
                            "Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s",
                            change.getPropertyName(), change.getOldValue(),
                            change.getNewValue(), change.getChangeType()));
                }
            }
        });
        return ConfigService.getAppConfig();
    }
}
