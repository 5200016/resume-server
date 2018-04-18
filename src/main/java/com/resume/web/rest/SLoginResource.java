package com.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.resume.domain.SLogin;
import com.resume.service.SLoginService;
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
 * REST controller for managing SLogin.
 */
@RestController
@RequestMapping("/api")
public class SLoginResource {

    private final Logger log = LoggerFactory.getLogger(SLoginResource.class);

    private static final String ENTITY_NAME = "sLogin";

    private final SLoginService sLoginService;

    public SLoginResource(SLoginService sLoginService) {
        this.sLoginService = sLoginService;
    }

    /**
     * POST  /s-logins : Create a new sLogin.
     *
     * @param sLogin the sLogin to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sLogin, or with status 400 (Bad Request) if the sLogin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/s-logins")
    @Timed
    public ResponseEntity<SLogin> createSLogin(@RequestBody SLogin sLogin) throws URISyntaxException {
        log.debug("REST request to save SLogin : {}", sLogin);
        if (sLogin.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sLogin cannot already have an ID")).body(null);
        }
        SLogin result = sLoginService.save(sLogin);
        return ResponseEntity.created(new URI("/api/s-logins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /s-logins : Updates an existing sLogin.
     *
     * @param sLogin the sLogin to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sLogin,
     * or with status 400 (Bad Request) if the sLogin is not valid,
     * or with status 500 (Internal Server Error) if the sLogin couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/s-logins")
    @Timed
    public ResponseEntity<SLogin> updateSLogin(@RequestBody SLogin sLogin) throws URISyntaxException {
        log.debug("REST request to update SLogin : {}", sLogin);
        if (sLogin.getId() == null) {
            return createSLogin(sLogin);
        }
        SLogin result = sLoginService.save(sLogin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sLogin.getId().toString()))
            .body(result);
    }

    /**
     * GET  /s-logins : get all the sLogins.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sLogins in body
     */
    @GetMapping("/s-logins")
    @Timed
    public List<SLogin> getAllSLogins() {
        log.debug("REST request to get all SLogins");
        return sLoginService.findAll();
    }

    /**
     * GET  /s-logins/:id : get the "id" sLogin.
     *
     * @param id the id of the sLogin to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sLogin, or with status 404 (Not Found)
     */
    @GetMapping("/s-logins/{id}")
    @Timed
    public ResponseEntity<SLogin> getSLogin(@PathVariable Long id) {
        log.debug("REST request to get SLogin : {}", id);
        SLogin sLogin = sLoginService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sLogin));
    }

    /**
     * DELETE  /s-logins/:id : delete the "id" sLogin.
     *
     * @param id the id of the sLogin to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/s-logins/{id}")
    @Timed
    public ResponseEntity<Void> deleteSLogin(@PathVariable Long id) {
        log.debug("REST request to delete SLogin : {}", id);
        sLoginService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
