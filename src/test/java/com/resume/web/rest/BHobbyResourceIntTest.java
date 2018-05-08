package com.resume.web.rest;

import com.resume.ResumeApp;

import com.resume.domain.BHobby;
import com.resume.repository.BHobbyRepository;
import com.resume.service.BHobbyService;
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
 * Test class for the BHobbyResource REST controller.
 *
 * @see BHobbyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResumeApp.class)
public class BHobbyResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BHobbyRepository bHobbyRepository;

    @Autowired
    private BHobbyService bHobbyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBHobbyMockMvc;

    private BHobby bHobby;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BHobbyResource bHobbyResource = new BHobbyResource(bHobbyService);
        this.restBHobbyMockMvc = MockMvcBuilders.standaloneSetup(bHobbyResource)
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
    public static BHobby createEntity(EntityManager em) {
        BHobby bHobby = new BHobby()
            .username(DEFAULT_USERNAME)
            .description(DEFAULT_DESCRIPTION)
            .extra(DEFAULT_EXTRA)
            .isActive(DEFAULT_IS_ACTIVE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return bHobby;
    }

    @Before
    public void initTest() {
        bHobby = createEntity(em);
    }

    @Test
    @Transactional
    public void createBHobby() throws Exception {
        int databaseSizeBeforeCreate = bHobbyRepository.findAll().size();

        // Create the BHobby
        restBHobbyMockMvc.perform(post("/api/b-hobbies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bHobby)))
            .andExpect(status().isCreated());

        // Validate the BHobby in the database
        List<BHobby> bHobbyList = bHobbyRepository.findAll();
        assertThat(bHobbyList).hasSize(databaseSizeBeforeCreate + 1);
        BHobby testBHobby = bHobbyList.get(bHobbyList.size() - 1);
        assertThat(testBHobby.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testBHobby.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBHobby.getExtra()).isEqualTo(DEFAULT_EXTRA);
        assertThat(testBHobby.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testBHobby.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testBHobby.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createBHobbyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bHobbyRepository.findAll().size();

        // Create the BHobby with an existing ID
        bHobby.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBHobbyMockMvc.perform(post("/api/b-hobbies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bHobby)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BHobby> bHobbyList = bHobbyRepository.findAll();
        assertThat(bHobbyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBHobbies() throws Exception {
        // Initialize the database
        bHobbyRepository.saveAndFlush(bHobby);

        // Get all the bHobbyList
        restBHobbyMockMvc.perform(get("/api/b-hobbies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bHobby.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].extra").value(hasItem(DEFAULT_EXTRA.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))));
    }

    @Test
    @Transactional
    public void getBHobby() throws Exception {
        // Initialize the database
        bHobbyRepository.saveAndFlush(bHobby);

        // Get the bHobby
        restBHobbyMockMvc.perform(get("/api/b-hobbies/{id}", bHobby.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bHobby.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.extra").value(DEFAULT_EXTRA.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingBHobby() throws Exception {
        // Get the bHobby
        restBHobbyMockMvc.perform(get("/api/b-hobbies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBHobby() throws Exception {
        // Initialize the database
        bHobbyService.save(bHobby);

        int databaseSizeBeforeUpdate = bHobbyRepository.findAll().size();

        // Update the bHobby
        BHobby updatedBHobby = bHobbyRepository.findOne(bHobby.getId());
        updatedBHobby
            .username(UPDATED_USERNAME)
            .description(UPDATED_DESCRIPTION)
            .extra(UPDATED_EXTRA)
            .isActive(UPDATED_IS_ACTIVE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restBHobbyMockMvc.perform(put("/api/b-hobbies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBHobby)))
            .andExpect(status().isOk());

        // Validate the BHobby in the database
        List<BHobby> bHobbyList = bHobbyRepository.findAll();
        assertThat(bHobbyList).hasSize(databaseSizeBeforeUpdate);
        BHobby testBHobby = bHobbyList.get(bHobbyList.size() - 1);
        assertThat(testBHobby.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testBHobby.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBHobby.getExtra()).isEqualTo(UPDATED_EXTRA);
        assertThat(testBHobby.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testBHobby.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testBHobby.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingBHobby() throws Exception {
        int databaseSizeBeforeUpdate = bHobbyRepository.findAll().size();

        // Create the BHobby

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBHobbyMockMvc.perform(put("/api/b-hobbies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bHobby)))
            .andExpect(status().isCreated());

        // Validate the BHobby in the database
        List<BHobby> bHobbyList = bHobbyRepository.findAll();
        assertThat(bHobbyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBHobby() throws Exception {
        // Initialize the database
        bHobbyService.save(bHobby);

        int databaseSizeBeforeDelete = bHobbyRepository.findAll().size();

        // Get the bHobby
        restBHobbyMockMvc.perform(delete("/api/b-hobbies/{id}", bHobby.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BHobby> bHobbyList = bHobbyRepository.findAll();
        assertThat(bHobbyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BHobby.class);
        BHobby bHobby1 = new BHobby();
        bHobby1.setId(1L);
        BHobby bHobby2 = new BHobby();
        bHobby2.setId(bHobby1.getId());
        assertThat(bHobby1).isEqualTo(bHobby2);
        bHobby2.setId(2L);
        assertThat(bHobby1).isNotEqualTo(bHobby2);
        bHobby1.setId(null);
        assertThat(bHobby1).isNotEqualTo(bHobby2);
    }
}
