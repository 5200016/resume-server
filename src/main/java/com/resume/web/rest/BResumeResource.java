package com.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.resume.domain.BResume;
import com.resume.service.BResumeService;
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
 * REST controller for managing BResume.
 */
@RestController
@RequestMapping("/api")
public class BResumeResource {

    private final Logger log = LoggerFactory.getLogger(BResumeResource.class);

    private static final String ENTITY_NAME = "bResume";

    private final BResumeService bResumeService;

    public BResumeResource(BResumeService bResumeService) {
        this.bResumeService = bResumeService;
    }

    /**
     * POST  /b-resumes : Create a new bResume.
     *
     * @param bResume the bResume to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bResume, or with status 400 (Bad Request) if the bResume has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/b-resumes")
    @Timed
    public ResponseEntity<BResume> createBResume(@RequestBody BResume bResume) throws URISyntaxException {
        log.debug("REST request to save BResume : {}", bResume);
        if (bResume.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bResume cannot already have an ID")).body(null);
        }
        BResume result = bResumeService.save(bResume);
        return ResponseEntity.created(new URI("/api/b-resumes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /b-resumes : Updates an existing bResume.
     *
     * @param bResume the bResume to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bResume,
     * or with status 400 (Bad Request) if the bResume is not valid,
     * or with status 500 (Internal Server Error) if the bResume couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/b-resumes")
    @Timed
    public ResponseEntity<BResume> updateBResume(@RequestBody BResume bResume) throws URISyntaxException {
        log.debug("REST request to update BResume : {}", bResume);
        if (bResume.getId() == null) {
            return createBResume(bResume);
        }
        BResume result = bResumeService.save(bResume);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bResume.getId().toString()))
            .body(result);
    }

    /**
     * GET  /b-resumes : get all the bResumes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bResumes in body
     */
    @GetMapping("/b-resumes")
    @Timed
    public List<BResume> getAllBResumes() {
        log.debug("REST request to get all BResumes");
        return bResumeService.findAll();
    }

    /**
     * GET  /b-resumes/:id : get the "id" bResume.
     *
     * @param id the id of the bResume to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bResume, or with status 404 (Not Found)
     */
    @GetMapping("/b-resumes/{id}")
    @Timed
    public ResponseEntity<BResume> getBResume(@PathVariable Long id) {
        log.debug("REST request to get BResume : {}", id);
        BResume bResume = bResumeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bResume));
    }

    /**
     * DELETE  /b-resumes/:id : delete the "id" bResume.
     *
     * @param id the id of the bResume to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/b-resumes/{id}")
    @Timed
    public ResponseEntity<Void> deleteBResume(@PathVariable Long id) {
        log.debug("REST request to delete BResume : {}", id);
        bResumeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
