package com.terry.Controller;

import com.terry.Bean.BaseBack;
import com.terry.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/***
 * *
 * 名称：     LoginController
 * 作者：     Terry
 * 创建时间：  on 2018/1/30.
 * 说明：     
 * *
 ***/

@Api(tags="用户管理")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("注册&登录")
    @PostMapping("/login")
    BaseBack login(@RequestParam String name,
                   @RequestParam String pwd) {
        return userService.signOrLogin(name, pwd);
    }

    @ApiOperation("获取当前用户信息")
    @GetMapping("/user/info")
    BaseBack getUser(@RequestHeader String jwt) {
        return userService.getUserInfo(jwt);
    }

    @ApiOperation("获取用户列表")
    @GetMapping("/user/list")
    BaseBack getUserList(@RequestHeader String jwt) {
        return userService.getUserList();
    }
}
