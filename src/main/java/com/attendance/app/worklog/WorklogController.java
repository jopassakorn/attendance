package com.attendance.app.worklog;

import com.attendance.domain.bean.JasperPdfModelBean;
import com.attendance.domain.entity.Fingerprint;
import com.attendance.domain.entity.Semesterlog;
import com.attendance.domain.entity.User;
import com.attendance.domain.entity.Worklog;
import com.attendance.domain.repository.SemesterlogRepository;
import com.attendance.domain.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

/**
 * Created by developer on 22/1/2561.
 */
@Controller
@RequestMapping("/worklog")
public class WorklogController {

    @Autowired
    ExportPdfService exportPdfService;

    @Autowired
    WorklogService worklogService;

    @Autowired
    UserService userService;

    @Autowired
    SemesterlogService semesterlogService;

    @Autowired
    FingerprintService fingerprintService;

    @RequestMapping("/view/{id}")
    public ModelAndView view(@PathVariable("id") int id, ModelAndView modelAndView, Principal principal){
        modelAndView.setViewName("worklog/view");
        User user = userService.findOne(id);
        List<Worklog> worklogList = worklogService.getAllCurrentWorklogByUserId(id);
        modelAndView.addObject("workResult",worklogService.getWorkResult(worklogList));
        modelAndView.addObject("lateMinMap",worklogService.getLateMinMap(worklogList,id));
        modelAndView.addObject("worklogList",worklogList);
        modelAndView.addObject("workTimeMap",worklogService.getSectionMap(id));
        modelAndView.addObject("semester",worklogService.getCurrentSemester() + "/" + worklogService.getCurrentYear());
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @RequestMapping("/pdf/{id}")
    public ModelAndView pdf(@PathVariable("id")int id){
        JasperPdfModelBean jasperPdfModelBean = exportPdfService.getWorklogPdfView(id);
        return new ModelAndView(jasperPdfModelBean, new HashMap());
    }

    @RequestMapping("/control")
    public ModelAndView control(ModelAndView modelAndView,Principal principal){
        Semesterlog semesterlog = semesterlogService.findOneBySemesterAndYear(userService.getCurrentSemester(),userService.getCurrentYear());
        modelAndView.setViewName("/worklog/control");
        modelAndView.addObject("fingerprintAddForm",new FingerprintAddForm());
        modelAndView.addObject("fingerprintList",fingerprintService.getAll());
        modelAndView.addObject("currentSemester",semesterlog);
        modelAndView.addObject("nextSemester",semesterlogService.getNextSemester());
        modelAndView.addObject("nextYear",semesterlogService.getNextYear());
        modelAndView.addObject("semesterlogAddForm",new SemesterlogAddForm());
        return modelAndView;
    }

    @RequestMapping("/semesterlog/update")
    public ModelAndView update(ModelAndView modelAndView,SemesterlogAddForm semesterlogAddForm){
        Semesterlog semesterlog = new Semesterlog();
        semesterlog.setStarted(semesterlogAddForm.getStarted());
        semesterlog.setEnded(semesterlogAddForm.getEnded());
        semesterlog.setSemester(semesterlogService.getNextSemester());
        semesterlog.setYear(semesterlogService.getNextYear());
        semesterlogService.save(semesterlog);
        modelAndView.setViewName("redirect:/worklog/control");
        return modelAndView;
    }

    @RequestMapping("/fingerprint/create")
    public ModelAndView createfinger(ModelAndView modelAndView,FingerprintAddForm fingerprintAddForm){
        Fingerprint fingerprint = new Fingerprint();
        fingerprint.setFingerprintId(fingerprintAddForm.getFingerprintId());
        fingerprint.setRoom(fingerprintAddForm.getRoom());
        fingerprintService.save(fingerprint);
        modelAndView.setViewName("redirect:/worklog/control");
        return modelAndView;
    }

    @RequestMapping("/fingerprint/edit")
    public ModelAndView editfinger(ModelAndView modelAndView,FingerprintAddForm fingerprintAddForm){
        Fingerprint fingerprint = fingerprintService.findOne(fingerprintAddForm.getId());
        fingerprint.setRoom(fingerprintAddForm.getRoom());
        fingerprintService.save(fingerprint);
        modelAndView.setViewName("redirect:/worklog/control");
        return modelAndView;
    }
}

