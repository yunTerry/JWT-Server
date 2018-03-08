
# JWT Server端

## 使用[JWT(JSON Web Token)](https://jwt.io/)做无状态的API身份认证

使用jjwt开源库生成token：

https://github.com/jwtk/jjwt

### RESTful接口

Server端基于SpringBoot开发，提供生成token和校验token的接口：

```java
@PostMapping("/login")
Back login(@RequestParam("name") String name,
           @RequestParam("pwd") String pwd);

@GetMapping("/user")
Back getUser(@RequestHeader("jwt") String token);
```

+ 登录或注册接口：客户端提交用户名密码，服务端完成登录或注册逻辑，然后生成jwt令牌并返回给客户端

+ 用户信息接口：客户端将token放在请求头，服务端校验是否合法，然后通过JAP从MySQL中查询并返回用户信息

+ 服务端无需存储jwt令牌，通过特定的算法和密钥校验token，同时取出Payload中携带的用户ID，减少不必要的数据库查询

+ 本例中设置jwt有效期为3天，如果过期就会请求失败，客户端需要重新申请token

### Token统一校验

业务相关接口一般都需要token验证，这就应该把验证逻辑放在一个统一业务层完成，Spring的AOP正好满足这一需要。

这里通过Spring的拦截器HandlerInterceptor接口实现，在Controller方法执行之前拦截需要鉴权的请求，验证token是否合法，合法就放行，不合法则直接抛出异常拦截请求，这就在请求处理之前实现了Token统一校验。

## 客户端代码：

https://github.com/yunTerry/JWT-Android