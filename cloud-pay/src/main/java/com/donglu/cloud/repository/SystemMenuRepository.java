package com.donglu.cloud.repository;

import com.donglu.cloud.bean.SystemMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemMenuRepository extends JpaRepository<SystemMenu, String>, QuerydslPredicateExecutor<SystemMenu> {
}
