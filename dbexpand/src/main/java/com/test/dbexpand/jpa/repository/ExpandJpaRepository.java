package com.test.dbexpand.jpa.repository;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//这个令人崩溃的实现，删了删了....全删了
@NoRepositoryBean
@Deprecated
public interface ExpandJpaRepository<T,ID extends Serializable> extends JpaRepository<T,ID> {

    T findOne(String condition,Object... objects);

    List<T> findAll(String condition, Object... objects);

//    List<T> findAll(Iterable<Predicate> predicates, Operator operator);
//
//    List<T> findAll(Iterable<Predicate> predicates, Operator operator, Sort sort);
//
//    Page<T> findAll(Iterable<Predicate> predicates, Operator operator, Pageable pageable);
//
//    long count(Iterable<Predicate> predicates,Operator operator);

    List<T> findAll(String condition,Pageable pageable,Object... objects);

    long count(String condition,Object... objects);

    void deleteByIds(Iterator<ID> ids);

    Class<T> getEntityClass();

    List<Map<String,Object>> nativeQuery4Map(String sql);

    Object nativeQuery4Object(String sql);
}

















