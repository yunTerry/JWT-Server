
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

+ 登录接口：客户端提交用户名密码，服务端返回jwt令牌，如：

`
eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiYWRtaW4iLCJzdWIiOiI4YWFkYjYwMDVlNzU2YWE5MDE1ZTc1NzViYWRmMDAwMSJ9.Mr0KGzbeeFlFpSTuncku7smPuyx3KBz9SNLlgTIoe5qo10iSe_pdndBdKAX0gMoplMPGyG0eSoS_c0lpnEKOkA
`

+ 用户信息接口：客户端将token放在请求头，服务端校验通过即返回用户信息

+ 服务端无需存储jwt令牌，通过特定的算法和密钥校验token，同时取出Payload中携带的用户ID，减少不必要的数据库查询

+ 为了安全起见，实际开发中要做token注销和过期处理

## 客户端代码：

https://github.com/yunTerry/JWT-Android