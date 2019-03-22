package com.donglu.cloud.repository;

import com.donglu.cloud.bean.WeiXinMerchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WeiXinMerchantRepository extends JpaRepository<WeiXinMerchant, String>, QuerydslPredicateExecutor<WeiXinMerchant> {
}
