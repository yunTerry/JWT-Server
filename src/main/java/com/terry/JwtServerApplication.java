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
        if (rpwd != null && rpwd.pwd.equals(pwd)) {
            back.code = 200;
            back.msg = "login success";
            String compactJws = Jwts.builder()
                    .claim("role", "admin")
                    .setSubject(rpwd.id)
                    .signWith(SignatureAlgorithm.HS512, key)
                    .compact();
            back.data = compactJws;
        } else {
            back.code = 300;
            back.msg = "login failed";
        }
        return back;
    }

    @Autowired
    UserDAO userDAO;

    @GetMapping("/user")
    Back getUser(@RequestHeader("jwt") String token){
        Back back = new Back();
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);
            back.code = 200;
            String id= claims.getBody().getSubject();
            back.msg = id;
            back.data = userDAO.findOne(id);
        } catch (Exception e) {
            back.code = 400;
            back.msg = "JWT Incorrect";
        }
        return back;
    }
}
