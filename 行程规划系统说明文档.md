# 项目结构目录树

trip-planner/  
├── tp-common/                     # 公共模块  
│   ├── src/main/java/org/example/common  
│   │   ├── config/                # 全局配置（如MvcConfig）  
│   │   ├── exception/             # 自定义异常（如InvalidTokenException）  
│   │   ├── interceptor/           # 拦截器（如UserContextInterceptor）  
│   │   ├── utils/                 # 工具类（如JwtUtil、UserContext）  
│   │   └── result/                # 统一响应封装（Result类）  
│  
├── user-service/                  # 用户服务  
│   ├── src/main/java/org/example/userservice  
│   │   ├── controller/            # 用户接口（UserController）  
│   │   ├── service/               # 业务逻辑（UserService与UserServiceImpl）  
│   │   ├── mapper/                # 数据访问层（UserMapper）  
│   │   ├── pojo/                  # 实体与VO  
│   │   │   ├── entity/User        # 用户实体  
│   │   │   └── vo/UserVO          # 用户视图对象  
│   │   └── UserServiceApplication # 启动类  
│  
├── destination-service/           # 目的地服务  
│   ├── src/main/java/org/example/destinationservice  
│   │   ├── controller/            # 目的地接口（DestinationController）  
│   │   ├── service/               # 业务逻辑（DestinationService）  
│   │   ├── mapper/                # 数据映射（DestinationMapper）  
│   │   ├── pojo/                  # 实体与VO  
│   │   │   ├── entity/Destination # 目的地实体  
│   │   │   └── vo/DestinationVO   # 目的地视图对象  
│  
├── itinerary-service/             # 行程服务  
│   ├── src/main/java/org/example/itineraryservice  
│   │   ├── controller/            # 行程接口（ItineraryController）  
│   │   ├── service/               # 业务逻辑（ItineraryServiceImpl）  
│   │   ├── mapper/                # 数据映射（ItineraryMapper）  
│   │   ├── pojo/                  # 实体与DTO  
│   │   │   ├── entity/Itinerary   # 行程实体  
│   │   │   └── dto/ItineraryDTO   # 行程数据传输对象  
│  
├── gateway/                       # 网关服务  
│   ├── src/main/java/org/example/gateway  
│   │   ├── config/                # 网关配置（如RatelimitConfig）  
│   │   ├── filters/               # 过滤器（如RatelimitGatewayFilter）  
│   │   └── TpGatewayApplication   # 启动类  
│  

├── tp-api/                          # API公共模块（存放Feign客户端接口与DTO）  
│   ├── src/main/java/org/example/api  
│   │   ├── client/  
│   │   │   └── DestinationClient.java  # Feign客户端接口（调用目的地服务）  
│   │   ├── dto/  
│   │   │   ├── DestinationDTO.java     # 目的地数据传输对象  
│   │   │   ├── ItineraryDTO.java       # 行程数据传输对象  
│   │   │   └── UserDTO.java            # 用户数据传输对象  
│   │   └── enums/  
│   │       └── ItineraryStatus.java    # 行程状态枚举（如PLANNING, COMPLETED）  
│   └── pom.xml                        # 模块依赖配置  

└── docker-compose.yml             # Docker环境编排  



# 任务

#### Spring Cloud Gateway进行用户身份校验

在 `GatewayFilter` 中：

- 从请求头中获取 `Authorization`（通常是 JWT Token）。
- 使用 `JwtUtil` 解析 `token` 并验证其有效性。
- 如果 `token` 无效，返回 401 未授权响应。
- 如果 `token` 有效，将 `userId` 提取并加入新的请求头（ `user-info`）。

```java
@Override
public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    try {
        // 1. 获取Request对象，以访问请求的相关信息
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().toString();

        // 2. 判断请求路径是否在白名单中
        if (isIgnoreUrl(path)){
            log.info("直接放行");
            return chain.filter(exchange);
        }

        // 3. 获取请求头中的token
        String token = null;
        List<String> headers = request.getHeaders().get("Authorization");
        if (!(headers == null || headers.isEmpty())) {
            token = headers.get(0);
        }

        // 4. 使用jwtTool校验并解析token
        Long userId = null;
        userId = jwtUtil.getId(token);
        System.out.println("userId = " + userId); // 打印用户ID供调试
        String userInfo = userId.toString();
        ServerWebExchange modifiedExchange = exchange.mutate()
                .request(builder -> builder.header("user-info", userInfo)) // 将用户信息保存到请求头
                .build();
        // 6. 放行请求
        return chain.filter(exchange);

    } catch (Exception e) {
        ServerHttpResponse response = exchange.getResponse();
        // 设置未授权状态码
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        // 设置返回的 JSON 错误信息
        String errorMessage = Result.error(e.getMessage()).toJson();
        // 将错误信息写入响应体
        return response.writeWith(Mono.just(response.bufferFactory().wrap(errorMessage.getBytes())));
    }
}
```

