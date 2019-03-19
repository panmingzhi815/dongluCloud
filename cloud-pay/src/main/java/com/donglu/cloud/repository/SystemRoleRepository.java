package com.donglu.cloud.repository;

import com.donglu.cloud.bean.SystemRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemRoleRepository extends JpaRepository<SystemRole, String>, QuerydslPredicateExecutor<SystemRole> {
}
