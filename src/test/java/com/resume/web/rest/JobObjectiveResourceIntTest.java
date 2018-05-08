package com.resume.web.rest;

import com.resume.ResumeApp;

import com.resume.domain.JobObjective;
import com.resume.repository.JobObjectiveRepository;
import com.resume.service.JobObjectiveService;
import com.resume.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.resume.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JobObjectiveResource REST controller.
 *
 * @see JobObjectiveResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResumeApp.class)
public class JobObjectiveResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ENTER_TIME = "AAAAAAAAAA";
    private static final String UPDATED_ENTER_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_SALARY_START = "AAAAAAAAAA";
    private static final String UPDATED_SALARY_START = "BBBBBBBBBB";

    private static final String DEFAULT_SALARY_END = "AAAAAAAAAA";
    private static final String UPDATED_SALARY_END = "BBBBBBBBBB";

    private static final String DEFAULT_EXPECT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_EXPECT_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private JobObjectiveRepository jobObjectiveRepository;

    @Autowired
    private JobObjectiveService jobObjectiveService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJobObjectiveMockMvc;

    private JobObjective jobObjective;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JobObjectiveResource jobObjectiveResource = new JobObjectiveResource(jobObjectiveService);
        this.restJobObjectiveMockMvc = MockMvcBuilders.standaloneSetup(jobObjectiveResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobObjective createEntity(EntityManager em) {
        JobObjective jobObjective = new JobObjective()
            .username(DEFAULT_USERNAME)
            .jobTitle(DEFAULT_JOB_TITLE)
            .jobType(DEFAULT_JOB_TYPE)
            .enterTime(DEFAULT_ENTER_TIME)
            .salaryStart(DEFAULT_SALARY_START)
            .salaryEnd(DEFAULT_SALARY_END)
            .expectCity(DEFAULT_EXPECT_CITY)
            .extra(DEFAULT_EXTRA)
            .isActive(DEFAULT_IS_ACTIVE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return jobObjective;
    }

    @Before
    public void initTest() {
        jobObjective = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobObjective() throws Exception {
        int databaseSizeBeforeCreate = jobObjectiveRepository.findAll().size();

        // Create the JobObjective
        restJobObjectiveMockMvc.perform(post("/api/job-objectives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobObjective)))
            .andExpect(status().isCreated());

        // Validate the JobObjective in the database
        List<JobObjective> jobObjectiveList = jobObjectiveRepository.findAll();
        assertThat(jobObjectiveList).hasSize(databaseSizeBeforeCreate + 1);
        JobObjective testJobObjective = jobObjectiveList.get(jobObjectiveList.size() - 1);
        assertThat(testJobObjective.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testJobObjective.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testJobObjective.getJobType()).isEqualTo(DEFAULT_JOB_TYPE);
        assertThat(testJobObjective.getEnterTime()).isEqualTo(DEFAULT_ENTER_TIME);
        assertThat(testJobObjective.getSalaryStart()).isEqualTo(DEFAULT_SALARY_START);
        assertThat(testJobObjective.getSalaryEnd()).isEqualTo(DEFAULT_SALARY_END);
        assertThat(testJobObjective.getExpectCity()).isEqualTo(DEFAULT_EXPECT_CITY);
        assertThat(testJobObjective.getExtra()).isEqualTo(DEFAULT_EXTRA);
        assertThat(testJobObjective.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testJobObjective.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testJobObjective.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createJobObjectiveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobObjectiveRepository.findAll().size();

        // Create the JobObjective with an existing ID
        jobObjective.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobObjectiveMockMvc.perform(post("/api/job-objectives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobObjective)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<JobObjective> jobObjectiveList = jobObjectiveRepository.findAll();
        assertThat(jobObjectiveList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllJobObjectives() throws Exception {
        // Initialize the database
        jobObjectiveRepository.saveAndFlush(jobObjective);

        // Get all the jobObjectiveList
        restJobObjectiveMockMvc.perform(get("/api/job-objectives?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobObjective.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE.toString())))
            .andExpect(jsonPath("$.[*].jobType").value(hasItem(DEFAULT_JOB_TYPE.toString())))
            .andExpect(jsonPath("$.[*].enterTime").value(hasItem(DEFAULT_ENTER_TIME.toString())))
            .andExpect(jsonPath("$.[*].salaryStart").value(hasItem(DEFAULT_SALARY_START.toString())))
            .andExpect(jsonPath("$.[*].salaryEnd").value(hasItem(DEFAULT_SALARY_END.toString())))
            .andExpect(jsonPath("$.[*].expectCity").value(hasItem(DEFAULT_EXPECT_CITY.toString())))
            .andExpect(jsonPath("$.[*].extra").value(hasItem(DEFAULT_EXTRA.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))));
    }

    @Test
    @Transactional
    public void getJobObjective() throws Exception {
        // Initialize the database
        jobObjectiveRepository.saveAndFlush(jobObjective);

        // Get the jobObjective
        restJobObjectiveMockMvc.perform(get("/api/job-objectives/{id}", jobObjective.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobObjective.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.jobTitle").value(DEFAULT_JOB_TITLE.toString()))
            .andExpect(jsonPath("$.jobType").value(DEFAULT_JOB_TYPE.toString()))
            .andExpect(jsonPath("$.enterTime").value(DEFAULT_ENTER_TIME.toString()))
            .andExpect(jsonPath("$.salaryStart").value(DEFAULT_SALARY_START.toString()))
            .andExpect(jsonPath("$.salaryEnd").value(DEFAULT_SALARY_END.toString()))
            .andExpect(jsonPath("$.expectCity").value(DEFAULT_EXPECT_CITY.toString()))
            .andExpect(jsonPath("$.extra").value(DEFAULT_EXTRA.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingJobObjective() throws Exception {
        // Get the jobObjective
        restJobObjectiveMockMvc.perform(get("/api/job-objectives/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobObjective() throws Exception {
        // Initialize the database
        jobObjectiveService.save(jobObjective);

        int databaseSizeBeforeUpdate = jobObjectiveRepository.findAll().size();

        // Update the jobObjective
        JobObjective updatedJobObjective = jobObjectiveRepository.findOne(jobObjective.getId());
        updatedJobObjective
            .username(UPDATED_USERNAME)
            .jobTitle(UPDATED_JOB_TITLE)
            .jobType(UPDATED_JOB_TYPE)
            .enterTime(UPDATED_ENTER_TIME)
            .salaryStart(UPDATED_SALARY_START)
            .salaryEnd(UPDATED_SALARY_END)
            .expectCity(UPDATED_EXPECT_CITY)
            .extra(UPDATED_EXTRA)
            .isActive(UPDATED_IS_ACTIVE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restJobObjectiveMockMvc.perform(put("/api/job-objectives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobObjective)))
            .andExpect(status().isOk());

        // Validate the JobObjective in the database
        List<JobObjective> jobObjectiveList = jobObjectiveRepository.findAll();
        assertThat(jobObjectiveList).hasSize(databaseSizeBeforeUpdate);
        JobObjective testJobObjective = jobObjectiveList.get(jobObjectiveList.size() - 1);
        assertThat(testJobObjective.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testJobObjective.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testJobObjective.getJobType()).isEqualTo(UPDATED_JOB_TYPE);
        assertThat(testJobObjective.getEnterTime()).isEqualTo(UPDATED_ENTER_TIME);
        assertThat(testJobObjective.getSalaryStart()).isEqualTo(UPDATED_SALARY_START);
        assertThat(testJobObjective.getSalaryEnd()).isEqualTo(UPDATED_SALARY_END);
        assertThat(testJobObjective.getExpectCity()).isEqualTo(UPDATED_EXPECT_CITY);
        assertThat(testJobObjective.getExtra()).isEqualTo(UPDATED_EXTRA);
        assertThat(testJobObjective.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testJobObjective.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testJobObjective.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingJobObjective() throws Exception {
        int databaseSizeBeforeUpdate = jobObjectiveRepository.findAll().size();

        // Create the JobObjective

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJobObjectiveMockMvc.perform(put("/api/job-objectives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobObjective)))
            .andExpect(status().isCreated());

        // Validate the JobObjective in the database
        List<JobObjective> jobObjectiveList = jobObjectiveRepository.findAll();
        assertThat(jobObjectiveList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJobObjective() throws Exception {
        // Initialize the database
        jobObjectiveService.save(jobObjective);

        int databaseSizeBeforeDelete = jobObjectiveRepository.findAll().size();

        // Get the jobObjective
        restJobObjectiveMockMvc.perform(delete("/api/job-objectives/{id}", jobObjective.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JobObjective> jobObjectiveList = jobObjectiveRepository.findAll();
        assertThat(jobObjectiveList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobObjective.class);
        JobObjective jobObjective1 = new JobObjective();
        jobObjective1.setId(1L);
        JobObjective jobObjective2 = new JobObjective();
        jobObjective2.setId(jobObjective1.getId());
        assertThat(jobObjective1).isEqualTo(jobObjective2);
        jobObjective2.setId(2L);
        assertThat(jobObjective1).isNotEqualTo(jobObjective2);
        jobObjective1.setId(null);
        assertThat(jobObjective1).isNotEqualTo(jobObjective2);
    }
}