在微服务中，使用 `UserContextInterceptor` 提取 `user-info` 请求头中的用户信息，并将其存入 `ThreadLocal`，供业务代码使用。

```java
public class UserContextInterceptor implements HandlerInterceptor {

    // 通过 preHandle 在请求到达处理方法前获取用户信息
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("user-info");

        if (userId != null) {
            // 将用户ID存储到 ThreadLocal 中
            UserContext.setUser(Long.parseLong(userId));
        }
```

在 Spring 的拦截器配置中注册此拦截器：

```java
@Configuration
@ConditionalOnClass(DispatcherServlet.class)
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserContextInterceptor());
    }
}
```

在业务代码中可以随时通过 `UserContext.getUser()` 获取当前用户 ID



#### 服务注册与发现（Nacos）

- 每个微服务（如`destination-service`）启动时，向Nacos注册中心注册自身信息（服务名、IP、端口等）。
- 其他服务（如`itinerary-service`）通过Nacos查询目标服务的可用实例列表，实现动态路由。

```yaml
spring:
  application:
    name: destination-service
  cloud:
    nacos:
      server-addr: 192.168.184.130:8850
```

#### 服务间调用

- DestinationClient 使 itinerary-service 可以调用 destination-service 里的方法获得目的地信息

```java
@FeignClient("destination-service")
@Component
public interface DestinationClient {
    @GetMapping("/destination/getDestinationsByName")
    Result <DestinationDTO> getDestinationsByName(@RequestParam String name);

    @GetMapping("/destination/getDestinationsById")
    Result <DestinationDTO> getDestinationsById(@RequestParam long id);
}
```



# trip-planner接口

# 用户模块

## POST  用户注册

POST /user/register

> Body 请求参数

```json
{
  "username": "string",
  "password": "string",
  "email": "string"
}
```

### 请求参数

| 名称         | 位置 | 类型              | 必选 | 说明 |
| ------------ | ---- | ----------------- | ---- | ---- |
| body         | body | object            | 否   | none |
| » id         | body | integer           | 是   | none |
| » username   | body | string            | 是   | none |
| » password   | body | string            | 是   | none |
| » email      | body | string            | 是   | none |
| » created_at | body | string(date-time) | 否   | none |
| » updated_at | body | string(date-time) | 否   | none |

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "string",
  "data": {}
}
```

### 返回结果

| 状态码 | 状态码含义                                              | 说明 | 数据模型 |
| ------ | ------------------------------------------------------- | ---- | -------- |
| 200    | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | none | Inline   |

### 返回数据结构

状态码 **200**

| 名称   | 类型    | 必选  | 约束 | 中文名                         | 说明 |
| ------ | ------- | ----- | ---- | ------------------------------ | ---- |
| » code | integer | true  | none | 编码：1成功，0和其它数字为失败 | none |
| » msg  | string  | false | none |                                | none |
| » data | object  | false | none |                                | none |

# 行程规划模块

## POST 创建行程

POST /itinerary/saveItinerary

添加行程需要查找目的地，并将对应的目的地加入行程中

> Body 请求参数

```json
{
  "destinationName": "string",
  "destinationDescription": "string",
  "start_date": "2019-08-24T14:15:22Z",
  "end_date": "2019-08-24T14:15:22Z"
}
```

### 请求参数

| 名称                     | 位置   | 类型              | 必选 | 中文名     | 说明 |
| ------------------------ | ------ | ----------------- | ---- | ---------- | ---- |
| Authorization            | header | string            | 是   |            | none |
| body                     | body   | object            | 否   |            | none |
| » destinationName        | body   | string            | 否   | 目的地名称 | none |
| » destinationDescription | body   | string            | 否   | 行程描述   | none |
| » start_date             | body   | string(date-time) | 否   | 开始日期   | none |
| » end_date               | body   | string(date-time) | 否   | 结束日期   | none |

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "string",
  "data": {}
}
```

### 返回结果

| 状态码 | 状态码含义                                              | 说明 | 数据模型 |
| ------ | ------------------------------------------------------- | ---- | -------- |
| 200    | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | none | Inline   |

### 返回数据结构

状态码 **200**

| 名称   | 类型    | 必选 | 约束 | 中文名                         | 说明                           |
| ------ | ------- | ---- | ---- | ------------------------------ | ------------------------------ |
| » code | integer | true | none | 编码：1成功，0和其它数字为失败 | 编码：1成功，0和其它数字为失败 |
| » msg  | string  | true | none |                                | none                           |
| » data | object  | true | none |                                | none                           |

## GET 查询行程

GET /itinerary/listItinerariesById

> Body 请求参数

