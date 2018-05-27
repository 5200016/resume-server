package com.resume.service.impl;

import com.resume.service.BEducationService;
import com.resume.domain.BEducation;
import com.resume.repository.BEducationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing BEducation.
 */
@Service
@Transactional
public class BEducationServiceImpl implements BEducationService{

    private final Logger log = LoggerFactory.getLogger(BEducationServiceImpl.class);

    private final BEducationRepository bEducationRepository;

    public BEducationServiceImpl(BEducationRepository bEducationRepository) {
        this.bEducationRepository = bEducationRepository;
    }

    /**
     * Save a bEducation.
     *
     * @param bEducation the entity to save
     * @return the persisted entity
     */
    @Override
    public BEducation save(BEducation bEducation) {
        log.debug("Request to save BEducation : {}", bEducation);
        return bEducationRepository.save(bEducation);
    }

    /**
     *  Get all the bEducations.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BEducation> findAll() {
        log.debug("Request to get all BEducations");
        return bEducationRepository.findAll();
    }

    /**
     *  Get one bEducation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BEducation findOne(Long id) {
        log.debug("Request to get BEducation : {}", id);
        return bEducationRepository.findOne(id);
    }

    /**
     *  Delete the  bEducation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BEducation : {}", id);
        bEducationRepository.delete(id);
    }
}
