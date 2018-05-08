package com.resume.service.impl;

import com.resume.service.BTemplateService;
import com.resume.domain.BTemplate;
import com.resume.repository.BTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing BTemplate.
 */
@Service
@Transactional
public class BTemplateServiceImpl implements BTemplateService{

    private final Logger log = LoggerFactory.getLogger(BTemplateServiceImpl.class);

    private final BTemplateRepository bTemplateRepository;

    public BTemplateServiceImpl(BTemplateRepository bTemplateRepository) {
        this.bTemplateRepository = bTemplateRepository;
    }

    /**
     * Save a bTemplate.
     *
     * @param bTemplate the entity to save
     * @return the persisted entity
     */
    @Override
    public BTemplate save(BTemplate bTemplate) {
        log.debug("Request to save BTemplate : {}", bTemplate);
        return bTemplateRepository.save(bTemplate);
    }

    /**
     *  Get all the bTemplates.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BTemplate> findAll() {
        log.debug("Request to get all BTemplates");
        return bTemplateRepository.findAllWithEagerRelationships();
    }

    /**
     *  Get one bTemplate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BTemplate findOne(Long id) {
        log.debug("Request to get BTemplate : {}", id);
        return bTemplateRepository.findOneWithEagerRelationships(id);
    }

    /**
     *  Delete the  bTemplate by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BTemplate : {}", id);
        bTemplateRepository.delete(id);
    }
}
