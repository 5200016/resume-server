package com.resume.service;

import com.resume.domain.BInformation;
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
}
