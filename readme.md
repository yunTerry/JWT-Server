
# JWT Server端

## 使用[JWT](https://jwt.io/)(JSON Web Token)做无状态的API身份认证

使用jjwt开源库生成token：

https://github.com/jwtk/jjwt

Server端提供生成token和校验token的接口：

```java
@PostMapping("/login")
Back login(@RequestParam("name") String name,
           @RequestParam("pwd") String pwd);

@GetMapping("/user")
Back getUser(@RequestHeader("jwt") String token);
```

+ 登录接口提交用户名密码，返回jwt令牌

+ 用户信息接口客户端将token放在请求头，服务端校验通过即返回用户信息

## 客户端代码：

https://github.com/yunTerry/JWT-Android