package com.resume.service.impl;

import com.resume.service.SUserService;
import com.resume.domain.SUser;
import com.resume.repository.SUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing SUser.
 */
@Service
@Transactional
public class SUserServiceImpl implements SUserService{

    private final Logger log = LoggerFactory.getLogger(SUserServiceImpl.class);

    private final SUserRepository sUserRepository;

    public SUserServiceImpl(SUserRepository sUserRepository) {
        this.sUserRepository = sUserRepository;
    }


    /**
     * Save a sUser.
     *
     * @param sUser the entity to save
     * @return the persisted entity
     */
    @Override
    public SUser save(SUser sUser) {
        log.debug("Request to save SUser : {}", sUser);
        return sUserRepository.save(sUser);
    }

    /**
     *  Get all the sUsers.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SUser> findAll() {
        log.debug("Request to get all SUsers");
        return sUserRepository.findAll();
    }

    /**
     *  Get one sUser by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SUser findOne(Long id) {
        log.debug("Request to get SUser : {}", id);
        return sUserRepository.findOne(id);
    }

    /**
     *  Delete the  sUser by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SUser : {}", id);
        sUserRepository.delete(id);
    }

    /**
     * 通过用户名查询用户基本信息
     */
    @Override
    public SUser findUserByUsername(String username) {
        return sUserRepository.findUserByUsername(username);
    }
}
