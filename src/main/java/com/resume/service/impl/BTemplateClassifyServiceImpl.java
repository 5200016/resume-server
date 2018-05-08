package com.resume.service.impl;

import com.resume.service.BTemplateClassifyService;
import com.resume.domain.BTemplateClassify;
import com.resume.repository.BTemplateClassifyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing BTemplateClassify.
 */
@Service
@Transactional
public class BTemplateClassifyServiceImpl implements BTemplateClassifyService{

    private final Logger log = LoggerFactory.getLogger(BTemplateClassifyServiceImpl.class);

    private final BTemplateClassifyRepository bTemplateClassifyRepository;

    public BTemplateClassifyServiceImpl(BTemplateClassifyRepository bTemplateClassifyRepository) {
        this.bTemplateClassifyRepository = bTemplateClassifyRepository;
    }

    /**
     * Save a bTemplateClassify.
     *
     * @param bTemplateClassify the entity to save
     * @return the persisted entity
     */
    @Override
    public BTemplateClassify save(BTemplateClassify bTemplateClassify) {
        log.debug("Request to save BTemplateClassify : {}", bTemplateClassify);
        return bTemplateClassifyRepository.save(bTemplateClassify);
    }

    /**
     *  Get all the bTemplateClassifies.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BTemplateClassify> findAll() {
        log.debug("Request to get all BTemplateClassifies");
        return bTemplateClassifyRepository.findAll();
    }

    /**
     *  Get one bTemplateClassify by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BTemplateClassify findOne(Long id) {
        log.debug("Request to get BTemplateClassify : {}", id);
        return bTemplateClassifyRepository.findOne(id);
    }

    /**
     *  Delete the  bTemplateClassify by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BTemplateClassify : {}", id);
        bTemplateClassifyRepository.delete(id);
    }
}
