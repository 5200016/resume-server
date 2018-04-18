package com.resume.service;

import com.resume.domain.SRole;
import java.util.List;

/**
 * Service Interface for managing SRole.
 */
public interface SRoleService {

    /**
     * Save a sRole.
     *
     * @param sRole the entity to save
     * @return the persisted entity
     */
    SRole save(SRole sRole);

    /**
     *  Get all the sRoles.
     *
     *  @return the list of entities
     */
    List<SRole> findAll();

    /**
     *  Get the "id" sRole.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SRole findOne(Long id);

    /**
     *  Delete the "id" sRole.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
