package com.resume.service.impl;

import com.resume.service.BSelfService;
import com.resume.domain.BSelf;
import com.resume.repository.BSelfRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing BSelf.
 */
@Service
@Transactional
public class BSelfServiceImpl implements BSelfService{

    private final Logger log = LoggerFactory.getLogger(BSelfServiceImpl.class);

    private final BSelfRepository bSelfRepository;

    public BSelfServiceImpl(BSelfRepository bSelfRepository) {
        this.bSelfRepository = bSelfRepository;
    }

    /**
     * Save a bSelf.
     *
     * @param bSelf the entity to save
     * @return the persisted entity
     */
    @Override
    public BSelf save(BSelf bSelf) {
        log.debug("Request to save BSelf : {}", bSelf);
        return bSelfRepository.save(bSelf);
    }

    /**
     *  Get all the bSelves.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BSelf> findAll() {
        log.debug("Request to get all BSelves");
        return bSelfRepository.findAll();
    }

    /**
     *  Get one bSelf by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BSelf findOne(Long id) {
        log.debug("Request to get BSelf : {}", id);
        return bSelfRepository.findOne(id);
    }

    /**
     *  Delete the  bSelf by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BSelf : {}", id);
        bSelfRepository.delete(id);
    }
}
