package com.resume.web.rest;

import com.resume.ResumeApp;

import com.resume.domain.SLogin;
import com.resume.repository.SLoginRepository;
import com.resume.service.SLoginService;
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
 * Test class for the SLoginResource REST controller.
 *
 * @see SLoginResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResumeApp.class)
public class SLoginResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SLoginRepository sLoginRepository;

    @Autowired
    private SLoginService sLoginService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSLoginMockMvc;

    private SLogin sLogin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SLoginResource sLoginResource = new SLoginResource(sLoginService);
        this.restSLoginMockMvc = MockMvcBuilders.standaloneSetup(sLoginResource)
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
    public static SLogin createEntity(EntityManager em) {
        SLogin sLogin = new SLogin()
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .isActive(DEFAULT_IS_ACTIVE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return sLogin;
    }

    @Before
    public void initTest() {
        sLogin = createEntity(em);
    }

    @Test
    @Transactional
    public void createSLogin() throws Exception {
        int databaseSizeBeforeCreate = sLoginRepository.findAll().size();

        // Create the SLogin
        restSLoginMockMvc.perform(post("/api/s-logins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sLogin)))
            .andExpect(status().isCreated());

        // Validate the SLogin in the database
        List<SLogin> sLoginList = sLoginRepository.findAll();
        assertThat(sLoginList).hasSize(databaseSizeBeforeCreate + 1);
        SLogin testSLogin = sLoginList.get(sLoginList.size() - 1);
        assertThat(testSLogin.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testSLogin.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testSLogin.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testSLogin.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testSLogin.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createSLoginWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sLoginRepository.findAll().size();

        // Create the SLogin with an existing ID
        sLogin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSLoginMockMvc.perform(post("/api/s-logins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sLogin)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SLogin> sLoginList = sLoginRepository.findAll();
        assertThat(sLoginList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSLogins() throws Exception {
        // Initialize the database
        sLoginRepository.saveAndFlush(sLogin);

        // Get all the sLoginList
        restSLoginMockMvc.perform(get("/api/s-logins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sLogin.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))));
    }

    @Test
    @Transactional
    public void getSLogin() throws Exception {
        // Initialize the database
        sLoginRepository.saveAndFlush(sLogin);

        // Get the sLogin
        restSLoginMockMvc.perform(get("/api/s-logins/{id}", sLogin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sLogin.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingSLogin() throws Exception {
        // Get the sLogin
        restSLoginMockMvc.perform(get("/api/s-logins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSLogin() throws Exception {
        // Initialize the database
        sLoginService.save(sLogin);

        int databaseSizeBeforeUpdate = sLoginRepository.findAll().size();

        // Update the sLogin
        SLogin updatedSLogin = sLoginRepository.findOne(sLogin.getId());
        updatedSLogin
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .isActive(UPDATED_IS_ACTIVE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restSLoginMockMvc.perform(put("/api/s-logins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSLogin)))
            .andExpect(status().isOk());

        // Validate the SLogin in the database
        List<SLogin> sLoginList = sLoginRepository.findAll();
        assertThat(sLoginList).hasSize(databaseSizeBeforeUpdate);
        SLogin testSLogin = sLoginList.get(sLoginList.size() - 1);
        assertThat(testSLogin.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testSLogin.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testSLogin.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSLogin.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testSLogin.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingSLogin() throws Exception {
        int databaseSizeBeforeUpdate = sLoginRepository.findAll().size();

        // Create the SLogin

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSLoginMockMvc.perform(put("/api/s-logins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sLogin)))
            .andExpect(status().isCreated());

        // Validate the SLogin in the database
        List<SLogin> sLoginList = sLoginRepository.findAll();
        assertThat(sLoginList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSLogin() throws Exception {
        // Initialize the database
        sLoginService.save(sLogin);

        int databaseSizeBeforeDelete = sLoginRepository.findAll().size();

        // Get the sLogin
        restSLoginMockMvc.perform(delete("/api/s-logins/{id}", sLogin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SLogin> sLoginList = sLoginRepository.findAll();
        assertThat(sLoginList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SLogin.class);
        SLogin sLogin1 = new SLogin();
        sLogin1.setId(1L);
        SLogin sLogin2 = new SLogin();
        sLogin2.setId(sLogin1.getId());
        assertThat(sLogin1).isEqualTo(sLogin2);
        sLogin2.setId(2L);
        assertThat(sLogin1).isNotEqualTo(sLogin2);
        sLogin1.setId(null);
        assertThat(sLogin1).isNotEqualTo(sLogin2);
    }
}
