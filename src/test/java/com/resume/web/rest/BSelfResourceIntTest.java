package com.resume.web.rest;

import com.resume.ResumeApp;

import com.resume.domain.BSelf;
import com.resume.repository.BSelfRepository;
import com.resume.service.BSelfService;
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
 * Test class for the BSelfResource REST controller.
 *
 * @see BSelfResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResumeApp.class)
public class BSelfResourceIntTest {

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
    private BSelfRepository bSelfRepository;

    @Autowired
    private BSelfService bSelfService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBSelfMockMvc;

    private BSelf bSelf;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BSelfResource bSelfResource = new BSelfResource(bSelfService);
        this.restBSelfMockMvc = MockMvcBuilders.standaloneSetup(bSelfResource)
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
    public static BSelf createEntity(EntityManager em) {
        BSelf bSelf = new BSelf()
            .username(DEFAULT_USERNAME)
            .description(DEFAULT_DESCRIPTION)
            .extra(DEFAULT_EXTRA)
            .isActive(DEFAULT_IS_ACTIVE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return bSelf;
    }

    @Before
    public void initTest() {
        bSelf = createEntity(em);
    }

    @Test
    @Transactional
    public void createBSelf() throws Exception {
        int databaseSizeBeforeCreate = bSelfRepository.findAll().size();

        // Create the BSelf
        restBSelfMockMvc.perform(post("/api/b-selves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bSelf)))
            .andExpect(status().isCreated());

        // Validate the BSelf in the database
        List<BSelf> bSelfList = bSelfRepository.findAll();
        assertThat(bSelfList).hasSize(databaseSizeBeforeCreate + 1);
        BSelf testBSelf = bSelfList.get(bSelfList.size() - 1);
        assertThat(testBSelf.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testBSelf.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBSelf.getExtra()).isEqualTo(DEFAULT_EXTRA);
        assertThat(testBSelf.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testBSelf.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testBSelf.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createBSelfWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bSelfRepository.findAll().size();

        // Create the BSelf with an existing ID
        bSelf.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBSelfMockMvc.perform(post("/api/b-selves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bSelf)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BSelf> bSelfList = bSelfRepository.findAll();
        assertThat(bSelfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBSelves() throws Exception {
        // Initialize the database
        bSelfRepository.saveAndFlush(bSelf);

        // Get all the bSelfList
        restBSelfMockMvc.perform(get("/api/b-selves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bSelf.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].extra").value(hasItem(DEFAULT_EXTRA.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))));
    }

    @Test
    @Transactional
    public void getBSelf() throws Exception {
        // Initialize the database
        bSelfRepository.saveAndFlush(bSelf);

        // Get the bSelf
        restBSelfMockMvc.perform(get("/api/b-selves/{id}", bSelf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bSelf.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.extra").value(DEFAULT_EXTRA.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingBSelf() throws Exception {
        // Get the bSelf
        restBSelfMockMvc.perform(get("/api/b-selves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBSelf() throws Exception {
        // Initialize the database
        bSelfService.save(bSelf);

        int databaseSizeBeforeUpdate = bSelfRepository.findAll().size();

        // Update the bSelf
        BSelf updatedBSelf = bSelfRepository.findOne(bSelf.getId());
        updatedBSelf
            .username(UPDATED_USERNAME)
            .description(UPDATED_DESCRIPTION)
            .extra(UPDATED_EXTRA)
            .isActive(UPDATED_IS_ACTIVE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restBSelfMockMvc.perform(put("/api/b-selves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBSelf)))
            .andExpect(status().isOk());

        // Validate the BSelf in the database
        List<BSelf> bSelfList = bSelfRepository.findAll();
        assertThat(bSelfList).hasSize(databaseSizeBeforeUpdate);
        BSelf testBSelf = bSelfList.get(bSelfList.size() - 1);
        assertThat(testBSelf.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testBSelf.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBSelf.getExtra()).isEqualTo(UPDATED_EXTRA);
        assertThat(testBSelf.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testBSelf.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testBSelf.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingBSelf() throws Exception {
        int databaseSizeBeforeUpdate = bSelfRepository.findAll().size();

        // Create the BSelf

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBSelfMockMvc.perform(put("/api/b-selves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bSelf)))
            .andExpect(status().isCreated());

        // Validate the BSelf in the database
        List<BSelf> bSelfList = bSelfRepository.findAll();
        assertThat(bSelfList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBSelf() throws Exception {
        // Initialize the database
        bSelfService.save(bSelf);

        int databaseSizeBeforeDelete = bSelfRepository.findAll().size();

        // Get the bSelf
        restBSelfMockMvc.perform(delete("/api/b-selves/{id}", bSelf.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BSelf> bSelfList = bSelfRepository.findAll();
        assertThat(bSelfList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BSelf.class);
        BSelf bSelf1 = new BSelf();
        bSelf1.setId(1L);
        BSelf bSelf2 = new BSelf();
        bSelf2.setId(bSelf1.getId());
        assertThat(bSelf1).isEqualTo(bSelf2);
        bSelf2.setId(2L);
        assertThat(bSelf1).isNotEqualTo(bSelf2);
        bSelf1.setId(null);
        assertThat(bSelf1).isNotEqualTo(bSelf2);
    }
}
