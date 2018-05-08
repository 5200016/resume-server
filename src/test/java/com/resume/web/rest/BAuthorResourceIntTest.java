package com.resume.web.rest;

import com.resume.ResumeApp;

import com.resume.domain.BAuthor;
import com.resume.repository.BAuthorRepository;
import com.resume.service.BAuthorService;
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
import org.springframework.util.Base64Utils;

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
 * Test class for the BAuthorResource REST controller.
 *
 * @see BAuthorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResumeApp.class)
public class BAuthorResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_LIKE_COUNT = 1;
    private static final Integer UPDATED_LIKE_COUNT = 2;

    private static final Integer DEFAULT_WRITE_COUNT = 1;
    private static final Integer UPDATED_WRITE_COUNT = 2;

    private static final String DEFAULT_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCTION = "BBBBBBBBBB";

    private static final String DEFAULT_QUALIFICATIONS = "AAAAAAAAAA";
    private static final String UPDATED_QUALIFICATIONS = "BBBBBBBBBB";

    private static final String DEFAULT_PROTECTION = "AAAAAAAAAA";
    private static final String UPDATED_PROTECTION = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BAuthorRepository bAuthorRepository;

    @Autowired
    private BAuthorService bAuthorService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBAuthorMockMvc;

    private BAuthor bAuthor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BAuthorResource bAuthorResource = new BAuthorResource(bAuthorService);
        this.restBAuthorMockMvc = MockMvcBuilders.standaloneSetup(bAuthorResource)
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
    public static BAuthor createEntity(EntityManager em) {
        BAuthor bAuthor = new BAuthor()
            .username(DEFAULT_USERNAME)
            .likeCount(DEFAULT_LIKE_COUNT)
            .writeCount(DEFAULT_WRITE_COUNT)
            .introduction(DEFAULT_INTRODUCTION)
            .qualifications(DEFAULT_QUALIFICATIONS)
            .protection(DEFAULT_PROTECTION)
            .phone(DEFAULT_PHONE)
            .responseTime(DEFAULT_RESPONSE_TIME)
            .extra(DEFAULT_EXTRA)
            .isActive(DEFAULT_IS_ACTIVE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return bAuthor;
    }

    @Before
    public void initTest() {
        bAuthor = createEntity(em);
    }

    @Test
    @Transactional
    public void createBAuthor() throws Exception {
        int databaseSizeBeforeCreate = bAuthorRepository.findAll().size();

        // Create the BAuthor
        restBAuthorMockMvc.perform(post("/api/b-authors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bAuthor)))
            .andExpect(status().isCreated());

        // Validate the BAuthor in the database
        List<BAuthor> bAuthorList = bAuthorRepository.findAll();
        assertThat(bAuthorList).hasSize(databaseSizeBeforeCreate + 1);
        BAuthor testBAuthor = bAuthorList.get(bAuthorList.size() - 1);
        assertThat(testBAuthor.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testBAuthor.getLikeCount()).isEqualTo(DEFAULT_LIKE_COUNT);
        assertThat(testBAuthor.getWriteCount()).isEqualTo(DEFAULT_WRITE_COUNT);
        assertThat(testBAuthor.getIntroduction()).isEqualTo(DEFAULT_INTRODUCTION);
        assertThat(testBAuthor.getQualifications()).isEqualTo(DEFAULT_QUALIFICATIONS);
        assertThat(testBAuthor.getProtection()).isEqualTo(DEFAULT_PROTECTION);
        assertThat(testBAuthor.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testBAuthor.getResponseTime()).isEqualTo(DEFAULT_RESPONSE_TIME);
        assertThat(testBAuthor.getExtra()).isEqualTo(DEFAULT_EXTRA);
        assertThat(testBAuthor.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testBAuthor.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testBAuthor.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createBAuthorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bAuthorRepository.findAll().size();

        // Create the BAuthor with an existing ID
        bAuthor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBAuthorMockMvc.perform(post("/api/b-authors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bAuthor)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BAuthor> bAuthorList = bAuthorRepository.findAll();
        assertThat(bAuthorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBAuthors() throws Exception {
        // Initialize the database
        bAuthorRepository.saveAndFlush(bAuthor);

        // Get all the bAuthorList
        restBAuthorMockMvc.perform(get("/api/b-authors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bAuthor.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].likeCount").value(hasItem(DEFAULT_LIKE_COUNT)))
            .andExpect(jsonPath("$.[*].writeCount").value(hasItem(DEFAULT_WRITE_COUNT)))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].qualifications").value(hasItem(DEFAULT_QUALIFICATIONS.toString())))
            .andExpect(jsonPath("$.[*].protection").value(hasItem(DEFAULT_PROTECTION.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].responseTime").value(hasItem(DEFAULT_RESPONSE_TIME.toString())))
            .andExpect(jsonPath("$.[*].extra").value(hasItem(DEFAULT_EXTRA.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))));
    }

    @Test
    @Transactional
    public void getBAuthor() throws Exception {
        // Initialize the database
        bAuthorRepository.saveAndFlush(bAuthor);

        // Get the bAuthor
        restBAuthorMockMvc.perform(get("/api/b-authors/{id}", bAuthor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bAuthor.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.likeCount").value(DEFAULT_LIKE_COUNT))
            .andExpect(jsonPath("$.writeCount").value(DEFAULT_WRITE_COUNT))
            .andExpect(jsonPath("$.introduction").value(DEFAULT_INTRODUCTION.toString()))
            .andExpect(jsonPath("$.qualifications").value(DEFAULT_QUALIFICATIONS.toString()))
            .andExpect(jsonPath("$.protection").value(DEFAULT_PROTECTION.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.responseTime").value(DEFAULT_RESPONSE_TIME.toString()))
            .andExpect(jsonPath("$.extra").value(DEFAULT_EXTRA.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingBAuthor() throws Exception {
        // Get the bAuthor
        restBAuthorMockMvc.perform(get("/api/b-authors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBAuthor() throws Exception {
        // Initialize the database
        bAuthorService.save(bAuthor);

        int databaseSizeBeforeUpdate = bAuthorRepository.findAll().size();

        // Update the bAuthor
        BAuthor updatedBAuthor = bAuthorRepository.findOne(bAuthor.getId());
        updatedBAuthor
            .username(UPDATED_USERNAME)
            .likeCount(UPDATED_LIKE_COUNT)
            .writeCount(UPDATED_WRITE_COUNT)
            .introduction(UPDATED_INTRODUCTION)
            .qualifications(UPDATED_QUALIFICATIONS)
            .protection(UPDATED_PROTECTION)
            .phone(UPDATED_PHONE)
            .responseTime(UPDATED_RESPONSE_TIME)
            .extra(UPDATED_EXTRA)
            .isActive(UPDATED_IS_ACTIVE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restBAuthorMockMvc.perform(put("/api/b-authors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBAuthor)))
            .andExpect(status().isOk());

        // Validate the BAuthor in the database
        List<BAuthor> bAuthorList = bAuthorRepository.findAll();
        assertThat(bAuthorList).hasSize(databaseSizeBeforeUpdate);
        BAuthor testBAuthor = bAuthorList.get(bAuthorList.size() - 1);
        assertThat(testBAuthor.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testBAuthor.getLikeCount()).isEqualTo(UPDATED_LIKE_COUNT);
        assertThat(testBAuthor.getWriteCount()).isEqualTo(UPDATED_WRITE_COUNT);
        assertThat(testBAuthor.getIntroduction()).isEqualTo(UPDATED_INTRODUCTION);
        assertThat(testBAuthor.getQualifications()).isEqualTo(UPDATED_QUALIFICATIONS);
        assertThat(testBAuthor.getProtection()).isEqualTo(UPDATED_PROTECTION);
        assertThat(testBAuthor.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testBAuthor.getResponseTime()).isEqualTo(UPDATED_RESPONSE_TIME);
        assertThat(testBAuthor.getExtra()).isEqualTo(UPDATED_EXTRA);
        assertThat(testBAuthor.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testBAuthor.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testBAuthor.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingBAuthor() throws Exception {
        int databaseSizeBeforeUpdate = bAuthorRepository.findAll().size();

        // Create the BAuthor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBAuthorMockMvc.perform(put("/api/b-authors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bAuthor)))
            .andExpect(status().isCreated());

        // Validate the BAuthor in the database
        List<BAuthor> bAuthorList = bAuthorRepository.findAll();
        assertThat(bAuthorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBAuthor() throws Exception {
        // Initialize the database
        bAuthorService.save(bAuthor);

        int databaseSizeBeforeDelete = bAuthorRepository.findAll().size();

        // Get the bAuthor
        restBAuthorMockMvc.perform(delete("/api/b-authors/{id}", bAuthor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BAuthor> bAuthorList = bAuthorRepository.findAll();
        assertThat(bAuthorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BAuthor.class);
        BAuthor bAuthor1 = new BAuthor();
        bAuthor1.setId(1L);
        BAuthor bAuthor2 = new BAuthor();
        bAuthor2.setId(bAuthor1.getId());
        assertThat(bAuthor1).isEqualTo(bAuthor2);
        bAuthor2.setId(2L);
        assertThat(bAuthor1).isNotEqualTo(bAuthor2);
        bAuthor1.setId(null);
        assertThat(bAuthor1).isNotEqualTo(bAuthor2);
    }
}
