package com.resume.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.resume.domain.JobObjective;
import com.resume.service.JobObjectiveService;
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
 * REST controller for managing JobObjective.
 */
@RestController
@RequestMapping("/api")
public class JobObjectiveResource {

    private final Logger log = LoggerFactory.getLogger(JobObjectiveResource.class);

    private static final String ENTITY_NAME = "jobObjective";

    private final JobObjectiveService jobObjectiveService;

    public JobObjectiveResource(JobObjectiveService jobObjectiveService) {
        this.jobObjectiveService = jobObjectiveService;
    }

    /**
     * POST  /job-objectives : Create a new jobObjective.
     *
     * @param jobObjective the jobObjective to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobObjective, or with status 400 (Bad Request) if the jobObjective has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-objectives")
    @Timed
    public ResponseEntity<JobObjective> createJobObjective(@RequestBody JobObjective jobObjective) throws URISyntaxException {
        log.debug("REST request to save JobObjective : {}", jobObjective);
        if (jobObjective.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new jobObjective cannot already have an ID")).body(null);
        }
        JobObjective result = jobObjectiveService.save(jobObjective);
        return ResponseEntity.created(new URI("/api/job-objectives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-objectives : Updates an existing jobObjective.
     *
     * @param jobObjective the jobObjective to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobObjective,
     * or with status 400 (Bad Request) if the jobObjective is not valid,
     * or with status 500 (Internal Server Error) if the jobObjective couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-objectives")
    @Timed
    public ResponseEntity<JobObjective> updateJobObjective(@RequestBody JobObjective jobObjective) throws URISyntaxException {
        log.debug("REST request to update JobObjective : {}", jobObjective);
        if (jobObjective.getId() == null) {
            return createJobObjective(jobObjective);
        }
        JobObjective result = jobObjectiveService.save(jobObjective);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobObjective.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-objectives : get all the jobObjectives.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jobObjectives in body
     */
    @GetMapping("/job-objectives")
    @Timed
    public List<JobObjective> getAllJobObjectives() {
        log.debug("REST request to get all JobObjectives");
        return jobObjectiveService.findAll();
    }

    /**
     * GET  /job-objectives/:id : get the "id" jobObjective.
     *
     * @param id the id of the jobObjective to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobObjective, or with status 404 (Not Found)
     */
    @GetMapping("/job-objectives/{id}")
    @Timed
    public ResponseEntity<JobObjective> getJobObjective(@PathVariable Long id) {
        log.debug("REST request to get JobObjective : {}", id);
        JobObjective jobObjective = jobObjectiveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jobObjective));
    }

    /**
     * DELETE  /job-objectives/:id : delete the "id" jobObjective.
     *
     * @param id the id of the jobObjective to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/job-objectives/{id}")
    @Timed
    public ResponseEntity<Void> deleteJobObjective(@PathVariable Long id) {
        log.debug("REST request to delete JobObjective : {}", id);
        jobObjectiveService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
