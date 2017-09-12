package com.terry;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
public class JwtServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtServerApplication.class, args);
    }

    String key = "23ddsshgdf@$";

    @Autowired
    PwdDao pwdDao;

    @PostMapping("/login")
    Back login(@RequestParam("name") String name,
               @RequestParam("pwd") String pwd) {
        Back back = new Back();
        Pswd rpwd = pwdDao.findByUsername(name);
        if (rpwd == null) {
            try {
                Pswd np = new Pswd();
                np.username = name;
                np.pwd = pwd;
                np = pwdDao.save(np);
                User user = new User();
                user.id = np.id;
                user.name = np.username;
                user.age = 20;
                user.sex = "girl";
                userDAO.save(user);
                back.code = 200;
                back.msg = "sign up success";
                back.data = Jwts.builder()
                        .claim("role", "admin")
                        .setSubject(np.id)
                        .signWith(SignatureAlgorithm.HS512, key)
                        .compact();
            } catch (Exception e) {
                back.code = 300;
                back.msg = e.getMessage();
                return back;
            }
        } else if (rpwd.pwd.equals(pwd)) {
            back.code = 200;
            back.msg = "login success";
            back.data = Jwts.builder()
                    .claim("role", "admin")
                    .setSubject(rpwd.id)
                    .signWith(SignatureAlgorithm.HS512, key)
                    .compact();
        } else {
            back.code = 400;
            back.msg = "login failed";
        }
        return back;
    }

    @Autowired
    UserDAO userDAO;

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
            back.data = userDAO.findOne(id);
        } catch (Exception e) {
            back.code = 400;
            back.msg = "JWT Incorrect";
        }
        return back;
    }
}
