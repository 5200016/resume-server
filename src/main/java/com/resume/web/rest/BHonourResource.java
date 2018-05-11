package com.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.resume.domain.BHonour;
import com.resume.service.BHonourService;
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
 * REST controller for managing BHonour.
 */
@RestController
@RequestMapping("/ap")
public class BHonourResource {

    private final Logger log = LoggerFactory.getLogger(BHonourResource.class);

    private static final String ENTITY_NAME = "bHonour";

    private final BHonourService bHonourService;

    public BHonourResource(BHonourService bHonourService) {
        this.bHonourService = bHonourService;
    }

    /**
     * POST  /b-honours : Create a new bHonour.
     *
     * @param bHonour the bHonour to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bHonour, or with status 400 (Bad Request) if the bHonour has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/b-honours")
    @Timed
    public ResponseEntity<BHonour> createBHonour(@RequestBody BHonour bHonour) throws URISyntaxException {
        log.debug("REST request to save BHonour : {}", bHonour);
        if (bHonour.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bHonour cannot already have an ID")).body(null);
        }
        BHonour result = bHonourService.save(bHonour);
        return ResponseEntity.created(new URI("/api/b-honours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /b-honours : Updates an existing bHonour.
     *
     * @param bHonour the bHonour to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bHonour,
     * or with status 400 (Bad Request) if the bHonour is not valid,
     * or with status 500 (Internal Server Error) if the bHonour couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/b-honours")
    @Timed
    public ResponseEntity<BHonour> updateBHonour(@RequestBody BHonour bHonour) throws URISyntaxException {
        log.debug("REST request to update BHonour : {}", bHonour);
        if (bHonour.getId() == null) {
            return createBHonour(bHonour);
        }
        BHonour result = bHonourService.save(bHonour);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bHonour.getId().toString()))
            .body(result);
    }

    /**
     * GET  /b-honours : get all the bHonours.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bHonours in body
     */
    @GetMapping("/b-honours")
    @Timed
    public List<BHonour> getAllBHonours() {
        log.debug("REST request to get all BHonours");
        return bHonourService.findAll();
    }

    /**
     * GET  /b-honours/:id : get the "id" bHonour.
     *
     * @param id the id of the bHonour to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bHonour, or with status 404 (Not Found)
     */
    @GetMapping("/b-honours/{id}")
    @Timed
    public ResponseEntity<BHonour> getBHonour(@PathVariable Long id) {
        log.debug("REST request to get BHonour : {}", id);
        BHonour bHonour = bHonourService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bHonour));
    }

    /**
     * DELETE  /b-honours/:id : delete the "id" bHonour.
     *
     * @param id the id of the bHonour to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/b-honours/{id}")
    @Timed
    public ResponseEntity<Void> deleteBHonour(@PathVariable Long id) {
        log.debug("REST request to delete BHonour : {}", id);
        bHonourService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
