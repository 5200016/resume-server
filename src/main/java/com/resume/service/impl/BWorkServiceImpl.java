package com.resume.service.impl;

import com.resume.service.BWorkService;
import com.resume.domain.BWork;
import com.resume.repository.BWorkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing BWork.
 */
@Service
@Transactional
public class BWorkServiceImpl implements BWorkService{

    private final Logger log = LoggerFactory.getLogger(BWorkServiceImpl.class);

    private final BWorkRepository bWorkRepository;

    public BWorkServiceImpl(BWorkRepository bWorkRepository) {
        this.bWorkRepository = bWorkRepository;
    }

    /**
     * Save a bWork.
     *
     * @param bWork the entity to save
     * @return the persisted entity
     */
    @Override
    public BWork save(BWork bWork) {
        log.debug("Request to save BWork : {}", bWork);
        return bWorkRepository.save(bWork);
    }

    /**
     *  Get all the bWorks.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BWork> findAll() {
        log.debug("Request to get all BWorks");
        return bWorkRepository.findAll();
    }

    /**
     *  Get one bWork by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BWork findOne(Long id) {
        log.debug("Request to get BWork : {}", id);
        return bWorkRepository.findOne(id);
    }

    /**
     *  Delete the  bWork by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BWork : {}", id);
        bWorkRepository.delete(id);
    }
}
