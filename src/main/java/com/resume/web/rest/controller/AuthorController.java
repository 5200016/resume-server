package com.resume.web.rest.controller;

import com.codahale.metrics.annotation.Timed;
import com.resume.domain.BAuthor;
import com.resume.service.BAuthorService;
import com.resume.web.rest.util.DateUtil;
import com.resume.web.rest.util.ResultObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

/**
 * User : 黄志成
 * Date : 2018/5/10
 * Desc : 代写管理
 */

@Api(description="代写管理")
@RestController
@RequestMapping("/api")
public class AuthorController {
    private final BAuthorService authorService;

    public AuthorController(BAuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * 新增代写作者（待审核状态）
     * @throws URISyntaxException
     */
    @ApiOperation("新增代写作者（待审核状态） RequestBody")
    @PostMapping("/insert/author")
    @Timed
    public ResultObj insertAuthor(@ApiParam(name="author",value="代写作者实体",required=true) @RequestBody BAuthor author) throws URISyntaxException {
        author.setIsActive(false);
        author.setUpdateTime(DateUtil.getZoneDateTime());
        author.setCreateTime(DateUtil.getZoneDateTime());
        return ResultObj.back(true,200,authorService.save(author));
    }

    /**
     * 审核代写作者
     * @throws URISyntaxException
     */
    @ApiOperation("审核代写作者 RequestParam")
    @PutMapping("/check/author")
    @Timed
    public ResultObj checkAuthor(@ApiParam(name="authorId",value="作者id",required=true) @RequestParam Long authorId) throws URISyntaxException {
        BAuthor author = authorService.findOne(authorId);
        author.setIsActive(true);
        author.setUpdateTime(DateUtil.getZoneDateTime());
        return ResultObj.back(true,200,authorService.save(author));
    }

    /**
     * 查询全部代写作者信息（并根据代写量，好评度排序  分页）
     */
    @ApiOperation("查询全部代写作者信息（并根据代写量，好评度排序  分页） RequestParam")
    @PostMapping("/select/author")
    @Timed
    public ResultObj selectAuthor(@ApiParam(name="pageNum",value="页码",required=true) @RequestParam Integer pageNum,
                                  @ApiParam(name="pageSize",value="数量",required=true) @RequestParam Integer pageSize) throws URISyntaxException {
        return ResultObj.back(true,200,authorService.findAuthor(pageNum, pageSize));
    }

    /**
     * 根据id查询代写作者信息
     */
    @ApiOperation("根据id查询代写作者信息 RequestParam")
    @PostMapping("/select/author/id")
    @Timed
    public ResultObj selectAuthor(@ApiParam(name="authorId",value="作者id",required=true) @RequestParam Long authorId) throws URISyntaxException {
        return ResultObj.back(true,200,authorService.findOne(authorId));
    }
}
