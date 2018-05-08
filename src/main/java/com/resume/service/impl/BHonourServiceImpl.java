package com.resume.service.impl;

import com.resume.service.BHonourService;
import com.resume.domain.BHonour;
import com.resume.repository.BHonourRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing BHonour.
 */
@Service
@Transactional
public class BHonourServiceImpl implements BHonourService{

    private final Logger log = LoggerFactory.getLogger(BHonourServiceImpl.class);

    private final BHonourRepository bHonourRepository;

    public BHonourServiceImpl(BHonourRepository bHonourRepository) {
        this.bHonourRepository = bHonourRepository;
    }

    /**
     * Save a bHonour.
     *
     * @param bHonour the entity to save
     * @return the persisted entity
     */
    @Override
    public BHonour save(BHonour bHonour) {
        log.debug("Request to save BHonour : {}", bHonour);
        return bHonourRepository.save(bHonour);
    }

    /**
     *  Get all the bHonours.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BHonour> findAll() {
        log.debug("Request to get all BHonours");
        return bHonourRepository.findAll();
    }

    /**
     *  Get one bHonour by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BHonour findOne(Long id) {
        log.debug("Request to get BHonour : {}", id);
        return bHonourRepository.findOne(id);
    }

    /**
     *  Delete the  bHonour by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BHonour : {}", id);
        bHonourRepository.delete(id);
    }
}
