package com.andrey.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Aspect
@Component
public class FirstAspect {

    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer(){
    }

    @Pointcut("within(com.andrey.spring.service.*Service)")
    public void isServiceLayer(){
    }

    @Pointcut("this(org.springframework.data.repository.Repository)")
    public void isRepositoryLayer(){
    }

    @Pointcut("isControllerLayer() && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void hasGetMapping(){
    }

    @Pointcut("isControllerLayer() && args(org.springframework.ui.Model,..)")
    public void hasModelParam(){
    }

    @Pointcut("isControllerLayer() && @args(com.andrey.spring.validation.UserInfo,..)")
    public void hasUserInfoParamAnnotation(){
    }

    @Pointcut("bean(*Service)")
    public void isServiceLayerBean(){
    }

    /*
        execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
    */
    @Pointcut("execution(public * com.andrey.spring.service.*Service.findById(*))")
    public void anyFindByIdServiceMethod(){
    }

    @Before("anyFindByIdServiceMethod() " +
            "&& args(id) " +
            "&& target(service) " +
            "&& this(serviceProxy) " +
            "&& @within(transactional)")
    public void addLogging(JoinPoint joinPoint,
                           Object id,
                           Object service,
                           Object serviceProxy,
                           Transactional transactional){
        log.info("invoked findById method in class {}, with id {}", service, id);
    }

    @AfterReturning(value = "anyFindByIdServiceMethod()" +
            " && target(service)",
            returning = "result")
    public void addLoggingAfterReturning(Object result, Object service){
        log.info("after returning - invoked findById method in class {}, result {}", service, result);
    }

    @AfterThrowing(value = "anyFindByIdServiceMethod()" +
            " && target(service)",
            throwing = "ex")
    public void addLoggingAfterThrowing(Throwable ex, Object service){
        log.info("after throwing - invoked findById method in class {}, exeption {}: {}", service, ex.getClass(), ex.getMessage());
    }

    @After("anyFindByIdServiceMethod() && target(service)")
    public void addLoggingAfterThrowing(Object service){
        log.info("after (finally) - invoked findById method in class {}", service);
    }

}
