package com.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.resume.domain.BWork;
import com.resume.service.BWorkService;
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
 * REST controller for managing BWork.
 */
@RestController
@RequestMapping("/ap")
public class BWorkResource {

    private final Logger log = LoggerFactory.getLogger(BWorkResource.class);

    private static final String ENTITY_NAME = "bWork";

    private final BWorkService bWorkService;

    public BWorkResource(BWorkService bWorkService) {
        this.bWorkService = bWorkService;
    }

    /**
     * POST  /b-works : Create a new bWork.
     *
     * @param bWork the bWork to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bWork, or with status 400 (Bad Request) if the bWork has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/b-works")
    @Timed
    public ResponseEntity<BWork> createBWork(@RequestBody BWork bWork) throws URISyntaxException {
        log.debug("REST request to save BWork : {}", bWork);
        if (bWork.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bWork cannot already have an ID")).body(null);
        }
        BWork result = bWorkService.save(bWork);
        return ResponseEntity.created(new URI("/api/b-works/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /b-works : Updates an existing bWork.
     *
     * @param bWork the bWork to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bWork,
     * or with status 400 (Bad Request) if the bWork is not valid,
     * or with status 500 (Internal Server Error) if the bWork couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/b-works")
    @Timed
    public ResponseEntity<BWork> updateBWork(@RequestBody BWork bWork) throws URISyntaxException {
        log.debug("REST request to update BWork : {}", bWork);
        if (bWork.getId() == null) {
            return createBWork(bWork);
        }
        BWork result = bWorkService.save(bWork);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bWork.getId().toString()))
            .body(result);
    }

    /**
     * GET  /b-works : get all the bWorks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bWorks in body
     */
    @GetMapping("/b-works")
    @Timed
    public List<BWork> getAllBWorks() {
        log.debug("REST request to get all BWorks");
        return bWorkService.findAll();
    }

    /**
     * GET  /b-works/:id : get the "id" bWork.
     *
     * @param id the id of the bWork to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bWork, or with status 404 (Not Found)
     */
    @GetMapping("/b-works/{id}")
    @Timed
    public ResponseEntity<BWork> getBWork(@PathVariable Long id) {
        log.debug("REST request to get BWork : {}", id);
        BWork bWork = bWorkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bWork));
    }

    /**
     * DELETE  /b-works/:id : delete the "id" bWork.
     *
     * @param id the id of the bWork to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/b-works/{id}")
    @Timed
    public ResponseEntity<Void> deleteBWork(@PathVariable Long id) {
        log.debug("REST request to delete BWork : {}", id);
        bWorkService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