```json
{}
```

### 请求参数

| 名称          | 位置   | 类型    | 必选 | 中文名 | 说明                            |
| ------------- | ------ | ------- | ---- | ------ | ------------------------------- |
| page          | query  | integer | 否   |        | 页码，默认值为 1                |
| size          | query  | integer | 否   |        | 每页显示的任务数量，默认值为 10 |
| Authorization | header | string  | 是   |        | none                            |
| body          | body   | object  | 否   |        | none                            |

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "string",
  "data": {
    "items": [
      {
        "name": "string",
        "location": "string",
        "description": "string",
        "start_date": "2019-08-24T14:15:22Z",
        "end_date": "2019-08-24T14:15:22Z",
        "status": "0",
        "created_at": "CURRENT_TIMESTAMP",
        "updated_at": "CURRENT_TIMESTAMP"
      }
    ]
  }
}
```

### 返回结果

| 状态码 | 状态码含义                                              | 说明 | 数据模型 |
| ------ | ------------------------------------------------------- | ---- | -------- |
| 200    | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | none | Inline   |

### 返回数据结构

状态码 **200**

| 名称            | 类型              | 必选  | 约束 | 中文名                     | 说明                           |
| --------------- | ----------------- | ----- | ---- | -------------------------- | ------------------------------ |
| » code          | integer           | true  | none |                            | 编码：1成功，0和其它数字为失败 |
| » msg           | string            | true  | none |                            | none                           |
| » data          | object            | true  | none |                            | none                           |
| »» items        | [object]          | true  | none |                            | none                           |
| »»» name        | string            | true  | none | 目的地名称                 | none                           |
| »»» location    | string            | false | none | 位置                       | none                           |
| »»» description | string            | false | none | 行程描述                   | none                           |
| »»» start_date  | string(date-time) | false | none | 开始日期                   | none                           |
| »»» end_date    | string(date-time) | false | none | 结束日期                   | none                           |
| »»» status      | integer           | false | none | 行程状态：0=待办，1=已完成 | none                           |
| »»» created_at  | string(date-time) | false | none |                            | none                           |
| »»» updated_at  | string(date-time) | false | none |                            | none                           |

## PUT 更新行程

PUT /itinerary/updateItinerary

> Body 请求参数

```json
{
  "id": 1550096057,
  "user_id": 67,
  "destination_id": 1900306374,
  "description": "手与代引九圆般龙水成联包各根上其。该素率少大如阶实算西明收后建九。较包起学称相置油价此研资很金相较来。须儿音真志始我了八正很济至。各始那专识得中提克拉何出。你和很话列比报海通大实里。技青更快好越选三车许情号我按深。",
  "start_date": "1971-03-29",
  "end_date": "2016-05-26",
  "status": 1,
  "created_at": "2001-04-24 21:25:11",
  "updated_at": "1974-08-11 01:16:58"
}
```

### 请求参数

| 名称                     | 位置   | 类型                   | 必选 | 中文名                     | 说明    |
| ------------------------ | ------ | ---------------------- | ---- | -------------------------- | ------- |
| id                       | query  | integer                | 是   |                            | ID 编号 |
| Authorization            | header | string                 | 是   |                            | none    |
| body                     | body   | object                 | 否   |                            | none    |
| » destinationName        | body   | string¦null            | 否   | 目的地名称                 | none    |
| » destinationDescription | body   | string¦null            | 否   | 行程描述                   | none    |
| » start_date             | body   | string(date-time)¦null | 否   | 开始日期                   | none    |
| » end_date               | body   | string(date-time)¦null | 否   | 结束日期                   | none    |
| » status                 | body   | integer¦null           | 否   | 行程状态：0=待办，1=已完成 | none    |

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "string",
  "data": {}
}
```

### 返回结果

| 状态码 | 状态码含义                                              | 说明 | 数据模型 |
| ------ | ------------------------------------------------------- | ---- | -------- |
| 200    | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | none | Inline   |

### 返回数据结构

状态码 **200**

| 名称   | 类型    | 必选 | 约束 | 中文名                         | 说明                           |
| ------ | ------- | ---- | ---- | ------------------------------ | ------------------------------ |
| » code | integer | true | none | 编码：1成功，0和其它数字为失败 | 编码：1成功，0和其它数字为失败 |
| » msg  | string  | true | none |                                | none                           |
| » data | object  | true | none |                                | none                           |

## DELETE 删除行程

DELETE /itinerary/deleteItinerary

> Body 请求参数

```json
{
  "id": 0
}
```

### 请求参数

