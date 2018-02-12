package com.attendance.app.section;

import com.attendance.domain.bean.JasperPdfModelBean;
import com.attendance.domain.entity.*;
import com.attendance.domain.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by developer on 18/1/2561.
 */
@Controller
@RequestMapping("/section")
public class SectionController {

    @Autowired
    SectionService sectionService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    FingerprintService fingerprintService;

    @Autowired
    AppService appService;

    @Autowired
    SectionlogService sectionlogService;

    @Autowired
    UserService userService;

    @Autowired
    SectionExportPdfService sectionExportPdfService;

    @RequestMapping("/view/current/{userId}")
    public ModelAndView viewcurrent(@PathVariable("userId")int userId,ModelAndView modelAndView,Principal principal,Pageable pageable){
        modelAndView.setViewName("/section/view");
        modelAndView.addObject("dayList",appService.getDayList());
        modelAndView.addObject("semester",appService.getCurrentSemester());
        modelAndView.addObject("year",appService.getCurrentYear());
        Page<Section> sectionPages = sectionService.getUserCurrentSectionPages(userId,appService.getCurrentSemester(),appService.getCurrentYear(),pageable);
        modelAndView.addObject("page",sectionPages);
        modelAndView.addObject("sections",sectionPages.getContent());
        modelAndView.addObject("url","/view/current");
        modelAndView.addObject("hourList",appService.convertSecondsToHour(sectionPages.getContent()));
        modelAndView.addObject("currentSec",true);
        modelAndView.addObject("user",userService.findOne(userId));
        return modelAndView;
    }

    @RequestMapping("/view/past/{userId}")
    public ModelAndView viewpast(@PathVariable("userId")int userId,ModelAndView modelAndView,Principal principal){
        modelAndView.setViewName("/section/view");
        modelAndView.addObject("dayList",appService.getDayList());
        modelAndView.addObject("semester",appService.getCurrentSemester());
        modelAndView.addObject("year",appService.getCurrentYear());
        Page<Section> pastSectionPages = sectionService.getAllPastSection(sectionService.getAllSectionByUserId(userId));
        modelAndView.addObject("page",pastSectionPages);
        modelAndView.addObject("sections",pastSectionPages.getContent());
        modelAndView.addObject("url","/view/past");
        modelAndView.addObject("hourList",appService.convertSecondsToHour(pastSectionPages.getContent()));
        modelAndView.addObject("currentSec",false);
        modelAndView.addObject("user",userService.findOne(userId));
        return modelAndView;
    }

    @RequestMapping("/list/{userId}")
    public ModelAndView list(@PathVariable("userId") int userId, ModelAndView modelAndView, Principal principal, @PageableDefault(value= 10) Pageable pageable){
        modelAndView.setViewName("/section/userlist");
        modelAndView.addObject("dayList",appService.getDayList());
        modelAndView.addObject("semester",appService.getCurrentSemester());
        modelAndView.addObject("year",appService.getCurrentYear());
        Page<Section> sectionPages = sectionService.getUserCurrentSectionPages(userId,appService.getCurrentSemester(),appService.getCurrentYear(),pageable);
        modelAndView.addObject("page",sectionPages);
        modelAndView.addObject("sections",sectionPages.getContent());
        modelAndView.addObject("url","/section/userlist");
        modelAndView.addObject("hourList",appService.convertSecondsToHour(sectionPages.getContent()));

        Page<Section> pastSectionPages = sectionService.getAllPastSection(sectionService.getAllSectionByUserId(userId));
        modelAndView.addObject("pasSections",pastSectionPages.getContent());
        modelAndView.addObject("url","/section/userlist");
        modelAndView.addObject("pastHourList",appService.convertSecondsToHour(pastSectionPages.getContent()));
        return modelAndView;
    }

