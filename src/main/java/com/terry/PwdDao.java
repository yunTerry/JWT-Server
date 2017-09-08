package com.terry;

import org.springframework.data.jpa.repository.JpaRepository;

/***
 * *
 * 名称：     PwdDao.
 * 作者：     Terry Tan
 * 创建时间：  on 2017/9/8.
 * 说明：     
 * *
 ***/

public interface PwdDao extends JpaRepository<Pswd,String> {
}
