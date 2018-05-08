package com.resume.service.impl;

import com.resume.domain.SUser;
import com.resume.repository.SUserRepository;
import com.resume.service.SLoginService;
import com.resume.domain.SLogin;
import com.resume.repository.SLoginRepository;
import com.resume.web.rest.util.DateUtil;
import com.resume.web.rest.util.ResultObj;
import com.resume.web.rest.util.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing SLogin.
 */
@Service
@Transactional
public class SLoginServiceImpl implements SLoginService{

    private final Logger log = LoggerFactory.getLogger(SLoginServiceImpl.class);

    private final SLoginRepository sLoginRepository;

    private final PasswordEncoder passwordEncoder;

    private final SUserRepository userRepository;

    public SLoginServiceImpl(SLoginRepository sLoginRepository, PasswordEncoder passwordEncoder, SUserRepository userRepository) {
        this.sLoginRepository = sLoginRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * Save a sLogin.
     *
     * @param sLogin the entity to save
     * @return the persisted entity
     */
    @Override
    public SLogin save(SLogin sLogin) {
        log.debug("Request to save SLogin : {}", sLogin);
        return sLoginRepository.save(sLogin);
    }

    /**
     *  Get all the sLogins.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SLogin> findAll() {
        log.debug("Request to get all SLogins");
        return sLoginRepository.findAll();
    }

    /**
     *  Get one sLogin by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SLogin findOne(Long id) {
        log.debug("Request to get SLogin : {}", id);
        return sLoginRepository.findOne(id);
    }

    /**
     *  Delete the  sLogin by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SLogin : {}", id);
        sLoginRepository.delete(id);
    }

    /**
     * 用户注册
     */
    @Override
    public ResultObj registerUser(SLogin login) {
        SLogin user = sLoginRepository.findOneByUsername(login.getUsername());
        SUser sUser = userRepository.findUserByUsername(login.getUsername());
        if(!TypeUtils.isEmpty(user) && !TypeUtils.isEmpty(sUser)){
            return ResultObj.backInfo(false,200,"用户已存在",false);
        }

        //保存用户登录信息
        login.setPassword(passwordEncoder.encode(login.getPassword()));
        login.setIsActive(true);
        login.setCreateTime(DateUtil.getZoneDateTime());
        login.setUpdateTime(DateUtil.getZoneDateTime());

        //保存用户基本信息
        SUser userInfo = new SUser();
        userInfo.setNickname(login.getUsername());
        userInfo.setUsername(login.getUsername());
        userInfo.setAvatar("../image/emoji.png");
        userInfo.setCity("未知城市");
        userInfo.setJobStatus(0);
        userInfo.setExtra("您还没有留下什么痕迹哦~");
        userInfo.setIsActive(true);
        userInfo.setCreateTime(DateUtil.getZoneDateTime());
        userInfo.setUpdateTime(DateUtil.getZoneDateTime());
        userRepository.save(userInfo);
        return ResultObj.back(200,sLoginRepository.save(login));
    }

    /**
     * 通过用户名查询用户登录信息
     * @return
     */
    @Override
    public SLogin findUserByUsername(String username) {
        if(TypeUtils.isEmpty(username)){
           return null ;
        }
        return sLoginRepository.findOneByUsername(username);
    }

    /**
     * 修改密码
     */
    @Override
    public ResultObj updatePassword(String username, String newPwd, String oldPwd) {
        SLogin login = sLoginRepository.findOneByUsername(username);
        if(passwordEncoder.matches(oldPwd, login.getPassword()) == true){
            login.setUpdateTime(DateUtil.getZoneDateTime());
            login.setPassword(passwordEncoder.encode(newPwd));
            sLoginRepository.save(login);
            return ResultObj.backInfo(true,200,"修改成功",true);
        }else {
            return ResultObj.backInfo(false,200,"修改失败",false);
        }
    }
}
