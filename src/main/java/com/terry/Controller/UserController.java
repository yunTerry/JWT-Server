package com.terry.Controller;

import com.terry.Bean.BaseBack;
import com.terry.Service.UserService;
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

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    BaseBack login(@RequestParam String name,
                   @RequestParam String pwd) {
        return userService.signOrLogin(name, pwd);
    }

    @GetMapping("/user")
    BaseBack getUser(@RequestHeader String jwt) {
        return userService.getUserInfo(jwt);
    }

    @GetMapping("/user/list")
    BaseBack getUserList(@RequestHeader String jwt) {
        return userService.getUserList();
    }
}
