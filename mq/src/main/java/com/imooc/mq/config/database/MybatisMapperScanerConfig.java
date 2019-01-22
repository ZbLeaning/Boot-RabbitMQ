package com.imooc.mq.config.database;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @Title: MybatisMapperScanerConfig
 * @Description: 扫码Mybatis
 * @AutoConfigureAfter(MybatisDataSourceConfig.class) 先加载数据源类，再加载该类
 * @date 2019/1/2214:43
 */
@Configuration
@AutoConfigureAfter(MybatisDataSourceConfig.class)
public class MybatisMapperScanerConfig {
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.imooc.mq.mapper");
        return mapperScannerConfigurer;
    }
}
