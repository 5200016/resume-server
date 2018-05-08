package com.resume.service;

import com.resume.domain.BWorkProject;
import java.util.List;

/**
 * Service Interface for managing BWorkProject.
 */
public interface BWorkProjectService {

    /**
     * Save a bWorkProject.
     *
     * @param bWorkProject the entity to save
     * @return the persisted entity
     */
    BWorkProject save(BWorkProject bWorkProject);

    /**
     *  Get all the bWorkProjects.
     *
     *  @return the list of entities
     */
    List<BWorkProject> findAll();

    /**
     *  Get the "id" bWorkProject.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BWorkProject findOne(Long id);

    /**
     *  Delete the "id" bWorkProject.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
