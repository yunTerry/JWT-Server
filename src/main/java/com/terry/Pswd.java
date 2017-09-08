package com.terry;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/***
 * *
 * 名称：     Pswd.
 * 作者：     Terry Tan
 * 创建时间：  on 2017/9/8.
 * 说明：     
 * *
 ***/

@Entity
public class Pswd {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public String id;
    public String username,pwd;

}
