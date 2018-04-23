package com.resume.web.rest;

import com.resume.ResumeApp;

import com.resume.domain.SUser;
import com.resume.repository.SUserRepository;
import com.resume.service.SUserService;
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
 * Test class for the SUserResource REST controller.
 *
 * @see SUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResumeApp.class)
public class SUserResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_NICKNAME = "AAAAAAAAAA";
    private static final String UPDATED_NICKNAME = "BBBBBBBBBB";

    private static final String DEFAULT_AVATAR = "AAAAAAAAAA";
    private static final String UPDATED_AVATAR = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final Integer DEFAULT_JOB_STATUS = 1;
    private static final Integer UPDATED_JOB_STATUS = 2;

    private static final String DEFAULT_EXTRA = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SUserRepository sUserRepository;

    @Autowired
    private SUserService sUserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSUserMockMvc;

    private SUser sUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SUserResource sUserResource = new SUserResource(sUserService);
        this.restSUserMockMvc = MockMvcBuilders.standaloneSetup(sUserResource)
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
    public static SUser createEntity(EntityManager em) {
        SUser sUser = new SUser()
            .username(DEFAULT_USERNAME)
            .nickname(DEFAULT_NICKNAME)
            .avatar(DEFAULT_AVATAR)
            .city(DEFAULT_CITY)
            .jobStatus(DEFAULT_JOB_STATUS)
            .extra(DEFAULT_EXTRA)
            .isActive(DEFAULT_IS_ACTIVE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return sUser;
    }

    @Before
    public void initTest() {
        sUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createSUser() throws Exception {
        int databaseSizeBeforeCreate = sUserRepository.findAll().size();

        // Create the SUser
        restSUserMockMvc.perform(post("/api/s-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sUser)))
            .andExpect(status().isCreated());

        // Validate the SUser in the database
        List<SUser> sUserList = sUserRepository.findAll();
        assertThat(sUserList).hasSize(databaseSizeBeforeCreate + 1);
        SUser testSUser = sUserList.get(sUserList.size() - 1);
        assertThat(testSUser.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testSUser.getNickname()).isEqualTo(DEFAULT_NICKNAME);
        assertThat(testSUser.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testSUser.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testSUser.getJobStatus()).isEqualTo(DEFAULT_JOB_STATUS);
        assertThat(testSUser.getExtra()).isEqualTo(DEFAULT_EXTRA);
        assertThat(testSUser.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testSUser.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testSUser.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createSUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sUserRepository.findAll().size();

        // Create the SUser with an existing ID
        sUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSUserMockMvc.perform(post("/api/s-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sUser)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SUser> sUserList = sUserRepository.findAll();
        assertThat(sUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSUsers() throws Exception {
        // Initialize the database
        sUserRepository.saveAndFlush(sUser);

        // Get all the sUserList
        restSUserMockMvc.perform(get("/api/s-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].nickname").value(hasItem(DEFAULT_NICKNAME.toString())))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(DEFAULT_AVATAR.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].jobStatus").value(hasItem(DEFAULT_JOB_STATUS)))
            .andExpect(jsonPath("$.[*].extra").value(hasItem(DEFAULT_EXTRA.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))));
    }

    @Test
    @Transactional
    public void getSUser() throws Exception {
        // Initialize the database
        sUserRepository.saveAndFlush(sUser);

        // Get the sUser
        restSUserMockMvc.perform(get("/api/s-users/{id}", sUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sUser.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.nickname").value(DEFAULT_NICKNAME.toString()))
            .andExpect(jsonPath("$.avatar").value(DEFAULT_AVATAR.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.jobStatus").value(DEFAULT_JOB_STATUS))
            .andExpect(jsonPath("$.extra").value(DEFAULT_EXTRA.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingSUser() throws Exception {
        // Get the sUser
        restSUserMockMvc.perform(get("/api/s-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSUser() throws Exception {
        // Initialize the database
        sUserService.save(sUser);

        int databaseSizeBeforeUpdate = sUserRepository.findAll().size();

        // Update the sUser
        SUser updatedSUser = sUserRepository.findOne(sUser.getId());
        updatedSUser
            .username(UPDATED_USERNAME)
            .nickname(UPDATED_NICKNAME)
            .avatar(UPDATED_AVATAR)
            .city(UPDATED_CITY)
            .jobStatus(UPDATED_JOB_STATUS)
            .extra(UPDATED_EXTRA)
            .isActive(UPDATED_IS_ACTIVE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restSUserMockMvc.perform(put("/api/s-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSUser)))
            .andExpect(status().isOk());

        // Validate the SUser in the database
        List<SUser> sUserList = sUserRepository.findAll();
        assertThat(sUserList).hasSize(databaseSizeBeforeUpdate);
        SUser testSUser = sUserList.get(sUserList.size() - 1);
        assertThat(testSUser.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testSUser.getNickname()).isEqualTo(UPDATED_NICKNAME);
        assertThat(testSUser.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testSUser.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testSUser.getJobStatus()).isEqualTo(UPDATED_JOB_STATUS);
        assertThat(testSUser.getExtra()).isEqualTo(UPDATED_EXTRA);
        assertThat(testSUser.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSUser.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testSUser.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingSUser() throws Exception {
        int databaseSizeBeforeUpdate = sUserRepository.findAll().size();

        // Create the SUser

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSUserMockMvc.perform(put("/api/s-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sUser)))
            .andExpect(status().isCreated());

        // Validate the SUser in the database
        List<SUser> sUserList = sUserRepository.findAll();
        assertThat(sUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSUser() throws Exception {
        // Initialize the database
        sUserService.save(sUser);

        int databaseSizeBeforeDelete = sUserRepository.findAll().size();

        // Get the sUser
        restSUserMockMvc.perform(delete("/api/s-users/{id}", sUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SUser> sUserList = sUserRepository.findAll();
        assertThat(sUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SUser.class);
        SUser sUser1 = new SUser();
        sUser1.setId(1L);
        SUser sUser2 = new SUser();
        sUser2.setId(sUser1.getId());
        assertThat(sUser1).isEqualTo(sUser2);
        sUser2.setId(2L);
        assertThat(sUser1).isNotEqualTo(sUser2);
        sUser1.setId(null);
        assertThat(sUser1).isNotEqualTo(sUser2);
    }
}
