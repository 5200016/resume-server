package com.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.resume.domain.BInformation;
import com.resume.service.BInformationService;
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
 * REST controller for managing BInformation.
 */
@RestController
@RequestMapping("/api")
public class BInformationResource {

    private final Logger log = LoggerFactory.getLogger(BInformationResource.class);

    private static final String ENTITY_NAME = "bInformation";

    private final BInformationService bInformationService;

    public BInformationResource(BInformationService bInformationService) {
        this.bInformationService = bInformationService;
    }

    /**
     * POST  /b-informations : Create a new bInformation.
     *
     * @param bInformation the bInformation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bInformation, or with status 400 (Bad Request) if the bInformation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/b-informations")
    @Timed
    public ResponseEntity<BInformation> createBInformation(@RequestBody BInformation bInformation) throws URISyntaxException {
        log.debug("REST request to save BInformation : {}", bInformation);
        if (bInformation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bInformation cannot already have an ID")).body(null);
        }
        BInformation result = bInformationService.save(bInformation);
        return ResponseEntity.created(new URI("/api/b-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /b-informations : Updates an existing bInformation.
     *
     * @param bInformation the bInformation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bInformation,
     * or with status 400 (Bad Request) if the bInformation is not valid,
     * or with status 500 (Internal Server Error) if the bInformation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/b-informations")
    @Timed
    public ResponseEntity<BInformation> updateBInformation(@RequestBody BInformation bInformation) throws URISyntaxException {
        log.debug("REST request to update BInformation : {}", bInformation);
        if (bInformation.getId() == null) {
            return createBInformation(bInformation);
        }
        BInformation result = bInformationService.save(bInformation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bInformation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /b-informations : get all the bInformations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bInformations in body
     */
    @GetMapping("/b-informations")
    @Timed
    public List<BInformation> getAllBInformations() {
        log.debug("REST request to get all BInformations");
        return bInformationService.findAll();
    }

    /**
     * GET  /b-informations/:id : get the "id" bInformation.
     *
     * @param id the id of the bInformation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bInformation, or with status 404 (Not Found)
     */
    @GetMapping("/b-informations/{id}")
    @Timed
    public ResponseEntity<BInformation> getBInformation(@PathVariable Long id) {
        log.debug("REST request to get BInformation : {}", id);
        BInformation bInformation = bInformationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bInformation));
    }

    /**
     * DELETE  /b-informations/:id : delete the "id" bInformation.
     *
     * @param id the id of the bInformation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/b-informations/{id}")
    @Timed
    public ResponseEntity<Void> deleteBInformation(@PathVariable Long id) {
        log.debug("REST request to delete BInformation : {}", id);
        bInformationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
