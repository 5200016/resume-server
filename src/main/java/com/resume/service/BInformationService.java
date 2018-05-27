package com.resume.service;

import com.resume.domain.*;
import com.resume.service.dto.InfoDTO;
import com.resume.web.rest.vm.WorkProjectVM;

import java.util.List;

/**
 * Service Interface for managing BInformation.
 */
public interface BInformationService {

    /**
     * Save a bInformation.
     *
     * @param bInformation the entity to save
     * @return the persisted entity
     */
    BInformation save(BInformation bInformation);

    /**
     *  Get all the bInformations.
     *
     *  @return the list of entities
     */
    List<BInformation> findAll();

    /**
     *  Get the "id" bInformation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BInformation findOne(Long id);

    /**
     *  Delete the "id" bInformation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * 查询用户详细信息
     */
    InfoDTO findUserInformation(String username);

    /**
     * 新增用户工作经历
     */
    void insertWorkExperience(List<WorkProjectVM> workProjects);

    /**
     * 通过用户账号查询详细信息
     */
    BInformation findInformationByUsername(String username);

    /**
     * 通过用户账号查询联系方式
     */
    BContact findContactByUsername(String username);

    /**
     * 通过用户账号查询教育背景
     */
    BEducation findEducationByUsername(String username);

    /**
     * 通过用户账号查询工作经验
     */
    List<BWork> findWorkByUsername(String username);

    /**
     * 通过用户账号查询项目经验
     */
    List<BWorkProject> findWorkProjectByUsername(String username);

    /**
     * 通过用户账号查询自我评价
     */
    BSelf findSelfByUsername(String username);

    /**
     * 通过用户账号查询兴趣爱好
     */
    BHobby findHobbyByUsername(String username);

    /**
     * 通过用户账号查询荣誉奖项
     */
    BHonour findHonourByUsername(String username);

    /**
     * 通过用户账号查询求职意向
     */
    JobObjective findJobObjectiveByUsername(String username);

}