| 名称          | 位置   | 类型    | 必选 | 中文名 | 说明    |
| ------------- | ------ | ------- | ---- | ------ | ------- |
| id            | query  | integer | 否   |        | ID 编号 |
| Authorization | header | string  | 是   |        | none    |
| body          | body   | object  | 否   |        | none    |
| » id          | body   | integer | 是   |        | ID 编号 |

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "string",
  "data": {}
}
```

### 返回结果

| 状态码 | 状态码含义                                              | 说明 | 数据模型 |
| ------ | ------------------------------------------------------- | ---- | -------- |
| 200    | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | none | Inline   |

### 返回数据结构

状态码 **200**

| 名称   | 类型    | 必选 | 约束 | 中文名                         | 说明                           |
| ------ | ------- | ---- | ---- | ------------------------------ | ------------------------------ |
| » code | integer | true | none | 编码：1成功，0和其它数字为失败 | 编码：1成功，0和其它数字为失败 |
| » msg  | string  | true | none |                                | none                           |
| » data | object  | true | none |                                | none                           |

# 目的地模块

## GET 查找目的地

GET /destination/getDestinationsByName

> Body 请求参数

```json
{
  "id": 1
}
```

### 请求参数

| 名称          | 位置   | 类型   | 必选 | 中文名 | 说明 |
| ------------- | ------ | ------ | ---- | ------ | ---- |
| name          | query  | string | 否   |        | 名称 |
| Authorization | header | string | 是   |        | none |
| body          | body   | object | 否   |        | none |

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "string",
  "data": {
    "items": {
      "id": 1,
      "name": "string",
      "description": "string",
      "location": "string",
      "created_at": "CURRENT_TIMESTAMP",
      "updated_at": "CURRENT_TIMESTAMP"
    }
  }
}
```

### 返回结果

| 状态码 | 状态码含义                                              | 说明 | 数据模型 |
| ------ | ------------------------------------------------------- | ---- | -------- |
| 200    | [OK](https://tools.ietf.org/html/rfc7231#section-6.3.1) | none | Inline   |

### 返回数据结构

状态码 **200**

| 名称            | 类型              | 必选  | 约束 | 中文名                         | 说明                           |
| --------------- | ----------------- | ----- | ---- | ------------------------------ | ------------------------------ |
| » code          | integer           | true  | none | 编码：1成功，0和其它数字为失败 | 编码：1成功，0和其它数字为失败 |
| » msg           | string            | true  | none |                                | none                           |
| » data          | object            | true  | none |                                | none                           |
| »» items        | object            | true  | none |                                | none                           |
| »»» id          | integer           | true  | none |                                | ID 编号                        |
| »»» name        | string            | true  | none | 目的地名称                     | none                           |
| »»» description | string            | false | none | 行程描述                       | none                           |
| »»» location    | string            | false | none | 位置                           | none                           |
| »»» created_at  | string(date-time) | false | none |                                | none                           |
| »»» updated_at  | string(date-time) | false | none |                                | none                           |

# 数据模型

<h2 id="tocS_users">users</h2>

```json
{
  "id": 1,
  "username": "string",
  "password": "string",
  "email": "string",
  "created_at": "CURRENT_TIMESTAMP",
  "updated_at": "CURRENT_TIMESTAMP"
}

```

### 属性

| 名称       | 类型              | 必选  | 约束 | 中文名 | 说明 |
| ---------- | ----------------- | ----- | ---- | ------ | ---- |
| id         | integer           | true  | none |        | none |
| username   | string            | true  | none |        | none |
| password   | string            | true  | none |        | none |
| email      | string            | true  | none |        | none |
| created_at | string(date-time) | false | none |        | none |
| updated_at | string(date-time) | false | none |        | none |

<h2 id="tocS_destinations">destinations</h2>

```json
{
  "id": 1,
  "name": "string",
  "description": "string",
  "location": "string",
  "created_at": "CURRENT_TIMESTAMP",
  "updated_at": "CURRENT_TIMESTAMP"
}

```

### 属性

| 名称        | 类型              | 必选  | 约束 | 中文名     | 说明    |
| ----------- | ----------------- | ----- | ---- | ---------- | ------- |
| id          | integer           | true  | none |            | ID 编号 |
| name        | string            | true  | none | 目的地名称 | none    |
| description | string            | false | none | 行程描述   | none    |
| location    | string            | false | none | 位置       | none    |
| created_at  | string(date-time) | false | none |            | none    |
| updated_at  | string(date-time) | false | none |            | none    |

<h2 id="tocS_result">result</h2>

```json
{
  "code": 0,
  "msg": "string",
  "data": {}
}

```

### 属性

| 名称 | 类型    | 必选 | 约束 | 中文名                         | 说明                           |
| ---- | ------- | ---- | ---- | ------------------------------ | ------------------------------ |
| code | integer | true | none | 编码：1成功，0和其它数字为失败 | 编码：1成功，0和其它数字为失败 |
| msg  | string  | true | none |                                | none                           |
| data | object  | true | none |                                | none                           |

