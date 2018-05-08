package com.resume.service;

import com.resume.domain.JobObjective;
import java.util.List;

/**
 * Service Interface for managing JobObjective.
 */
public interface JobObjectiveService {

    /**
     * Save a jobObjective.
     *
     * @param jobObjective the entity to save
     * @return the persisted entity
     */
    JobObjective save(JobObjective jobObjective);

    /**
     *  Get all the jobObjectives.
     *
     *  @return the list of entities
     */
    List<JobObjective> findAll();

    /**
     *  Get the "id" jobObjective.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    JobObjective findOne(Long id);

    /**
     *  Delete the "id" jobObjective.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
