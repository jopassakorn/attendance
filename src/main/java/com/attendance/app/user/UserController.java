package com.attendance.app.user;

import com.attendance.config.oauth2.GoogleUser;
import com.attendance.domain.entity.Section;
import com.attendance.domain.entity.User;
import com.attendance.domain.entity.Worklog;
import com.attendance.domain.service.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by developer on 17/1/2561.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AppService appService;

    @Autowired
    SectionService sectionService;

    @Autowired
    FingerprintService fingerprintService;

    @Autowired
    SectionlogService sectionlogService;

    @Autowired
    WorklogService worklogService;

    @RequestMapping("/reg_finger")
    public ModelAndView registerfinger(ModelAndView modelAndView, Principal principal){
        modelAndView.setViewName("/user/activate");
        Authentication auth = (Authentication) principal;
        ObjectMapper objectMapper = new ObjectMapper();
        Object detail = ((OAuth2Authentication) auth).getUserAuthentication().getDetails();
        Map<String, Object> map = objectMapper.convertValue(detail, Map.class);
        GoogleUser googleUser = GoogleUser.fromUserInfoMap(map);
        User user = userService.findByEmail(googleUser.getEmail());
        if(!user.getActivated()) {
            Random random = new Random();
            int acCode = (int)(Math.random() * 9000) + 999;
            while(userService.findByAcCode(acCode) != null) {
                acCode = (int)(Math.random() * 9000) + 999;
            }
            user.setActivatedCode(acCode);
            userService.saveUser(user);
            modelAndView.addObject("activatedCode", acCode);
            modelAndView.addObject("fingerprintLocation", fingerprintService.findOneById("pi-server").getRoom());
        }else{
            modelAndView.setViewName("/user/home");
        }
        return modelAndView;
    }

    @RequestMapping("/profile/{id}")
    public ModelAndView profile(@PathVariable("id")int id, ModelAndView modelAndView,Principal principal){
        User user = userService.findOne(id);
        modelAndView.setViewName("/user/profile");
        modelAndView.addObject("thisSemester",appService.getCurrentSemester() + "/" + appService.getCurrentYear());
        List<Section> sectionList = sectionService.getAllSectionByUserId(user.getId());
        List<Section> currentSectionList = sectionService.findAllCurrentSectionByUserId(user.getId());
        int[] currentSectionResult = sectionlogService.getSectionStatus(currentSectionList);
        int[] AllSectionResult = sectionlogService.getSectionStatus(sectionList);
        modelAndView.addObject("totalCurrentSection",currentSectionList.size());
        modelAndView.addObject("currentSectionResult",currentSectionResult);
        modelAndView.addObject("totalAllSection",sectionList.size());
        modelAndView.addObject("allSectionResult",AllSectionResult);
        List<Worklog> worklogs = worklogService.getAllCurrentWorklogByUserId(user.getId());
        modelAndView.addObject("totalCurrentWorklogs",worklogs.size());
        modelAndView.addObject("currentWorklogResult",worklogService.getWorklogStatus(worklogs));
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @RequestMapping("/list")
    public ModelAndView list(ModelAndView modelAndView, Principal principal, Pageable pageable){
        modelAndView.setViewName("user/list");
        Page<User> pages = userService.findAll(pageable);
        modelAndView.addObject("page",pages);
        modelAndView.addObject("users",pages.getContent());
        modelAndView.addObject("url","/user/list");
        return modelAndView;
    }
}
