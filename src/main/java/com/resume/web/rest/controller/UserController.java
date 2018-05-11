package com.resume.web.rest.controller;

import com.codahale.metrics.annotation.Timed;
import com.resume.domain.SLogin;
import com.resume.domain.SUser;
import com.resume.service.SLoginService;
import com.resume.service.SUserService;
import com.resume.web.rest.util.DateUtil;
import com.resume.web.rest.util.ResultObj;
import com.resume.web.rest.util.TypeUtils;
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
    private final SUserService userService;

    public UserController(SLoginService loginService, SUserService userService) {
        this.loginService = loginService;
        this.userService = userService;
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
    @ApiOperation("通过用户名查询用户登录信息 RequestParam")
    @PostMapping("/select/username")
    @Timed
    public ResultObj selectUserByUsername(@ApiParam(name="username",value="用户名",required=true) @RequestParam String username) throws URISyntaxException {
        return ResultObj.back(true,200,loginService.findUserByUsername(username));
    }

    /**
     * 通过用户名查询用户基本信息
     * @throws URISyntaxException
     */
    @ApiOperation("通过用户名查询用户基本信息 RequestParam")
    @PostMapping("/select/info")
    @Timed
    public ResultObj selectUserInfoByUsername(@ApiParam(name="username",value="用户名",required=true) @RequestParam String username) throws URISyntaxException {
        return ResultObj.back(200,userService.findUserByUsername(username));
    }

    /**
     * 修改密码
     * @throws URISyntaxException
     */
    @ApiOperation("修改密码 RequestParam")
    @PutMapping("/update/password")
    @Timed
    public ResultObj updatePassword(@ApiParam(name="username",value="用户账号",required=true) @RequestParam String username,
                                    @ApiParam(name="newPwd",value="新密码",required=true) @RequestParam String newPwd,
                                    @ApiParam(name="oldPwd",value="旧密码",required=true) @RequestParam String oldPwd) throws URISyntaxException {
        return loginService.updatePassword(username, newPwd, oldPwd);
    }

    /**
     * 重置密码
     * @throws URISyntaxException
     */
    @ApiOperation("重置密码 RequestParam")
    @PutMapping("/reset/password")
    @Timed
    public ResultObj resetPassword() throws URISyntaxException {
        return null;
    }

    /**
     * 修改用户个人信息
     * @throws URISyntaxException
     */
    @ApiOperation("修改用户个人信息 RequestBody")
    @PutMapping("/update/info")
    @Timed
    public ResultObj updateUserInfo(@ApiParam(name="user",value="用户实体",required=true) @RequestBody SUser user) throws URISyntaxException {
        if(TypeUtils.isEmpty(user.getId())){
            return ResultObj.backInfo(false,200,"修改失败",false);
        }
        user.setUpdateTime(DateUtil.getZoneDateTime());
        return ResultObj.back(200,userService.save(user));
    }
}
