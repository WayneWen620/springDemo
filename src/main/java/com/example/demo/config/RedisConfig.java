package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 整合Spring data Redis的配置類
 */
@Configuration
public class RedisConfig {
//    @Value("${spring.data.redis.host}")
//    private String redisHost;
//
//    @Value("${spring.data.redis.port}")
//    private Integer redisPort;
//
//    @Value("${spring.data.redis.database}")
//    private Integer redisDatabase;
//
//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        // Lettuce pool config application設定了
//        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
//        poolConfig.setMaxTotal(10);//最大連線
//        poolConfig.setMaxIdle(5);//最大空閒數
//        poolConfig.setMinIdle(1);//最小空閒數
//        poolConfig.setMaxWait(Duration.ofMillis(5000));
//
//        // Redis standalone configuration
//        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
//        redisConfig.setHostName(redisHost);
//        redisConfig.setPort(redisPort);
//        redisConfig.setDatabase(redisDatabase);
//        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
//                .poolConfig(poolConfig)
//                .commandTimeout(Duration.ofSeconds(2))
//                .build();
//        return new LettuceConnectionFactory(redisConfig,  clientConfig);
//    }

    /**
     * 用於執行Redis操作
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        //關聯LettuceConnectionFactory
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 使用 JSON 序列化 value
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);

        // key 使用 String
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }
}
