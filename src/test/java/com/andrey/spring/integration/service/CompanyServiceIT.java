package com.andrey.spring.integration.service;

import com.andrey.spring.config.DatabaseProperties;
import com.andrey.spring.dto.CompanyReadDto;
import com.andrey.spring.integration.annotation.IT;
import com.andrey.spring.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.assertEquals;


@IT
@RequiredArgsConstructor
//@ExtendWith(SpringExtension.class)//интегрирует jUnit с TestContext Framework
//@ContextConfiguration(classes = ApplicationRunner.class, initializers = ConfigDataApplicationContextInitializer.class)
public class CompanyServiceIT {

    private static final Integer COMPANY_ID = 1;

    private final CompanyService companyService;

    private final DatabaseProperties databaseProperties;

    @Test
    @Transactional
    void findDyId(){
        var actualResult = companyService.findById(COMPANY_ID);

        Assertions.assertTrue(actualResult.isPresent());

        var expectedResult = new CompanyReadDto(COMPANY_ID, null);
        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));
    }
}
