package com.resume.web.rest;

import com.resume.ResumeApp;

import com.resume.domain.BTemplateClassify;
import com.resume.repository.BTemplateClassifyRepository;
import com.resume.service.BTemplateClassifyService;
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
 * Test class for the BTemplateClassifyResource REST controller.
 *
 * @see BTemplateClassifyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResumeApp.class)
public class BTemplateClassifyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final String DEFAULT_EXTRA = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BTemplateClassifyRepository bTemplateClassifyRepository;

    @Autowired
    private BTemplateClassifyService bTemplateClassifyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBTemplateClassifyMockMvc;

    private BTemplateClassify bTemplateClassify;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BTemplateClassifyResource bTemplateClassifyResource = new BTemplateClassifyResource(bTemplateClassifyService);
        this.restBTemplateClassifyMockMvc = MockMvcBuilders.standaloneSetup(bTemplateClassifyResource)
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
    public static BTemplateClassify createEntity(EntityManager em) {
        BTemplateClassify bTemplateClassify = new BTemplateClassify()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .extra(DEFAULT_EXTRA)
            .isActive(DEFAULT_IS_ACTIVE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return bTemplateClassify;
    }

    @Before
    public void initTest() {
        bTemplateClassify = createEntity(em);
    }

    @Test
    @Transactional
    public void createBTemplateClassify() throws Exception {
        int databaseSizeBeforeCreate = bTemplateClassifyRepository.findAll().size();

        // Create the BTemplateClassify
        restBTemplateClassifyMockMvc.perform(post("/api/b-template-classifies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bTemplateClassify)))
            .andExpect(status().isCreated());

        // Validate the BTemplateClassify in the database
        List<BTemplateClassify> bTemplateClassifyList = bTemplateClassifyRepository.findAll();
        assertThat(bTemplateClassifyList).hasSize(databaseSizeBeforeCreate + 1);
        BTemplateClassify testBTemplateClassify = bTemplateClassifyList.get(bTemplateClassifyList.size() - 1);
        assertThat(testBTemplateClassify.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBTemplateClassify.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBTemplateClassify.getExtra()).isEqualTo(DEFAULT_EXTRA);
        assertThat(testBTemplateClassify.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testBTemplateClassify.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testBTemplateClassify.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createBTemplateClassifyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bTemplateClassifyRepository.findAll().size();

        // Create the BTemplateClassify with an existing ID
        bTemplateClassify.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBTemplateClassifyMockMvc.perform(post("/api/b-template-classifies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bTemplateClassify)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BTemplateClassify> bTemplateClassifyList = bTemplateClassifyRepository.findAll();
        assertThat(bTemplateClassifyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBTemplateClassifies() throws Exception {
        // Initialize the database
        bTemplateClassifyRepository.saveAndFlush(bTemplateClassify);

        // Get all the bTemplateClassifyList
        restBTemplateClassifyMockMvc.perform(get("/api/b-template-classifies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bTemplateClassify.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].extra").value(hasItem(DEFAULT_EXTRA.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))));
    }

    @Test
    @Transactional
    public void getBTemplateClassify() throws Exception {
        // Initialize the database
        bTemplateClassifyRepository.saveAndFlush(bTemplateClassify);

        // Get the bTemplateClassify
        restBTemplateClassifyMockMvc.perform(get("/api/b-template-classifies/{id}", bTemplateClassify.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bTemplateClassify.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.extra").value(DEFAULT_EXTRA.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingBTemplateClassify() throws Exception {
        // Get the bTemplateClassify
        restBTemplateClassifyMockMvc.perform(get("/api/b-template-classifies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBTemplateClassify() throws Exception {
        // Initialize the database
        bTemplateClassifyService.save(bTemplateClassify);

        int databaseSizeBeforeUpdate = bTemplateClassifyRepository.findAll().size();

        // Update the bTemplateClassify
        BTemplateClassify updatedBTemplateClassify = bTemplateClassifyRepository.findOne(bTemplateClassify.getId());
        updatedBTemplateClassify
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .extra(UPDATED_EXTRA)
            .isActive(UPDATED_IS_ACTIVE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restBTemplateClassifyMockMvc.perform(put("/api/b-template-classifies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBTemplateClassify)))
            .andExpect(status().isOk());

        // Validate the BTemplateClassify in the database
        List<BTemplateClassify> bTemplateClassifyList = bTemplateClassifyRepository.findAll();
        assertThat(bTemplateClassifyList).hasSize(databaseSizeBeforeUpdate);
        BTemplateClassify testBTemplateClassify = bTemplateClassifyList.get(bTemplateClassifyList.size() - 1);
        assertThat(testBTemplateClassify.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBTemplateClassify.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBTemplateClassify.getExtra()).isEqualTo(UPDATED_EXTRA);
        assertThat(testBTemplateClassify.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testBTemplateClassify.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testBTemplateClassify.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingBTemplateClassify() throws Exception {
        int databaseSizeBeforeUpdate = bTemplateClassifyRepository.findAll().size();

        // Create the BTemplateClassify

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBTemplateClassifyMockMvc.perform(put("/api/b-template-classifies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bTemplateClassify)))
            .andExpect(status().isCreated());

        // Validate the BTemplateClassify in the database
        List<BTemplateClassify> bTemplateClassifyList = bTemplateClassifyRepository.findAll();
        assertThat(bTemplateClassifyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBTemplateClassify() throws Exception {
        // Initialize the database
        bTemplateClassifyService.save(bTemplateClassify);

        int databaseSizeBeforeDelete = bTemplateClassifyRepository.findAll().size();

        // Get the bTemplateClassify
        restBTemplateClassifyMockMvc.perform(delete("/api/b-template-classifies/{id}", bTemplateClassify.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BTemplateClassify> bTemplateClassifyList = bTemplateClassifyRepository.findAll();
        assertThat(bTemplateClassifyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BTemplateClassify.class);
        BTemplateClassify bTemplateClassify1 = new BTemplateClassify();
        bTemplateClassify1.setId(1L);
        BTemplateClassify bTemplateClassify2 = new BTemplateClassify();
        bTemplateClassify2.setId(bTemplateClassify1.getId());
        assertThat(bTemplateClassify1).isEqualTo(bTemplateClassify2);
        bTemplateClassify2.setId(2L);
        assertThat(bTemplateClassify1).isNotEqualTo(bTemplateClassify2);
        bTemplateClassify1.setId(null);
        assertThat(bTemplateClassify1).isNotEqualTo(bTemplateClassify2);
    }
}
