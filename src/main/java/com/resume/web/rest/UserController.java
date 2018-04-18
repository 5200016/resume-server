package com.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.resume.domain.SLogin;
import com.resume.service.SLoginService;
import com.resume.web.rest.util.ResultObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

/**
 * User : 黄志成
 * Date : 2018/4/18
 * Desc : 用户管理
 */

@Api(description="用户管理")
@RestController
@RequestMapping("/api")
public class UserController {
    private final SLoginService loginService;

    public UserController(SLoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 用户注册
     * @throws URISyntaxException
     */
    @ApiOperation("用户注册 RequestBody")
    @PostMapping("/register/user")
    @Timed
    public ResultObj registerUser(@ApiParam(name="login",value="用户登录实体",required=true) @RequestBody SLogin login) throws URISyntaxException {
        return ResultObj.back(true,200,loginService.registerUser(login));
    }

    /**
     * 通过用户名查询用户信息
     * @throws URISyntaxException
     */
    @ApiOperation("通过用户名查询用户信息 RequestParam")
    @PostMapping("/select/username")
    @Timed
    public ResultObj selectUserByUsername(@ApiParam(name="username",value="用户名",required=true) @RequestParam String username) throws URISyntaxException {
        return ResultObj.back(true,200,loginService.findUserByUsername(username));
    }
}
