package io.xdea.xmux.forum.config;

import com.aliyun.green20220302.Client;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AliyunGreenConfig {
    @Value("${aliyun.green.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.green.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.green.endpoint}")
    private String endpoint;

    @Value("${aliyun.green.readTimeout}")
    private Integer readTimeout;

    @Value("${aliyun.green.connectTimeout}")
    private Integer connectTimeout;


    @Bean
    public Client aliyunGreenClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret)
                .setEndpoint(endpoint)
                .setReadTimeout(readTimeout)
                .setConnectTimeout(connectTimeout);

        return new Client(config);
    }
}
