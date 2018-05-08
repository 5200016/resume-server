package com.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.resume.domain.BTemplate;
import com.resume.service.BTemplateService;
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
 * REST controller for managing BTemplate.
 */
@RestController
@RequestMapping("/api")
public class BTemplateResource {

    private final Logger log = LoggerFactory.getLogger(BTemplateResource.class);

    private static final String ENTITY_NAME = "bTemplate";

    private final BTemplateService bTemplateService;

    public BTemplateResource(BTemplateService bTemplateService) {
        this.bTemplateService = bTemplateService;
    }

    /**
     * POST  /b-templates : Create a new bTemplate.
     *
     * @param bTemplate the bTemplate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bTemplate, or with status 400 (Bad Request) if the bTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/b-templates")
    @Timed
    public ResponseEntity<BTemplate> createBTemplate(@RequestBody BTemplate bTemplate) throws URISyntaxException {
        log.debug("REST request to save BTemplate : {}", bTemplate);
        if (bTemplate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bTemplate cannot already have an ID")).body(null);
        }
        BTemplate result = bTemplateService.save(bTemplate);
        return ResponseEntity.created(new URI("/api/b-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /b-templates : Updates an existing bTemplate.
     *
     * @param bTemplate the bTemplate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bTemplate,
     * or with status 400 (Bad Request) if the bTemplate is not valid,
     * or with status 500 (Internal Server Error) if the bTemplate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/b-templates")
    @Timed
    public ResponseEntity<BTemplate> updateBTemplate(@RequestBody BTemplate bTemplate) throws URISyntaxException {
        log.debug("REST request to update BTemplate : {}", bTemplate);
        if (bTemplate.getId() == null) {
            return createBTemplate(bTemplate);
        }
        BTemplate result = bTemplateService.save(bTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bTemplate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /b-templates : get all the bTemplates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bTemplates in body
     */
    @GetMapping("/b-templates")
    @Timed
    public List<BTemplate> getAllBTemplates() {
        log.debug("REST request to get all BTemplates");
        return bTemplateService.findAll();
    }

    /**
     * GET  /b-templates/:id : get the "id" bTemplate.
     *
     * @param id the id of the bTemplate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bTemplate, or with status 404 (Not Found)
     */
    @GetMapping("/b-templates/{id}")
    @Timed
    public ResponseEntity<BTemplate> getBTemplate(@PathVariable Long id) {
        log.debug("REST request to get BTemplate : {}", id);
        BTemplate bTemplate = bTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bTemplate));
    }

    /**
     * DELETE  /b-templates/:id : delete the "id" bTemplate.
     *
     * @param id the id of the bTemplate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/b-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteBTemplate(@PathVariable Long id) {
        log.debug("REST request to delete BTemplate : {}", id);
        bTemplateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
