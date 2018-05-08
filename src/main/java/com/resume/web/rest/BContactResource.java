package com.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.resume.domain.BContact;
import com.resume.service.BContactService;
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
 * REST controller for managing BContact.
 */
@RestController
@RequestMapping("/api")
public class BContactResource {

    private final Logger log = LoggerFactory.getLogger(BContactResource.class);

    private static final String ENTITY_NAME = "bContact";

    private final BContactService bContactService;

    public BContactResource(BContactService bContactService) {
        this.bContactService = bContactService;
    }

    /**
     * POST  /b-contacts : Create a new bContact.
     *
     * @param bContact the bContact to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bContact, or with status 400 (Bad Request) if the bContact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/b-contacts")
    @Timed
    public ResponseEntity<BContact> createBContact(@RequestBody BContact bContact) throws URISyntaxException {
        log.debug("REST request to save BContact : {}", bContact);
        if (bContact.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bContact cannot already have an ID")).body(null);
        }
        BContact result = bContactService.save(bContact);
        return ResponseEntity.created(new URI("/api/b-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /b-contacts : Updates an existing bContact.
     *
     * @param bContact the bContact to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bContact,
     * or with status 400 (Bad Request) if the bContact is not valid,
     * or with status 500 (Internal Server Error) if the bContact couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/b-contacts")
    @Timed
    public ResponseEntity<BContact> updateBContact(@RequestBody BContact bContact) throws URISyntaxException {
        log.debug("REST request to update BContact : {}", bContact);
        if (bContact.getId() == null) {
            return createBContact(bContact);
        }
        BContact result = bContactService.save(bContact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bContact.getId().toString()))
            .body(result);
    }

    /**
     * GET  /b-contacts : get all the bContacts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bContacts in body
     */
    @GetMapping("/b-contacts")
    @Timed
    public List<BContact> getAllBContacts() {
        log.debug("REST request to get all BContacts");
        return bContactService.findAll();
    }

    /**
     * GET  /b-contacts/:id : get the "id" bContact.
     *
     * @param id the id of the bContact to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bContact, or with status 404 (Not Found)
     */
    @GetMapping("/b-contacts/{id}")
    @Timed
    public ResponseEntity<BContact> getBContact(@PathVariable Long id) {
        log.debug("REST request to get BContact : {}", id);
        BContact bContact = bContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bContact));
    }

    /**
     * DELETE  /b-contacts/:id : delete the "id" bContact.
     *
     * @param id the id of the bContact to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/b-contacts/{id}")
    @Timed
    public ResponseEntity<Void> deleteBContact(@PathVariable Long id) {
        log.debug("REST request to delete BContact : {}", id);
        bContactService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
