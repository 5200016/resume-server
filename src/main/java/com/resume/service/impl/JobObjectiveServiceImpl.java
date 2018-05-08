package com.resume.service.impl;

import com.resume.service.JobObjectiveService;
import com.resume.domain.JobObjective;
import com.resume.repository.JobObjectiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing JobObjective.
 */
@Service
@Transactional
public class JobObjectiveServiceImpl implements JobObjectiveService{

    private final Logger log = LoggerFactory.getLogger(JobObjectiveServiceImpl.class);

    private final JobObjectiveRepository jobObjectiveRepository;

    public JobObjectiveServiceImpl(JobObjectiveRepository jobObjectiveRepository) {
        this.jobObjectiveRepository = jobObjectiveRepository;
    }

    /**
     * Save a jobObjective.
     *
     * @param jobObjective the entity to save
     * @return the persisted entity
     */
    @Override
    public JobObjective save(JobObjective jobObjective) {
        log.debug("Request to save JobObjective : {}", jobObjective);
        return jobObjectiveRepository.save(jobObjective);
    }

    /**
     *  Get all the jobObjectives.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<JobObjective> findAll() {
        log.debug("Request to get all JobObjectives");
        return jobObjectiveRepository.findAll();
    }

    /**
     *  Get one jobObjective by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public JobObjective findOne(Long id) {
        log.debug("Request to get JobObjective : {}", id);
        return jobObjectiveRepository.findOne(id);
    }

    /**
     *  Delete the  jobObjective by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete JobObjective : {}", id);
        jobObjectiveRepository.delete(id);
    }
}
