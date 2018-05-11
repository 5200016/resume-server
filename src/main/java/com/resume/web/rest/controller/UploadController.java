package com.resume.web.rest.controller;

import com.alibaba.fastjson.JSONObject;
import com.codahale.metrics.annotation.Timed;
import com.resume.config.ApplicationProperties;
import com.resume.domain.SUser;
import com.resume.service.SUserService;
import com.resume.web.rest.util.DateUtil;
import com.resume.web.rest.util.ResultObj;
import com.resume.web.rest.util.TypeUtils;
import com.resume.web.rest.util.UploadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URISyntaxException;

/**
 * User : 黄志成
 * Date : 2018/4/18
 * Desc : 文件上传管理
 */

@Api(description="文件上传管理")
@RestController
@RequestMapping("/api")
public class UploadController {
    private final ApplicationProperties applicationProperties;
    private final SUserService userService;

    public UploadController(ApplicationProperties applicationProperties, SUserService userService) {
        this.applicationProperties = applicationProperties;
        this.userService = userService;
    }


    /**
     * 上传图片
     * @param request
     * @param myfile
     * @param username
     * @return
     * @throws URISyntaxException
     */
    @ApiOperation("上传图片 RequestParam")
    @PostMapping("/upload/avatar")
    @Timed
    public ResultObj uploadPic(@ApiParam(name="file",value="上传文件",required=true) HttpServletRequest request, @RequestParam MultipartFile myfile,
                               @ApiParam(name="username",value="用户账号",required=true) @RequestParam String username) throws URISyntaxException {
        String avatar = applicationProperties.getAvatorpath();
        new File(UploadUtils.getFullPath(applicationProperties.getFilepath(),applicationProperties.getAvatorpath())).mkdirs();                               //创建文件夹

        SUser sUser = userService.findUserByUsername(username);
        if(TypeUtils.isEmpty(sUser.getAvatar())){
            if (!TypeUtils.isEmpty(myfile)) {
                String url = UploadUtils.uploadDoc(myfile, avatar);
                sUser.setAvatar(url);
                sUser.setUpdateTime(DateUtil.getZoneDateTime());
                userService.save(sUser);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("url",url);
                return ResultObj.back(200,jsonObject);
            }
        }else {
            if (!TypeUtils.isEmpty(myfile)) {
                String url = UploadUtils.uploadDoc(myfile, avatar);
                new File(applicationProperties.getFilepath() + sUser.getAvatar()).delete();
                sUser.setAvatar(url);
                sUser.setUpdateTime(DateUtil.getZoneDateTime());
                userService.save(sUser);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("url",url);
                return ResultObj.back(200,jsonObject);
            }
        }

        return ResultObj.back(200,null);
    }
}
