package com.resume.service.impl;

import com.resume.service.BResumeService;
import com.resume.domain.BResume;
import com.resume.repository.BResumeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing BResume.
 */
@Service
@Transactional
public class BResumeServiceImpl implements BResumeService{

    private final Logger log = LoggerFactory.getLogger(BResumeServiceImpl.class);

    private final BResumeRepository bResumeRepository;

    public BResumeServiceImpl(BResumeRepository bResumeRepository) {
        this.bResumeRepository = bResumeRepository;
    }

    /**
     * Save a bResume.
     *
     * @param bResume the entity to save
     * @return the persisted entity
     */
    @Override
    public BResume save(BResume bResume) {
        log.debug("Request to save BResume : {}", bResume);
        return bResumeRepository.save(bResume);
    }

    /**
     *  Get all the bResumes.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BResume> findAll() {
        log.debug("Request to get all BResumes");
        return bResumeRepository.findAll();
    }

    /**
     *  Get one bResume by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BResume findOne(Long id) {
        log.debug("Request to get BResume : {}", id);
        return bResumeRepository.findOne(id);
    }

    /**
     *  Delete the  bResume by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BResume : {}", id);
        bResumeRepository.delete(id);
    }
}
