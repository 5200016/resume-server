package com.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.resume.domain.BWorkProject;
import com.resume.service.BWorkProjectService;
import com.resume.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BWorkProject.
 */
@RestController
@RequestMapping("/ap")
public class BWorkProjectResource {

    private final Logger log = LoggerFactory.getLogger(BWorkProjectResource.class);

    private static final String ENTITY_NAME = "bWorkProject";

    private final BWorkProjectService bWorkProjectService;

    public BWorkProjectResource(BWorkProjectService bWorkProjectService) {
        this.bWorkProjectService = bWorkProjectService;
    }

    /**
     * POST  /b-work-projects : Create a new bWorkProject.
     *
     * @param bWorkProject the bWorkProject to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bWorkProject, or with status 400 (Bad Request) if the bWorkProject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/b-work-projects")
    @Timed
    public ResponseEntity<BWorkProject> createBWorkProject(@RequestBody BWorkProject bWorkProject) throws URISyntaxException {
        log.debug("REST request to save BWorkProject : {}", bWorkProject);
        if (bWorkProject.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bWorkProject cannot already have an ID")).body(null);
        }
        BWorkProject result = bWorkProjectService.save(bWorkProject);
        return ResponseEntity.created(new URI("/api/b-work-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /b-work-projects : Updates an existing bWorkProject.
     *
     * @param bWorkProject the bWorkProject to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bWorkProject,
     * or with status 400 (Bad Request) if the bWorkProject is not valid,
     * or with status 500 (Internal Server Error) if the bWorkProject couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/b-work-projects")
    @Timed
    public ResponseEntity<BWorkProject> updateBWorkProject(@RequestBody BWorkProject bWorkProject) throws URISyntaxException {
        log.debug("REST request to update BWorkProject : {}", bWorkProject);
        if (bWorkProject.getId() == null) {
            return createBWorkProject(bWorkProject);
        }
        BWorkProject result = bWorkProjectService.save(bWorkProject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bWorkProject.getId().toString()))
            .body(result);
    }

    /**
     * GET  /b-work-projects : get all the bWorkProjects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bWorkProjects in body
     */
    @GetMapping("/b-work-projects")
    @Timed
    public List<BWorkProject> getAllBWorkProjects() {
        log.debug("REST request to get all BWorkProjects");
        return bWorkProjectService.findAll();
    }

    /**
     * GET  /b-work-projects/:id : get the "id" bWorkProject.
     *
     * @param id the id of the bWorkProject to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bWorkProject, or with status 404 (Not Found)
     */
    @GetMapping("/b-work-projects/{id}")
    @Timed
    public ResponseEntity<BWorkProject> getBWorkProject(@PathVariable Long id) {
        log.debug("REST request to get BWorkProject : {}", id);
        BWorkProject bWorkProject = bWorkProjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bWorkProject));
    }

    /**
     * DELETE  /b-work-projects/:id : delete the "id" bWorkProject.
     *
     * @param id the id of the bWorkProject to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/b-work-projects/{id}")
    @Timed
    public ResponseEntity<Void> deleteBWorkProject(@PathVariable Long id) {
        log.debug("REST request to delete BWorkProject : {}", id);
        bWorkProjectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
