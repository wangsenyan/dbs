package com.dbs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

/**
 * 配置默认序列化程序
 * docker pull 镜像源地址/redis版本
 */
@Configuration
public class MyRedisConfig {

    @Value("spring.redis.cluster.nodes")
    private String nodes;

//    @Bean
//    public RedisTemplate<Object, Employee> employeeRedisTemplate(
//            RedisConnectionFactory redisConnectionFactory
//    )throws UnknownHostException {
//        RedisTemplate<Object,Employee> template = new RedisTemplate<Object, Employee>();
//        template.setConnectionFactory(redisConnectionFactory);
//        Jackson2JsonRedisSerializer<Employee> serializer = new Jackson2JsonRedisSerializer<Employee>(Employee.class);
//        template.setDefaultSerializer(serializer);
//        return template;
//    }

//    @Bean
//    RedisConnectionFactory connectionFactory(){
//        List<String> clusterNodes = Arrays.asList(nodes);
//        return new JedisConnectionFactory(new RedisClusterConfiguration(clusterNodes));
//    }

    /**
     * RedisCacheManager
     *
     * @param redisConnectionFactory
     * @return 1 https://github.com/linq/spring-jedis-cache
     */
    @Primary //将某个缓存管理器作为默认管理器
    @Bean
    public RedisCacheManager RedisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration cacheConfiguration =
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofDays(1))   // 设置缓存过期时间为一天
                        .disableCachingNullValues();    // 禁用缓存空值，不缓存null校验
        //.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer( new Jackson2JsonRedisSerializer<Employee>(Employee.class)));

        //.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new
        // GenericJackson2JsonRedisSerializer()));     // 设置CacheManager的值序列化方式为json序列化，可加入@Class属性
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(cacheConfiguration).build();     // 设置默认的cache组件
    }

}
