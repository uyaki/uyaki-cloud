package com.benyuan.bootdemo.featuresname.service.impl;

import com.benyuan.bootdemo.featuresname.dao.TeaRepository;
import com.benyuan.bootdemo.featuresname.entity.Tea;
import com.benyuan.bootdemo.featuresname.service.TeaService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;

/**
 * @Description service实现类
 */
@Service
public class TeaServiceImpl implements TeaService {
    @Resource
    private TeaRepository teaRepository;

    @Resource
    private EntityManager entityManager;


    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void initFactory(){
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Tea getTea() {
        // QueryDsl 写法，单表操作不建议使用，请直接用JPA
        // QTea qTea = QTea.tea;
        // Tea tea= queryFactory.selectFrom(qTea).where(qTea.name.eq("红茶")).fetchOne();
        Tea tea = teaRepository.findByName("红茶");
        return tea;
    }
}
