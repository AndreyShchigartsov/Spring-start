package com.andrey.spring.config;

import com.andrey.spring.database.pool.ConnectionPool;
import com.andrey.spring.database.repository.CrudRepository;
import com.andrey.spring.database.repository.UserRepository;
import com.andrey.web.WebConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
@Import(WebConfiguration.class)
@Configuration(proxyBeanMethods = true)
//@ComponentScan(basePackages = "com.andrey.spring",
//        useDefaultFilters = false,
//        includeFilters = {
//            @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Component.class),
//            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CrudRepository.class),
//            @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\..+Repository")
//        })
public class ApplicationConfiguration {

        @Bean
        @Scope(BeanDefinition.SCOPE_SINGLETON)
        public ConnectionPool pool2(@Value("${db.username}") String username){
                return new ConnectionPool(username, 20);
        }

        @Bean
        @Profile("prod1")
        @Scope(BeanDefinition.SCOPE_SINGLETON)
        public ConnectionPool pool4(){
                return new ConnectionPool("username", 20);
        }

        @Bean
        @Scope(BeanDefinition.SCOPE_SINGLETON)
        public ConnectionPool pool3(){
                return new ConnectionPool("test-pool", 25);
        }
}
