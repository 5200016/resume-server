package com.resume.web.rest.controller;

import com.codahale.metrics.annotation.Timed;
import com.resume.service.BTemplateService;
import com.resume.web.rest.util.ResultObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

/**
 * User : 黄志成
 * Date : 2018/5/10
 * Desc : 模板管理
 */

@Api(description="模板管理")
@RestController
@RequestMapping("/api")
public class TemplateController {
    private final BTemplateService templateService;

    public TemplateController(BTemplateService templateService) {
        this.templateService = templateService;
    }

    /**
     * 根据分类id查询相关模板（分页）
     * @throws URISyntaxException
     */
    @ApiOperation("根据分类id查询相关模板（分页） RequestBody")
    @PostMapping("/select/template")
    @Timed
    public ResultObj selectTemplate(@ApiParam(name="classifyId",value="分类id（null：查全部）",required=true) @RequestParam Long classifyId,
                                    @ApiParam(name="name",value="模板名称",required=true) @RequestParam String name,
                                    @ApiParam(name="pageNum",value="页码",required=true) @RequestParam Integer pageNum,
                                    @ApiParam(name="pageSize",value="数量",required=true) @RequestParam Integer pageSize) throws URISyntaxException {
        return ResultObj.back(true,200,templateService.findTemplate(classifyId, name, pageNum, pageSize));
    }
}
