package com.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.resume.domain.BEducation;
import com.resume.service.BEducationService;
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
 * REST controller for managing BEducation.
 */
@RestController
@RequestMapping("/api")
public class BEducationResource {

    private final Logger log = LoggerFactory.getLogger(BEducationResource.class);

    private static final String ENTITY_NAME = "bEducation";

    private final BEducationService bEducationService;

    public BEducationResource(BEducationService bEducationService) {
        this.bEducationService = bEducationService;
    }

    /**
     * POST  /b-educations : Create a new bEducation.
     *
     * @param bEducation the bEducation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bEducation, or with status 400 (Bad Request) if the bEducation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/b-educations")
    @Timed
    public ResponseEntity<BEducation> createBEducation(@RequestBody BEducation bEducation) throws URISyntaxException {
        log.debug("REST request to save BEducation : {}", bEducation);
        if (bEducation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bEducation cannot already have an ID")).body(null);
        }
        BEducation result = bEducationService.save(bEducation);
        return ResponseEntity.created(new URI("/api/b-educations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /b-educations : Updates an existing bEducation.
     *
     * @param bEducation the bEducation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bEducation,
     * or with status 400 (Bad Request) if the bEducation is not valid,
     * or with status 500 (Internal Server Error) if the bEducation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/b-educations")
    @Timed
    public ResponseEntity<BEducation> updateBEducation(@RequestBody BEducation bEducation) throws URISyntaxException {
        log.debug("REST request to update BEducation : {}", bEducation);
        if (bEducation.getId() == null) {
            return createBEducation(bEducation);
        }
        BEducation result = bEducationService.save(bEducation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bEducation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /b-educations : get all the bEducations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bEducations in body
     */
    @GetMapping("/b-educations")
    @Timed
    public List<BEducation> getAllBEducations() {
        log.debug("REST request to get all BEducations");
        return bEducationService.findAll();
    }

    /**
     * GET  /b-educations/:id : get the "id" bEducation.
     *
     * @param id the id of the bEducation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bEducation, or with status 404 (Not Found)
     */
    @GetMapping("/b-educations/{id}")
    @Timed
    public ResponseEntity<BEducation> getBEducation(@PathVariable Long id) {
        log.debug("REST request to get BEducation : {}", id);
        BEducation bEducation = bEducationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bEducation));
    }

    /**
     * DELETE  /b-educations/:id : delete the "id" bEducation.
     *
     * @param id the id of the bEducation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/b-educations/{id}")
    @Timed
    public ResponseEntity<Void> deleteBEducation(@PathVariable Long id) {
        log.debug("REST request to delete BEducation : {}", id);
        bEducationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
