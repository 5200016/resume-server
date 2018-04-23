package com.resume.service;

import com.resume.domain.SUser;
import com.resume.web.rest.util.ResultObj;

import java.util.List;

/**
 * Service Interface for managing SUser.
 */
public interface SUserService {

    /**
     * Save a sUser.
     *
     * @param sUser the entity to save
     * @return the persisted entity
     */
    SUser save(SUser sUser);

    /**
     *  Get all the sUsers.
     *
     *  @return the list of entities
     */
    List<SUser> findAll();

    /**
     *  Get the "id" sUser.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SUser findOne(Long id);

    /**
     *  Delete the "id" sUser.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * 通过用户名查询用户基本信息
     */
    SUser findUserByUsername(String username);

}
