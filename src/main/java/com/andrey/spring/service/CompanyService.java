package com.andrey.spring.service;

import com.andrey.spring.database.entity.Company;
import com.andrey.spring.database.repository.CompanyRepository;
import com.andrey.spring.database.repository.CrudRepository;
import com.andrey.spring.dto.CompanyReadDto;
import com.andrey.spring.listener.entity.AccessType;
import com.andrey.spring.listener.entity.EntityEvent;
import com.andrey.spring.mapper.CompanyReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
//@Transactional//Работа с транзакциями осуществляется зачастую на уровне сервисов
public class CompanyService {

    private final CompanyReadMapper companyReadMapper;
    private final CompanyRepository companyRepository;
    private final ApplicationEventPublisher eventPublisher;

    public Optional<CompanyReadDto> findById(Integer id){
        return companyRepository.findById(id)
                .map(entity -> {
                    eventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));
                    return companyReadMapper.map(entity);
                });
    }

    public List<CompanyReadDto> findAll(){
        return companyRepository.findAll().stream()
                .map(companyReadMapper::map)
                .toList();
    }
}
