package com.resume.web.rest;

import com.resume.ResumeApp;

import com.resume.domain.BResume;
import com.resume.repository.BResumeRepository;
import com.resume.service.BResumeService;
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
 * Test class for the BResumeResource REST controller.
 *
 * @see BResumeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResumeApp.class)
public class BResumeResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final String DEFAULT_EXTRA = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BResumeRepository bResumeRepository;

    @Autowired
    private BResumeService bResumeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBResumeMockMvc;

    private BResume bResume;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BResumeResource bResumeResource = new BResumeResource(bResumeService);
        this.restBResumeMockMvc = MockMvcBuilders.standaloneSetup(bResumeResource)
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
    public static BResume createEntity(EntityManager em) {
        BResume bResume = new BResume()
            .username(DEFAULT_USERNAME)
            .url(DEFAULT_URL)
            .status(DEFAULT_STATUS)
            .extra(DEFAULT_EXTRA)
            .isActive(DEFAULT_IS_ACTIVE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return bResume;
    }

    @Before
    public void initTest() {
        bResume = createEntity(em);
    }

    @Test
    @Transactional
    public void createBResume() throws Exception {
        int databaseSizeBeforeCreate = bResumeRepository.findAll().size();

        // Create the BResume
        restBResumeMockMvc.perform(post("/api/b-resumes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bResume)))
            .andExpect(status().isCreated());

        // Validate the BResume in the database
        List<BResume> bResumeList = bResumeRepository.findAll();
        assertThat(bResumeList).hasSize(databaseSizeBeforeCreate + 1);
        BResume testBResume = bResumeList.get(bResumeList.size() - 1);
        assertThat(testBResume.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testBResume.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testBResume.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBResume.getExtra()).isEqualTo(DEFAULT_EXTRA);
        assertThat(testBResume.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testBResume.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testBResume.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createBResumeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bResumeRepository.findAll().size();

        // Create the BResume with an existing ID
        bResume.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBResumeMockMvc.perform(post("/api/b-resumes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bResume)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BResume> bResumeList = bResumeRepository.findAll();
        assertThat(bResumeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBResumes() throws Exception {
        // Initialize the database
        bResumeRepository.saveAndFlush(bResume);

        // Get all the bResumeList
        restBResumeMockMvc.perform(get("/api/b-resumes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bResume.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].extra").value(hasItem(DEFAULT_EXTRA.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))));
    }

    @Test
    @Transactional
    public void getBResume() throws Exception {
        // Initialize the database
        bResumeRepository.saveAndFlush(bResume);

        // Get the bResume
        restBResumeMockMvc.perform(get("/api/b-resumes/{id}", bResume.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bResume.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.extra").value(DEFAULT_EXTRA.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingBResume() throws Exception {
        // Get the bResume
        restBResumeMockMvc.perform(get("/api/b-resumes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBResume() throws Exception {
        // Initialize the database
        bResumeService.save(bResume);

        int databaseSizeBeforeUpdate = bResumeRepository.findAll().size();

        // Update the bResume
        BResume updatedBResume = bResumeRepository.findOne(bResume.getId());
        updatedBResume
            .username(UPDATED_USERNAME)
            .url(UPDATED_URL)
            .status(UPDATED_STATUS)
            .extra(UPDATED_EXTRA)
            .isActive(UPDATED_IS_ACTIVE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restBResumeMockMvc.perform(put("/api/b-resumes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBResume)))
            .andExpect(status().isOk());

        // Validate the BResume in the database
        List<BResume> bResumeList = bResumeRepository.findAll();
        assertThat(bResumeList).hasSize(databaseSizeBeforeUpdate);
        BResume testBResume = bResumeList.get(bResumeList.size() - 1);
        assertThat(testBResume.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testBResume.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testBResume.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBResume.getExtra()).isEqualTo(UPDATED_EXTRA);
        assertThat(testBResume.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testBResume.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testBResume.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingBResume() throws Exception {
        int databaseSizeBeforeUpdate = bResumeRepository.findAll().size();

        // Create the BResume

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBResumeMockMvc.perform(put("/api/b-resumes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bResume)))
            .andExpect(status().isCreated());

        // Validate the BResume in the database
        List<BResume> bResumeList = bResumeRepository.findAll();
        assertThat(bResumeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBResume() throws Exception {
        // Initialize the database
        bResumeService.save(bResume);

        int databaseSizeBeforeDelete = bResumeRepository.findAll().size();

        // Get the bResume
        restBResumeMockMvc.perform(delete("/api/b-resumes/{id}", bResume.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BResume> bResumeList = bResumeRepository.findAll();
        assertThat(bResumeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BResume.class);
        BResume bResume1 = new BResume();
        bResume1.setId(1L);
        BResume bResume2 = new BResume();
        bResume2.setId(bResume1.getId());
        assertThat(bResume1).isEqualTo(bResume2);
        bResume2.setId(2L);
        assertThat(bResume1).isNotEqualTo(bResume2);
        bResume1.setId(null);
        assertThat(bResume1).isNotEqualTo(bResume2);
    }
}
