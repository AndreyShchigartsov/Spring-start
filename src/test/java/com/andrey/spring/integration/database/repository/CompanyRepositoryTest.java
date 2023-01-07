package com.andrey.spring.integration.database.repository;

import com.andrey.spring.database.entity.Company;
import com.andrey.spring.database.repository.CompanyRepository;
import com.andrey.spring.integration.IntegrationTestBase;
import com.andrey.spring.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Assertions;Assertions
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class CompanyRepositoryTest extends IntegrationTestBase {

    private static final Integer APPLE_ID = 5;
    private final EntityManager entityManager;
    private final TransactionTemplate transactionTemplate;//так как он есть в context то но автоматически заинжектиться в тест
    private final CompanyRepository companyRepository;

    @Test
    void checkFindByQueries(){
        companyRepository.findByName("Apple");
        companyRepository.findAllByNameContainingIgnoreCase("a");
    }

    @Test
    @Disabled
    void delete(){
        var maybeCompany = companyRepository.findById(APPLE_ID);
        assertTrue(maybeCompany.isPresent());
        maybeCompany.ifPresent(companyRepository::delete);
        entityManager.flush();//delete вызовится тогда, когда произойдет коммит транзакции или вызовится flush явно
        assertTrue(companyRepository.findById(APPLE_ID).isEmpty());
    }

    @Test
    void findById() {
        transactionTemplate.executeWithoutResult(tx -> {
            var company = entityManager.find(Company.class, 1);
            assertNotNull(company);
            assertThat(company.getLocation()).hasSize(2);
        });
    }

    @Test
    void save(){
        var company = Company.builder()
                .name("Apple")
                .location(Map.of(
                        "ru", "Apple описание",
                        "en", "Apple description"
                ))
                .build();
        entityManager.persist(company);
        assertNotNull(company.getId());
    }
}