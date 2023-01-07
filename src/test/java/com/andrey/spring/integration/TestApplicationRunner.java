package com.andrey.spring.integration;

import com.andrey.spring.database.pool.ConnectionPool;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;

@TestConfiguration//Сканирует ApplicationRunner(ищет @SpringBootApplication) и теперь мы
// можем писать все мок и спай бины тут
public class TestApplicationRunner {

    @SpyBean(name = "pool1")
    private ConnectionPool pool1;
}