    @RequestMapping("/list")
    public ModelAndView list(ModelAndView modelAndView, Principal principal, @PageableDefault(value= 10) Pageable pageable){
        modelAndView.setViewName("/section/list");
        modelAndView.addObject("dayList",appService.getDayList());
        modelAndView.addObject("semester",appService.getCurrentSemester());
        modelAndView.addObject("year",appService.getCurrentYear());
        Page<Section> sectionPages = sectionService.getSectionPages(pageable);
        modelAndView.addObject("page",sectionPages);
        modelAndView.addObject("sections",sectionPages.getContent());
        modelAndView.addObject("url","/section/list");
        modelAndView.addObject("hourList",appService.convertSecondsToHour(sectionPages.getContent()));
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public void delete(@RequestParam(value = "id") String data){
        sectionlogService.deleteBySectionId(Integer.parseInt(data));
        sectionService.delete(Integer.parseInt(data));
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(ModelAndView modelAndView,Principal principal){
        modelAndView.setViewName("/section/create");
        User user = sectionlogService.getUser(principal);
        modelAndView.addObject("currentSectionList",sectionService.findAllCurrentSectionByUserId(user.getId()));
        modelAndView.addObject("noSubject",false);
        modelAndView.addObject("sectionAddForm",new SectionAddForm());
        modelAndView.addObject("fingerprintList",fingerprintService.getAll());
        List<Subject> subjectList = subjectService.getAllByDeleteStatus(false);
        if(subjectList.size() == 0){
            modelAndView.addObject("noSubject",true);
        }
        modelAndView.addObject("subjectList",subjectList);
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView add(@Valid SectionAddForm sectionAddForm, BindingResult bindingResult,ModelAndView modelAndView, Principal principal) throws ParseException {
        Section validSection = sectionService.findOneBySecStartedAndSemesterAndYearAndDayAndRoom(
                sectionAddForm.getSecStarted(),appService.getCurrentSemester(),appService.getCurrentYear(),sectionAddForm.getDay(),sectionAddForm.getRoomId());
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        User user = sectionlogService.getUser(principal);
        if(validSection != null) {
            FieldError error = new FieldError("sectionAddForm", "day", "There is section take room this day! please change to another room or another day");
            bindingResult.addError(error);
        }
        if (!sectionlogService.isDateMatchDay(sdf.parse(sectionAddForm.getStarted()),sdf.parse(sectionAddForm.getEnded()), sectionAddForm.getDay())) {
            FieldError error = new FieldError("sectionAddForm", "day", "Please match day with the started date!");
            bindingResult.addError(error);
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("section/create");
            modelAndView.addObject("sectionAddForm",new SectionAddForm());
            modelAndView.addObject("currentSectionList",sectionService.findAllCurrentSectionByUserId(user.getId()));
            modelAndView.addObject("noSubject",false);
            modelAndView.addObject("sectionAddForm",sectionAddForm);
            modelAndView.addObject("fingerprintList",fingerprintService.getAll());
            List<Subject> subjectList = subjectService.getAllByDeleteStatus(false);
            if(subjectList.size() == 0){
                modelAndView.addObject("noSubject",true);
            }
            modelAndView.addObject("subjectList",subjectList);
        }else{
            modelAndView.setViewName("redirect:/section/list/" + user.getId());
            Section section = new Section();
            section.setUserId(appService.getUser(principal).getId());
            section.setSubjectId(sectionAddForm.getSubjectId());
            section.setRoomId(sectionAddForm.getRoomId());
            section.setDay(sectionAddForm.getDay());
            section.setSecStarted(sectionAddForm.getSecStarted());
            section.setStarted(sdf.parse(sectionAddForm.getStarted()));
            section.setEnded(sdf.parse(sectionAddForm.getEnded()));
            section.setSemester(appService.getCurrentSemester());
            section.setYear(appService.getCurrentYear());
            sectionService.save(section);
            sectionlogService.generatedSectionlog(section.getStarted(), section.getEnded(), section.getId());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/semester/pdf")
    public ModelAndView exportallusersectionbysemesterpdf(@RequestParam(value = "started",required = false) String started,@RequestParam(value = "ended",required = false) String ended) throws ParseException {
        JasperPdfModelBean jasperPdfModelBean = sectionExportPdfService.getAllUserSectionPdfView(null,null);
        return new ModelAndView(jasperPdfModelBean, new HashMap());
    }

    @RequestMapping(value = "/semester/pdf/{started}/{ended}")
    public ModelAndView exportallusersectionbydatepdf(@PathVariable(value = "started")String started,@PathVariable(value = "ended")String ended) throws ParseException {
        Date startedDate = new SimpleDateFormat("yyyy-MM-dd").parse(started);
        Date endedDate = new SimpleDateFormat("yyyy-MM-dd").parse(ended);
        JasperPdfModelBean jasperPdfModelBean = sectionExportPdfService.getAllUserSectionPdfView(startedDate,endedDate);
        return new ModelAndView(jasperPdfModelBean, new HashMap());
    }
}
