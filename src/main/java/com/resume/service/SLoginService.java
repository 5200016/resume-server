package com.resume.service;

import com.resume.domain.SLogin;
import com.resume.web.rest.util.ResultObj;

import java.util.List;

/**
 * Service Interface for managing SLogin.
 */
public interface SLoginService {

    /**
     * Save a sLogin.
     *
     * @param sLogin the entity to save
     * @return the persisted entity
     */
    SLogin save(SLogin sLogin);

    /**
     *  Get all the sLogins.
     *
     *  @return the list of entities
     */
    List<SLogin> findAll();

    /**
     *  Get the "id" sLogin.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SLogin findOne(Long id);

    /**
     *  Delete the "id" sLogin.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * 用户注册
     */
    ResultObj registerUser(SLogin login);

    /**
     * 通过用户名查询用户登录信息
     */
    SLogin findUserByUsername(String username);

    /**
     * 修改密码
     */
    ResultObj updatePassword(String username, String newPwd, String oldPwd);

}
