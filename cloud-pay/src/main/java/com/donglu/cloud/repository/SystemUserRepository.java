package com.donglu.cloud.repository;

import com.donglu.cloud.bean.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser, String>, QuerydslPredicateExecutor<SystemUser> {

}
