package com.resume.service.impl;

import com.resume.service.SLoginService;
import com.resume.domain.SLogin;
import com.resume.repository.SLoginRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing SLogin.
 */
@Service
@Transactional
public class SLoginServiceImpl implements SLoginService{

    private final Logger log = LoggerFactory.getLogger(SLoginServiceImpl.class);

    private final SLoginRepository sLoginRepository;

    public SLoginServiceImpl(SLoginRepository sLoginRepository) {
        this.sLoginRepository = sLoginRepository;
    }

    /**
     * Save a sLogin.
     *
     * @param sLogin the entity to save
     * @return the persisted entity
     */
    @Override
    public SLogin save(SLogin sLogin) {
        log.debug("Request to save SLogin : {}", sLogin);
        return sLoginRepository.save(sLogin);
    }

    /**
     *  Get all the sLogins.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SLogin> findAll() {
        log.debug("Request to get all SLogins");
        return sLoginRepository.findAll();
    }

    /**
     *  Get one sLogin by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SLogin findOne(Long id) {
        log.debug("Request to get SLogin : {}", id);
        return sLoginRepository.findOne(id);
    }

    /**
     *  Delete the  sLogin by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SLogin : {}", id);
        sLoginRepository.delete(id);
    }
}
