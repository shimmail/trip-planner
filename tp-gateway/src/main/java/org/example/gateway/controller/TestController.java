package org.example.gateway.controller;


import org.example.common.result.Result;
import org.example.gateway.config.AccessLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    /**
     * 接口请求频率限制
     * 设置10秒内最多请求3次
     *
     * @return
     */
    @AccessLimit(seconds = 10, maxCount = 3)
    @GetMapping("/test")
    public Result test() {
        return Result.success("接口请求成功");
    }

}
