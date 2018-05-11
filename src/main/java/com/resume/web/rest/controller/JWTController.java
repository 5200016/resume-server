package com.resume.web.rest.controller;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.resume.config.ApplicationProperties;
import com.resume.domain.SLogin;
import com.resume.repository.SLoginRepository;
import com.resume.security.jwt.JWTConfigurer;
import com.resume.security.jwt.TokenProvider;
import com.resume.web.rest.util.ResultObj;
import com.resume.web.rest.vm.LoginVM;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * User : 黄志成
 * Date : 2018/4/10
 * Desc : JWT安全验证
 */

@Api(description="获取登录token")
@RestController
@RequestMapping("/api")
public class JWTController {
    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    private final SLoginRepository loginRepository;

    public JWTController(TokenProvider tokenProvider, AuthenticationManager authenticationManager, SLoginRepository loginRepository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.loginRepository = loginRepository;
    }

    @PostMapping("/jwt-token")
    @Timed
    public ResultObj authorize(@Valid @RequestBody LoginVM loginVM) {
        SLogin login = loginRepository.findOneByUsername(loginVM.getUsername());
        if(login == null || login.isIsActive() == false){
            return new ResultObj(false,200,"用户不存在",null);
        }
        if(passwordEncoder.matches(loginVM.getPassword(), login.getPassword()) == false){
            return new ResultObj(false,200,"密码不正确",null);
        }
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, applicationProperties.getJwtprefix() + " " + jwt);
        return ResultObj.back(200, new JWTToken(jwt));
    }

    /**
     * JWT返回实体对象
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
