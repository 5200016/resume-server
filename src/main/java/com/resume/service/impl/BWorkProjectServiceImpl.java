package com.resume.service.impl;

import com.resume.service.BWorkProjectService;
import com.resume.domain.BWorkProject;
import com.resume.repository.BWorkProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing BWorkProject.
 */
@Service
@Transactional
public class BWorkProjectServiceImpl implements BWorkProjectService{

    private final Logger log = LoggerFactory.getLogger(BWorkProjectServiceImpl.class);

    private final BWorkProjectRepository bWorkProjectRepository;

    public BWorkProjectServiceImpl(BWorkProjectRepository bWorkProjectRepository) {
        this.bWorkProjectRepository = bWorkProjectRepository;
    }

    /**
     * Save a bWorkProject.
     *
     * @param bWorkProject the entity to save
     * @return the persisted entity
     */
    @Override
    public BWorkProject save(BWorkProject bWorkProject) {
        log.debug("Request to save BWorkProject : {}", bWorkProject);
        return bWorkProjectRepository.save(bWorkProject);
    }

    /**
     *  Get all the bWorkProjects.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BWorkProject> findAll() {
        log.debug("Request to get all BWorkProjects");
        return bWorkProjectRepository.findAll();
    }

    /**
     *  Get one bWorkProject by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BWorkProject findOne(Long id) {
        log.debug("Request to get BWorkProject : {}", id);
        return bWorkProjectRepository.findOne(id);
    }

    /**
     *  Delete the  bWorkProject by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BWorkProject : {}", id);
        bWorkProjectRepository.delete(id);
    }
}
