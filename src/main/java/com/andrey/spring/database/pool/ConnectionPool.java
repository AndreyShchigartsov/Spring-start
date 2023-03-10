package com.andrey.spring.database.pool;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component("pool1")
@RequiredArgsConstructor
public class ConnectionPool {

    @Value("${db.username}")
    private final String username;

    @Value("${db.pool.size}")
    private final Integer pollSize;

    @PostConstruct
    private void init() {
        log.info("Init-method ConnectionPool");
    }
    @PreDestroy
    private void destroy() {
        log.info("Clean ConnectionPool");
    }
}
