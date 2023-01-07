package com.andrey.spring.integration.service;

import com.andrey.spring.database.entity.Role;
import com.andrey.spring.dto.UserCreateEditDto;
import com.andrey.spring.dto.UserReadDto;
import com.andrey.spring.integration.IntegrationTestBase;
import com.andrey.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.util.Optional;


//@IT
@RequiredArgsConstructor//что бы не ставить @Autowired
//@DirtiesContext//Заперещает переиспользовать конткест
public class UserServiceIT extends IntegrationTestBase {

    private static final Long USER_1 = 1L;
    private static final Integer COMPANY_1 = 1;
    private final UserService userService;

    @Test
    void findAll(){
        var users = userService.findAll();
        Assertions.assertThat(users).hasSize(5);
    }

    @Test
    void findById(){
        Optional<UserReadDto> maybeUser = userService.findById(USER_1);
        org.junit.jupiter.api.Assertions.assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> org.junit.jupiter.api.Assertions.assertEquals("ivan@gmail.com", user.getUsername()));
    }

    @Test
    void create(){
        UserCreateEditDto userDto  = new UserCreateEditDto(
            "test@gmail.com",
                "test",
                LocalDate.now(),
                "Test",
                "Test",
                Role.ADMIN,
                COMPANY_1,
                new MockMultipartFile("test", new byte[0])
        );
        var actualResult = userService.create(userDto);
        org.junit.jupiter.api.Assertions.assertEquals(userDto.getUsername(), actualResult.getUsername());
        org.junit.jupiter.api.Assertions.assertEquals(userDto.getBirthDate(), actualResult.getBirthDate());
        org.junit.jupiter.api.Assertions.assertEquals(userDto.getFirstname(), actualResult.getFirstname());
        org.junit.jupiter.api.Assertions.assertEquals(userDto.getLastname(), actualResult.getLastname());
        org.junit.jupiter.api.Assertions.assertEquals(userDto.getCompanyId(), actualResult.getCompanyId().id());
        org.junit.jupiter.api.Assertions.assertSame(userDto.getRole(), actualResult.getRole());
    }

    @Test
    void update(){
        UserCreateEditDto userDto  = new UserCreateEditDto(
                "test@gmail.com",
                "test",
                LocalDate.now(),
                "Test",
                "Test",
                Role.ADMIN,
                COMPANY_1,
                new MockMultipartFile("test", new byte[0])
        );
        var actualResult = userService.update(USER_1, userDto);
        org.junit.jupiter.api.Assertions.assertTrue(actualResult.isPresent());

        actualResult.ifPresent(user -> {
            org.junit.jupiter.api.Assertions.assertEquals(userDto.getUsername(), user.getUsername());
            org.junit.jupiter.api.Assertions.assertEquals(userDto.getBirthDate(), user.getBirthDate());
            org.junit.jupiter.api.Assertions.assertEquals(userDto.getFirstname(), user.getFirstname());
            org.junit.jupiter.api.Assertions.assertEquals(userDto.getLastname(), user.getLastname());
            org.junit.jupiter.api.Assertions.assertEquals(userDto.getCompanyId(), user.getCompanyId().id());
            org.junit.jupiter.api.Assertions.assertSame(userDto.getRole(), user.getRole());
        });
    }

    @Test
    void delete(){
        org.junit.jupiter.api.Assertions.assertTrue(userService.delete(USER_1));
        org.junit.jupiter.api.Assertions.assertFalse(userService.delete(-123L));
    }
}
