package com.benyuan.bootdemo.featuresname.dao;

import com.benyuan.bootdemo.featuresname.entity.Tea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @Description Repository
 */
public interface TeaRepository extends JpaRepository<Tea, Long>,QuerydslPredicateExecutor<Tea> {
    /**
     * findByName
     * @param name name
     * @return tea
     */
    Tea findByName(String name);
}
