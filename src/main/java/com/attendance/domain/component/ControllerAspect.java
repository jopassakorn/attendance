package com.attendance.domain.component;

import com.attendance.config.oauth2.GoogleUser;
import com.attendance.domain.entity.Activated;
import com.attendance.domain.entity.Fingerprint;
import com.attendance.domain.service.ActivatedService;
import com.attendance.domain.service.AppService;
import com.attendance.domain.service.FingerprintService;
import com.attendance.domain.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by developer on 8/1/2561.
 */

@Component
@Aspect
public class ControllerAspect {

    @Autowired
    UserService userService;

    @Autowired
    FingerprintService fingerprintService;

    @Autowired
    ActivatedService activatedService;

    @Before("execution(* com.attendance.app.*.*Controller.*(..))")
    public void invokeBefore(JoinPoint joinPoint) throws URISyntaxException {
        Principal principal = null;
        ModelAndView modelAndView = null;
        for(Object object : joinPoint.getArgs()) {
            if(object instanceof Principal) {
                principal = (Principal) object;
            } else if(object instanceof ModelAndView) {
                modelAndView = (ModelAndView) object;
            }
        }
        if(principal != null && modelAndView != null) {
            Authentication auth = (Authentication) principal;
            ObjectMapper objectMapper = new ObjectMapper();
            Object detail = ((OAuth2Authentication) auth).getUserAuthentication().getDetails();
            Map<String, Object> map = objectMapper.convertValue(detail, Map.class);
            GoogleUser googleUser = GoogleUser.fromUserInfoMap(map);
            com.attendance.domain.entity.User user_info = userService.findByEmail(googleUser.getEmail());
            if (user_info == null && googleUser.getHd() != null){
                addUser(googleUser);
            }
            setIsLogin(modelAndView, googleUser);
        }
    }

    private void addUser(GoogleUser googleUser){
        com.attendance.domain.entity.User user = new com.attendance.domain.entity.User();
        user.setEmail(googleUser.getEmail());
        user.setFirstName(googleUser.getGivenName());
        user.setLastName(googleUser.getFamilyName());
        userService.saveUser(user);
        List<Fingerprint> fingerprintList = fingerprintService.getAll();
        for(Fingerprint fingerprint : fingerprintList){
            Activated activated = new Activated();
            activated.setUserId(user.getId());
            activated.setActivatedStatus(false);
            activated.setFingerprintId(fingerprint.getId());
            activatedService.save(activated);
        }
    }

    private void setIsLogin(ModelAndView modelAndView, GoogleUser googleUser) throws URISyntaxException {

        modelAndView.addObject("isLogin", true);
        modelAndView.addObject("googleUser", googleUser);
        if (googleUser.getHd() != null && googleUser.getHd().equals("mail.rmutk.ac.th")) {
            modelAndView.addObject("isRmutk", true);
            modelAndView.addObject("userId", userService.findByEmail(googleUser.getEmail()).getId());
        } else {
            modelAndView.addObject("isRmutk", false);
        }
        if (googleUser.getEmail().equals("575021001004@mail.rmutk.ac.th") || googleUser.getEmail().equals("srisuda.s@mail.rmutk.ac.th")) {
            modelAndView.addObject("isAdmin", true);
        }
    }
}
