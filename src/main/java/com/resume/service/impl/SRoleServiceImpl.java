package com.resume.service.impl;

import com.resume.service.SRoleService;
import com.resume.domain.SRole;
import com.resume.repository.SRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing SRole.
 */
@Service
@Transactional
public class SRoleServiceImpl implements SRoleService{

    private final Logger log = LoggerFactory.getLogger(SRoleServiceImpl.class);

    private final SRoleRepository sRoleRepository;

    public SRoleServiceImpl(SRoleRepository sRoleRepository) {
        this.sRoleRepository = sRoleRepository;
    }

    /**
     * Save a sRole.
     *
     * @param sRole the entity to save
     * @return the persisted entity
     */
    @Override
    public SRole save(SRole sRole) {
        log.debug("Request to save SRole : {}", sRole);
        return sRoleRepository.save(sRole);
    }

    /**
     *  Get all the sRoles.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SRole> findAll() {
        log.debug("Request to get all SRoles");
        return sRoleRepository.findAll();
    }

    /**
     *  Get one sRole by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SRole findOne(Long id) {
        log.debug("Request to get SRole : {}", id);
        return sRoleRepository.findOne(id);
    }

    /**
     *  Delete the  sRole by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SRole : {}", id);
        sRoleRepository.delete(id);
    }
}
