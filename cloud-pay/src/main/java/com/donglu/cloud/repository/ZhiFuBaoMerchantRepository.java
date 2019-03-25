package com.donglu.cloud.repository;

import com.donglu.cloud.bean.WeiXinMerchant;
import com.donglu.cloud.bean.ZhiFuBaoMerchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ZhiFuBaoMerchantRepository extends JpaRepository<ZhiFuBaoMerchant, String>, QuerydslPredicateExecutor<ZhiFuBaoMerchant> {
}
