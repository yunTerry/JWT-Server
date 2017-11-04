
# JWT Server端

## 使用[JWT](https://jwt.io/)(JSON Web Token)做无状态的API身份认证

使用jjwt开源库生成token：

https://github.com/jwtk/jjwt

Server端基于SpringBoot开发，提供生成token和校验token的接口：

```java
@PostMapping("/login")
Back login(@RequestParam("name") String name,
           @RequestParam("pwd") String pwd);

@GetMapping("/user")
Back getUser(@RequestHeader("jwt") String token);
```

配置MySQL数据库：
```sh
spring.datasource.dbcp2.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/userdb?useUnicode=true&characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=xx
spring.jpa.hibernate.ddl-auto=update
```

+ 用户名密码和用户信息通过JPA持久化到不同的MySQL数据库表中，通过用户id关联两张表

+ 登录接口：客户端提交用户名密码，服务端返回jwt令牌，如：

`
eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiYWRtaW4iLCJzdWIiOiI0MDI4Yjg4MTVmN2I0MmQxMDE1ZjdiNDQ4ZTZjMDAwMCIsImV4cCI6MTUxMDQ2NjEyMn0.U3UOe8Jc6HLE3hw8r6BSus8mr2q1mguo3jiFsLkvRf5jsNX2ibZzmJSVgGUmanNSN05Jrv6ZiBMmVbo-R5TYbg
`

+ 用户信息接口：客户端将token放在请求头，服务端校验是否合法，然后通过JAP从MySQL中查询并返回用户信息

+ 服务端无需存储jwt令牌，通过特定的算法和密钥校验token，同时取出Payload中携带的用户ID，减少不必要的数据库查询

+ 本例中设置jwt有效期为10天，服务端每次都会自动校验token是否过期，如果过期就直接抛出异常，客户端需要重新申请token

+ jwt存在一个设计缺陷——服务端无法主动注销token，所以安全性上不及session，实际开发中谨慎使用

## 客户端代码：

https://github.com/yunTerry/JWT-Android