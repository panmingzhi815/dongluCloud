package com.donglu.cloud.repository;

import com.donglu.cloud.bean.SystemFile;
import com.donglu.cloud.bean.SystemLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemFileRepository extends JpaRepository<SystemFile, String>, QuerydslPredicateExecutor<SystemFile> {

}
