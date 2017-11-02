package com.terry;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;

@RestController
@SpringBootApplication
public class JwtServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtServerApplication.class, args);
    }

    String key = "23ddsshgdf@$";

    @Autowired
    PwdRepository pwdRepository;

    @PostMapping("/login")
    Back login(@RequestParam("name") String name,
               @RequestParam("pwd") String pwd) {
        Back back = new Back();
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
                back.code = 200;
                back.msg = "sign up success";
                back.data = Jwts.builder()
                        .claim("role", "admin")
                        .setSubject(np.id)
                        //token有效期10天
                        .setExpiration(getExdate(10))
                        .signWith(SignatureAlgorithm.HS512, key)
                        .compact();
            } catch (Exception e) {
                back.code = 300;
                back.msg = e.getMessage();
                return back;
            }
        } else if (rpwd.pwd.equals(pwd)) {
            //老用户直接登录
            back.code = 200;
            back.msg = "login success";
            back.data = Jwts.builder()
                    .claim("role", "admin")
                    .setSubject(rpwd.id)
                    .setExpiration(getExdate(10))
                    .signWith(SignatureAlgorithm.HS512, key)
                    .compact();
        } else {
            back.code = 400;
            back.msg = "login failed";
        }
        return back;
    }

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user")
    Back getUser(@RequestHeader("jwt") String token) {
        Back back = new Back();
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);
            back.code = 200;
            String id = claims.getBody().getSubject();
            back.msg = "get success";
            back.data = userRepository.findOne(id);
        } catch (Exception e) {
            back.code = 400;
            back.msg = e.toString();
        }
        return back;
    }

    Date getExdate(int day){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }
}
