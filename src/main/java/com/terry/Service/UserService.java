package com.terry.Service;

import com.terry.Bean.BaseBack;
import com.terry.Bean.Pswd;
import com.terry.Bean.User;
import com.terry.Repository.PwdRepository;
import com.terry.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * *
 * 名称：     LoginService
 * 作者：     Terry
 * 创建时间：  on 2018/1/30.
 * 说明：     
 * *
 ***/

@Service
public class UserService {

    @Autowired
    PwdRepository pwdRepository;
    @Autowired
    UserRepository userRepository;

    public BaseBack signOrLogin(String name, String pwd) {
        BaseBack back = new BaseBack();
        Pswd rpwd = pwdRepository.findByUsername(name);
        if (rpwd == null) {
            //新用户自动注册
            try {
                Pswd np = new Pswd();
                np.username = name;
                np.pwd = pwd;
                np = pwdRepository.save(np);
                User user = new User();
                user.id = np.id;
                user.name = np.username;
                user.age = 20;
                user.sex = "girl";
                userRepository.save(user);
                back.code = 0;
                back.msg = "sign up success";
                back.data = Util.getToken(np.id);
            } catch (Exception e) {
                back.code = 3;
                back.msg = e.getMessage();
                return back;
            }
        } else if (rpwd.pwd.equals(pwd)) {
            //老用户直接登录
            back.code = 0;
            back.msg = "login success";
            back.data = Util.getToken(rpwd.id);
        } else {
            back.code = 4;
            back.msg = "login failed";
        }
        return back;
    }

    public BaseBack getUserInfo(String token) {
        BaseBack back = new BaseBack();
        String id = Util.getUid(token);
        back.code = 0;
        back.msg = "get success";
        back.data = userRepository.findOne(id);
        return back;
    }

    public BaseBack getUserList() {
        BaseBack back = new BaseBack();
        back.code = 0;
        back.msg = "get success";
        back.data = userRepository.findAll();
        return back;
    }
}
