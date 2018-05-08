package com.resume.service;

import com.resume.domain.BResume;
import java.util.List;

/**
 * Service Interface for managing BResume.
 */
public interface BResumeService {

    /**
     * Save a bResume.
     *
     * @param bResume the entity to save
     * @return the persisted entity
     */
    BResume save(BResume bResume);

    /**
     *  Get all the bResumes.
     *
     *  @return the list of entities
     */
    List<BResume> findAll();

    /**
     *  Get the "id" bResume.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BResume findOne(Long id);

    /**
     *  Delete the "id" bResume.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
