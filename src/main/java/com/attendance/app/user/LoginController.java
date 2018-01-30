package com.attendance.app.user;

import com.attendance.config.oauth2.GoogleUser;
import com.attendance.domain.entity.Section;
import com.attendance.domain.entity.User;
import com.attendance.domain.entity.Worklog;
import com.attendance.domain.repository.WorklogRepository;
import com.attendance.domain.service.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by developer on 3/1/2561.
 */

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    SectionService sectionService;

    @Autowired
    AppService appService;

    @Autowired
    WorklogService worklogService;

    @Autowired
    FingerprintService fingerprintService;

    @RequestMapping(value = "user",method = RequestMethod.GET)
    @ResponseBody
    public Principal user(Principal user){
        return user;
    }

    @RequestMapping(value = {"/home","/"}, method = RequestMethod.GET)
    public ModelAndView home(ModelAndView modelAndView, Principal principal,HttpServletRequest req){
        modelAndView.setViewName("/user/home");
        modelAndView.addObject("welcomeModal",false);
        if(principal == null){
            modelAndView.setViewName("/user/login");
        }else {
            Authentication auth = (Authentication) principal;
            ObjectMapper objectMapper = new ObjectMapper();
            Object detail = ((OAuth2Authentication) auth).getUserAuthentication().getDetails();
            Map<String, Object> map = objectMapper.convertValue(detail, Map.class);
            GoogleUser googleUser = GoogleUser.fromUserInfoMap(map);
            User user_info = userService.findByEmail(googleUser.getEmail());
            if (user_info == null) {
                modelAndView.setViewName("shared/main");
                modelAndView.addObject("isRmutkUser", false);
                return modelAndView;
            } else if (!user_info.getActivated()) {
                modelAndView.addObject("isActivated", false);
            }else if(user_info.getActivated()){
                modelAndView.addObject("isActivated",true);
            }
            List<Worklog> worklogList = worklogService.getAllCurrentWorklogByUserId(user_info.getId());
            if(worklogList.size() == 0 && user_info.getActivated()){
                worklogService.generateWorklog(new Date(),user_info.getId());
                modelAndView.addObject("welcomeModal",true);
            }
            Section section = sectionService.findNextSection(user_info.getId());
            if(section == null){
                modelAndView.addObject("isNoSection",true);
            }else{
                modelAndView.addObject("nextSection",section);
                modelAndView.addObject("nextSectionTime",sectionService.getHour(section.getSecStarted()) + ":" + sectionService.getMinute(section.getSecStarted()));
                modelAndView.addObject("dayMap",sectionService.getDayList());
                modelAndView.addObject("room",fingerprintService.findOne(section.getRoomId()).getRoom());
            }
        }


        return modelAndView;
    }

    @RequestMapping("/logout")
    public void exit(HttpServletRequest request, HttpServletResponse response,
                             ModelAndView modelAndView) {
        // token can be revoked here if needed
        new SecurityContextLogoutHandler().logout(request, null, null);
        try {
            //sending back to client app
            response.sendRedirect(request.getHeader("referer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
