package com.wxblog.core.config.mybatisplus;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: NightWish
 * @create:
 * @description:  逻辑删除配置
 **/
@Configuration
public class MybatisPlusConfig {

    @Bean
    public ISqlInjector sqlInjector(){
        return new LogicSqlInjector();
    }
}
