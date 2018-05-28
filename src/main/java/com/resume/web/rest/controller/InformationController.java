package com.resume.web.rest.controller;

import com.codahale.metrics.annotation.Timed;
import com.resume.domain.*;
import com.resume.service.*;
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
    private final BEducationService educationService;
    private final BSelfService selfService;

    public InformationController(BInformationService informationService, BContactService contactService, BWorkProjectService workProjectService, BWorkService workService, BHonourService honourService, BHobbyService hobbyService, JobObjectiveService jobObjectiveService, BEducationService educationService, BSelfService selfService) {
        this.informationService = informationService;
        this.contactService = contactService;
        this.workProjectService = workProjectService;
        this.workService = workService;
        this.honourService = honourService;
        this.hobbyService = hobbyService;
        this.jobObjectiveService = jobObjectiveService;
        this.educationService = educationService;
        this.selfService = selfService;
    }

    /**
     * 新增用户详细信息
     * @throws URISyntaxException
     */
    @ApiOperation("新增用户详细信息 RequestBody")
    @PostMapping("/insert/information")
    @Timed
    public ResultObj insertInformation(@ApiParam(name="information",value="用户详细信息实体",required=true) @RequestBody BInformation information)
        throws URISyntaxException {
        information.setIsActive(true);
        information.setUpdateTime(DateUtil.getZoneDateTime());
        information.setCreateTime(DateUtil.getZoneDateTime());
        return ResultObj.back(true,200,informationService.save(information));
    }

    /**
     * 修改用户详细信息
     * @throws URISyntaxException
     */
    @ApiOperation("修改用户详细信息 RequestBody")
    @PutMapping("/update/information")
    @Timed
    public ResultObj updateInformation(@ApiParam(name="information",value="用户详细信息实体",required=true) @RequestBody BInformation information)
        throws URISyntaxException {
        if(TypeUtils.isEmpty(information.getId())){
            return ResultObj.backInfo(true,200,"修改失败",null);
        }
        information.setUpdateTime(DateUtil.getZoneDateTime());
        return ResultObj.back(true,200,informationService.save(information));
    }

    /**
     * 新增用户联系方式
     * @throws URISyntaxException
     */
    @ApiOperation("新增用户联系方式 RequestBody")
    @PostMapping("/insert/contact")
    @Timed
    public ResultObj insertContact(@ApiParam(name="contact",value="用户联系方式实体",required=true) @RequestBody BContact contact)
        throws URISyntaxException {
        contact.setIsActive(true);
        contact.setUpdateTime(DateUtil.getZoneDateTime());
        contact.setCreateTime(DateUtil.getZoneDateTime());
        return ResultObj.back(true,200,contactService.save(contact));
    }

    /**
     * 修改用户联系方式
     * @throws URISyntaxException
     */
    @ApiOperation("修改用户联系方式 RequestBody")
    @PutMapping("/update/contact")
    @Timed
    public ResultObj updateContact(@ApiParam(name="contact",value="用户联系方式实体",required=true) @RequestBody BContact contact)
        throws URISyntaxException {
        if(TypeUtils.isEmpty(contact.getId())){
            return ResultObj.backInfo(true,200,"修改失败",null);
        }
        contact.setUpdateTime(DateUtil.getZoneDateTime());
        return ResultObj.back(true,200,contactService.save(contact));
    }

    /**
     * 新增用户荣誉奖项
     * @throws URISyntaxException
     */
    @ApiOperation("新增用户荣誉奖项 RequestBody")
    @PostMapping("/insert/honour")
    @Timed
    public ResultObj insertHonour(@ApiParam(name="honour",value="用户荣誉奖项实体",required=true) @RequestBody BHonour honour)
        throws URISyntaxException {
        honour.setIsActive(true);
        honour.setUpdateTime(DateUtil.getZoneDateTime());
        honour.setCreateTime(DateUtil.getZoneDateTime());
        return ResultObj.back(true,200,honourService.save(honour));
    }

    /**
     * 修改用户荣誉奖项
     * @throws URISyntaxException
     */
    @ApiOperation("修改用户荣誉奖项 RequestBody")
    @PutMapping("/update/honour")
    @Timed
    public ResultObj updateHonour(@ApiParam(name="honour",value="用户荣誉奖项实体",required=true) @RequestBody BHonour honour)
        throws URISyntaxException {
        if(TypeUtils.isEmpty(honour.getId())){
            return ResultObj.backInfo(true,200,"修改失败",null);
        }
        honour.setUpdateTime(DateUtil.getZoneDateTime());
        return ResultObj.back(true,200,honourService.save(honour));
    }

    /**
     * 新增用户兴趣特长
     * @throws URISyntaxException
     */
    @ApiOperation("新增用户兴趣特长 RequestBody")
    @PostMapping("/insert/hobby")
    @Timed
    public ResultObj insertHobby(@ApiParam(name="hobby",value="用户兴趣特长实体",required=true) @RequestBody BHobby hobby)
        throws URISyntaxException {
        hobby.setIsActive(true);
        hobby.setUpdateTime(DateUtil.getZoneDateTime());
        hobby.setCreateTime(DateUtil.getZoneDateTime());
        return ResultObj.back(true,200,hobbyService.save(hobby));
    }

    /**
     * 修改用户兴趣特长
     * @throws URISyntaxException
     */
    @ApiOperation("修改用户兴趣特长 RequestBody")
    @PutMapping("/update/hobby")
    @Timed
    public ResultObj updateHobby(@ApiParam(name="hobby",value="用户兴趣特长实体",required=true) @RequestBody BHobby hobby)
        throws URISyntaxException {
        if(TypeUtils.isEmpty(hobby.getId())){
            return ResultObj.backInfo(true,200,"修改失败",null);
        }
        hobby.setUpdateTime(DateUtil.getZoneDateTime());
        return ResultObj.back(true,200,hobbyService.save(hobby));
    }

    /**
     * 新增用户求职意向
     * @throws URISyntaxException
     */
    @ApiOperation("新增用户求职意向 RequestBody")
    @PostMapping("/insert/jobObjective")
    @Timed
    public ResultObj insertJobObjective(@ApiParam(name="jobObjective",value="用户求职意向实体",required=true) @RequestBody JobObjective jobObjective)
        throws URISyntaxException {
        jobObjective.setIsActive(true);
        jobObjective.setUpdateTime(DateUtil.getZoneDateTime());
        jobObjective.setCreateTime(DateUtil.getZoneDateTime());
        return ResultObj.back(true,200,jobObjectiveService.save(jobObjective));
    }

    /**
     * 修改用户求职意向
     * @throws URISyntaxException
     */
    @ApiOperation("修改用户求职意向 RequestBody")
    @PutMapping("/update/jobObjective")
    @Timed
    public ResultObj updateJobObjective(@ApiParam(name="jobObjective",value="用户求职意向实体",required=true) @RequestBody JobObjective jobObjective)
        throws URISyntaxException {
        if(TypeUtils.isEmpty(jobObjective.getId())){
            return ResultObj.backInfo(true,200,"修改失败",null);
        }
        jobObjective.setUpdateTime(DateUtil.getZoneDateTime());
        return ResultObj.back(true,200,jobObjectiveService.save(jobObjective));
    }

    /**
     * 新增用户工作经验
     * @throws URISyntaxException
     */
    @ApiOperation("新增用户工作经验 RequestBody")
    @PostMapping("/insert/work")
    @Timed
    public ResultObj insertWork(@ApiParam(name="work",value="用户工作经验实体",required=true) @RequestBody BWork work)
        throws URISyntaxException {
        work.setIsActive(true);
        work.setCreateTime(DateUtil.getZoneDateTime());
        work.setUpdateTime(DateUtil.getZoneDateTime());
        return ResultObj.back(true,200,workService.save(work));
    }

    /**
     * 修改用户工作经验
     * @throws URISyntaxException
     */
    @ApiOperation("修改用户工作经验 RequestBody")
    @PutMapping("/update/work")
    @Timed
    public ResultObj updateWork(@ApiParam(name="work",value="用户工作经验实体",required=true) @RequestBody BWork work)
        throws URISyntaxException {
        if(TypeUtils.isEmpty(work.getId())){
            return ResultObj.backInfo(true,200,"修改失败",null);
        }
        work.setUpdateTime(DateUtil.getZoneDateTime());
        return ResultObj.back(true,200,workService.save(work));
    }

    /**
     * 新增用户项目经验
     * @throws URISyntaxException
     */
    @ApiOperation("新增用户项目经验 RequestBody")
    @PostMapping("/insert/workProject")
    @Timed
    public ResultObj insertWorkProject(@ApiParam(name="workProject",value="用户项目经验实体",required=true) @RequestBody BWorkProject workProject)
        throws URISyntaxException {
        workProject.setIsActive(true);
        workProject.setUpdateTime(DateUtil.getZoneDateTime());
        workProject.setCreateTime(DateUtil.getZoneDateTime());
        return ResultObj.back(true,200,workProjectService.save(workProject));
    }

    /**
     * 修改用户项目经验
     * @throws URISyntaxException
     */
    @ApiOperation("修改用户项目经验 RequestBody")
    @PutMapping("/update/workProject")
    @Timed
    public ResultObj updateWorkProject(@ApiParam(name="workProject",value="用户项目经验实体",required=true) @RequestBody BWorkProject workProject)
        throws URISyntaxException {
        if(TypeUtils.isEmpty(workProject.getId())){
            return ResultObj.backInfo(true,200,"修改失败",null);
        }
        workProject.setUpdateTime(DateUtil.getZoneDateTime());
        return ResultObj.back(true,200,workProjectService.save(workProject));
    }

    /**
     * 新增用户自我评价
     * @throws URISyntaxException
     */
    @ApiOperation("新增用户自我评价 RequestBody")
    @PostMapping("/insert/self")
    @Timed
    public ResultObj insertSelf(@ApiParam(name="self",value="用户自我评价实体",required=true) @RequestBody BSelf self)
        throws URISyntaxException {
        self.setIsActive(true);
        self.setUpdateTime(DateUtil.getZoneDateTime());
        self.setCreateTime(DateUtil.getZoneDateTime());
        return ResultObj.back(true,200,selfService.save(self));
    }

    /**
     * 修改用户自我评价
     * @throws URISyntaxException
     */
    @ApiOperation("修改用户自我评价 RequestBody")
    @PutMapping("/update/self")
    @Timed
    public ResultObj updateSelf(@ApiParam(name="self",value="用户自我评价实体",required=true) @RequestBody BSelf self)
        throws URISyntaxException {
        if(TypeUtils.isEmpty(self.getId())){
            return ResultObj.backInfo(true,200,"修改失败",null);
        }
        self.setUpdateTime(DateUtil.getZoneDateTime());
        return ResultObj.back(true,200,selfService.save(self));
    }

    /**
     * 新增用户教育背景
     * @throws URISyntaxException
     */
    @ApiOperation("新增用户教育背景 RequestBody")
    @PostMapping("/insert/education")
    @Timed
    public ResultObj insertEducation(@ApiParam(name="education",value="用户教育背景实体",required=true) @RequestBody BEducation education)
        throws URISyntaxException {
        education.setIsActive(true);
        education.setUpdateTime(DateUtil.getZoneDateTime());
        education.setCreateTime(DateUtil.getZoneDateTime());
        return ResultObj.back(true,200,educationService.save(education));
    }

    /**
     * 修改用户教育背景
     * @throws URISyntaxException
     */
    @ApiOperation("修改用户教育背景 RequestBody")
    @PutMapping("/update/education")
    @Timed
    public ResultObj updateEducation(@ApiParam(name="education",value="用户教育背景实体",required=true) @RequestBody BEducation education)
        throws URISyntaxException {
        if(TypeUtils.isEmpty(education.getId())){
            return ResultObj.backInfo(true,200,"修改失败",null);
        }
        education.setUpdateTime(DateUtil.getZoneDateTime());
        return ResultObj.back(true,200,educationService.save(education));
    }

    /**
     * 通过用户账号查询详细信息
     */
    @ApiOperation("通过用户账号查询详细信息 PathVariable")
    @GetMapping("/select/information/{username}")
    @Timed
    public ResultObj selectInformationByUsername(@ApiParam(name="username",value="用户名",required=true) @PathVariable String username) throws URISyntaxException {
        return ResultObj.back(true,200,informationService.findInformationByUsername(username));
    }


    /**
     * 通过用户账号查询联系方式
     */
    @ApiOperation("通过用户账号查询联系方式 PathVariable")
    @GetMapping("/select/contact/{username}")
    @Timed
    public ResultObj selectContactByUsername(@ApiParam(name="username",value="用户名",required=true) @PathVariable String username) throws URISyntaxException {
        return ResultObj.back(true,200,informationService.findContactByUsername(username));
    }

    /**
     * 通过用户账号查询教育背景
     */
    @ApiOperation("通过用户账号查询教育背景 PathVariable")
    @GetMapping("/select/education/{username}")
    @Timed
    public ResultObj selectEducationByUsername(@ApiParam(name="username",value="用户名",required=true) @PathVariable String username) throws URISyntaxException {
        return ResultObj.back(true,200,informationService.findEducationByUsername(username));
    }

    /**
     * 通过用户账号查询工作经验
     */
    @ApiOperation("通过用户账号查询工作经验 PathVariable")
    @GetMapping("/select/work/{username}")
    @Timed
    public ResultObj selectWorkByUsername(@ApiParam(name="username",value="用户名",required=true) @PathVariable String username) throws URISyntaxException {
        return ResultObj.back(true,200,informationService.findWorkByUsername(username));
    }

    /**
     * 删除用户工作经验
     */
    @ApiOperation("删除用户工作经验 PathVariable")
    @DeleteMapping("/delete/work/{id}")
    @Timed
    public ResultObj deleteWorkById(@ApiParam(name="id",value="主键id",required=true) @PathVariable Long id) throws URISyntaxException {
        workService.delete(id);
        return ResultObj.back(true,200,true);
    }

    /**
     * 通过用户账号查询项目经验
     */
    @ApiOperation("通过用户账号查询项目经验 PathVariable")
    @GetMapping("/select/workProject/{username}")
    @Timed
    public ResultObj selectWorkProjectByUsername(@ApiParam(name="username",value="用户名",required=true) @PathVariable String username) throws URISyntaxException {
        return ResultObj.back(true,200,informationService.findWorkProjectByUsername(username));
    }

    /**
     * 删除用户项目经验
     */
    @ApiOperation("删除用户项目经验 PathVariable")
    @DeleteMapping("/delete/workProject/{id}")
    @Timed
    public ResultObj deleteWorkProjectById(@ApiParam(name="id",value="主键id",required=true) @PathVariable Long id) throws URISyntaxException {
        workProjectService.delete(id);
        return ResultObj.back(true,200,true);
    }

    /**
     * 通过用户账号查询自我评价
     */
    @ApiOperation("通过用户账号查询自我评价 PathVariable")
    @GetMapping("/select/self/{username}")
    @Timed
    public ResultObj selectSelfByUsername(@ApiParam(name="username",value="用户名",required=true) @PathVariable String username) throws URISyntaxException {
        return ResultObj.back(true,200,informationService.findSelfByUsername(username));
    }

    /**
     * 通过用户账号查询兴趣爱好
     */
    @ApiOperation("通过用户账号查询兴趣爱好 PathVariable")
    @GetMapping("/select/hobby/{username}")
    @Timed
    public ResultObj selectHobbyByUsername(@ApiParam(name="username",value="用户名",required=true) @PathVariable String username) throws URISyntaxException {
        return ResultObj.back(true,200,informationService.findHobbyByUsername(username));
    }

    /**
     * 通过用户账号查询荣誉奖项
     */
    @ApiOperation("通过用户账号查询荣誉奖项 PathVariable")
    @GetMapping("/select/honour/{username}")
    @Timed
    public ResultObj selectHonourByUsername(@ApiParam(name="username",value="用户名",required=true) @PathVariable String username) throws URISyntaxException {
        return ResultObj.back(true,200,informationService.findHonourByUsername(username));
    }

    /**
     * 通过用户账号查询求职意向
     */
    @ApiOperation("通过用户账号查询求职意向 PathVariable")
    @GetMapping("/select/jobObjective/{username}")
    @Timed
    public ResultObj selectJobObjectiveByUsername(@ApiParam(name="username",value="用户名",required=true) @PathVariable String username) throws URISyntaxException {
        return ResultObj.back(true,200,informationService.findJobObjectiveByUsername(username));
    }
}
