package com.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.resume.domain.*;
import com.resume.service.*;
import com.resume.web.rest.util.ResultObj;
import com.resume.web.rest.vm.WorkProjectVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

/**
 * User : 黄志成
 * Date : 2018/5/8
 * Desc : 用户详细信息管理
 */

@Api(description="用户详细信息管理")
@RestController
@RequestMapping("/api")
public class InformationController {
    private final BInformationService informationService;
    private final BContactService contactService;
    private final BWorkProjectService workProjectService;
    private final BWorkService workService;
    private final BHonourService honourService;
    private final BHobbyService hobbyService;
    private final JobObjectiveService jobObjectiveService;

    public InformationController(BInformationService informationService, BContactService contactService, BWorkProjectService workProjectService, BWorkService workService, BHonourService honourService, BHobbyService hobbyService, JobObjectiveService jobObjectiveService) {
        this.informationService = informationService;
        this.contactService = contactService;
        this.workProjectService = workProjectService;
        this.workService = workService;
        this.honourService = honourService;
        this.hobbyService = hobbyService;
        this.jobObjectiveService = jobObjectiveService;
    }

    /**
     * 通过用户名查询用户详细信息
     * @throws URISyntaxException
     */
    @ApiOperation("通过用户名查询用户详细信息 RequestParam")
    @PostMapping("/select/information")
    @Timed
    public ResultObj selectInformationByUsername(@ApiParam(name="username",value="用户名",required=true) @RequestParam String username) throws URISyntaxException {
        return ResultObj.back(true,200,informationService.findUserInformation(username));
    }

    /**
     * 新增用户详细信息
     * @throws URISyntaxException
     */
    @ApiOperation("新增用户详细信息 RequestBody")
    @PostMapping("/insert/information")
    @Timed
    public ResultObj insertInformation(@ApiParam(name="information",value="用户详细信息实体",required=true) @RequestBody BInformation information) throws URISyntaxException {
        return ResultObj.back(true,200,informationService.save(information));
    }

    /**
     * 新增用户联系方式
     * @throws URISyntaxException
     */
    @ApiOperation("新增用户联系方式 RequestBody")
    @PostMapping("/insert/contact")
    @Timed
    public ResultObj insertContact(@ApiParam(name="contact",value="用户联系方式实体",required=true) @RequestBody BContact contact) throws URISyntaxException {
        return ResultObj.back(true,200,contactService.save(contact));
    }

    /**
     * 新增用户荣誉奖项
     * @throws URISyntaxException
     */
    @ApiOperation("新增用户荣誉奖项 RequestBody")
    @PostMapping("/insert/honour")
    @Timed
    public ResultObj insertHonour(@ApiParam(name="honour",value="用户荣誉奖项实体",required=true) @RequestBody BHonour honour) throws URISyntaxException {
        return ResultObj.back(true,200,honourService.save(honour));
    }

    /**
     * 新增用户兴趣特长
     * @throws URISyntaxException
     */
    @ApiOperation("新增用户兴趣特长 RequestBody")
    @PostMapping("/insert/hobby")
    @Timed
    public ResultObj insertHobby(@ApiParam(name="hobby",value="用户兴趣特长实体",required=true) @RequestBody BHobby hobby) throws URISyntaxException {
        return ResultObj.back(true,200,hobbyService.save(hobby));
    }

    /**
     * 新增用户求职意向
     * @throws URISyntaxException
     */
    @ApiOperation("新增用户求职意向 RequestBody")
    @PostMapping("/insert/jobObjective")
    @Timed
    public ResultObj insertJobObjective(@ApiParam(name="jobObjective",value="用户求职意向实体",required=true) @RequestBody JobObjective jobObjective) throws URISyntaxException {
        return ResultObj.back(true,200,jobObjectiveService.save(jobObjective));
    }

    /**
     * 新增用户工作经历
     * @throws URISyntaxException
     */
    @ApiOperation("新增用户工作经历 RequestBody")
    @PostMapping("/insert/work")
    @Timed
    public ResultObj insertWork(@ApiParam(name="workProjectVM",value="用户工作经历实体",required=true) @RequestBody List<WorkProjectVM> workProjectVM) throws URISyntaxException {
        informationService.insertWorkExperience(workProjectVM);
        return ResultObj.back(true,200,true);
    }
}
