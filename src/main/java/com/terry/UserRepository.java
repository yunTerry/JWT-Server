package com.terry;

import org.springframework.data.jpa.repository.JpaRepository;

/***
 * *
 * 名称：     UserRepository.
 * 作者：     Terry Tan
 * 创建时间：  on 2017/9/8.
 * 说明：     
 * *
 ***/

public interface UserRepository extends JpaRepository<User, String> {
}
