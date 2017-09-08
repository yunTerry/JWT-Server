package com.terry;

import org.springframework.data.jpa.repository.JpaRepository;

/***
 * *
 * 名称：     UserDAO.
 * 作者：     Terry Tan
 * 创建时间：  on 2017/9/8.
 * 说明：     
 * *
 ***/

public interface UserDAO extends JpaRepository<User, String> {
}
