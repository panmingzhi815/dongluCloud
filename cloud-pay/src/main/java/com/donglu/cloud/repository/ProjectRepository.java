package com.donglu.cloud.repository;

import com.donglu.cloud.bean.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String>, QuerydslPredicateExecutor<Project> {
}
