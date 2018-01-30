package com.attendance.domain.service;

import com.attendance.domain.bean.*;
import com.attendance.domain.entity.*;
import com.attendance.domain.repository.SectionRepository;
import com.attendance.domain.repository.SectionlogRepository;
import com.attendance.domain.repository.SemesterlogRepository;
import com.attendance.domain.repository.WorklogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by developer on 27/11/2560.
 */
@Service
public class ExportPdfService extends AppService {

    @Value("${attendance.jasper.path}")
    private String jasperPath;

    @Autowired
    AppService appService;

    @Autowired
    WorklogRepository worklogRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    SectionlogRepository sectionlogRepository;

    @Autowired
    SectionlogService sectionlogService;

    @Autowired
    SemesterlogRepository semesterlogRepository;

    @Autowired
    WorklogService worklogService;

//    get information for pdf put into beanList
    public JasperPdfModelBean getSectionlogPdfView(int sectionId){
        List<SectionPdfFormList> beanList = new ArrayList<>();
        List<Sectionlog> sectionlogList = new ArrayList<>();
        sectionlogList = sectionlogRepository.findAllBySectionId(sectionId);
        Section section = sectionRepository.findOne(sectionId);
        beanList = setSectionPdfFormList(sectionlogList,section);

        String fileName = "Sectionlog_user_" + section.getUser().getFirstName() + "_" + section.getUser().getLastName()
                + "_" + section.getSubject().getName() + appService.getCurrentSemester() + "/" + appService.getCurrentYear();
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new JasperPdfModelBean(jasperPath + "/section/sectionlog_report.jrxml",beanList,fileName,jasperPath);
    }

    public JasperPdfModelBean getAllCurrentSectionPdfView(int userId){
        User user = userRepository.findById(userId);
        List<Section> sectionList = sectionRepository.findAllSectionByUserIdAndSemesterAndYear(userId,getCurrentSemester(),getCurrentYear());
        List<AllSectionPdfFormList> beanList = new ArrayList<>();
        beanList = setAllSectionPdfFormListInfo(user,sectionList);

        String fileName = user.getFirstName() + "_" + user.getLastName() +
                "_section_report_of_" + getCurrentSemester() + "/" + getCurrentYear();
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new JasperPdfModelBean(jasperPath + "/allsection/allsection_report.jrxml",beanList,fileName,jasperPath);
    }

    public JasperPdfModelBean getAllPastSectionPdfView(int userId){
        User user = userRepository.findById(userId);
        List<Section> sectionList = sectionRepository.findAllPastSection(userId,getCurrentSemester(),getCurrentYear());
        List<AllSectionPdfFormList> beanList = new ArrayList<>();
        beanList = setAllSectionPdfFormListInfo(user,sectionList);

        String fileName = "all_user_" + user.getFirstName() + "_" + user.getLastName() +
                "_section_report_of_" + getCurrentSemester() + "/" + getCurrentYear();
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new JasperPdfModelBean(jasperPath + "/allsection/allsection_report.jrxml",beanList,fileName,jasperPath);
    }

    public List<AllSectionPdfFormList> setAllSectionPdfFormListInfo(User user,List<Section> sectionList){
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        List<AllSectionPdfFormList> allSectionPdfFormLists = new ArrayList<>();
        AllSectionPdfFormList allSectionPdfFormList = new AllSectionPdfFormList();
        List<AllSectionPdfForm> allSectionPdfForms = new ArrayList<>();
        List<AllSectionlogPdfForm> allSectionlogPdfForms = new ArrayList<>();
        allSectionPdfFormList.setName(user.getFirstName() + " " + user.getLastName());
        allSectionPdfFormList.setSemester(getCurrentSemester() + "/" + getCurrentYear());
        String[] days = getDayList();
        int[] result = {0,0,0,0,0};
        for(Section section : sectionList){
            AllSectionPdfForm allSectionPdfForm = new AllSectionPdfForm();
            allSectionPdfForm.setName(section.getSubject().getName());
            allSectionPdfForm.setDay(days[section.getDay()]);
            allSectionPdfForm.setStarted(format1.format(section.getStarted()).toString());
            allSectionPdfForm.setEnded(format1.format(section.getEnded()).toString());
            allSectionPdfForm.setTime(getHour(section.getSecStarted()) + ":" + getMinute(section.getSecStarted()));
            allSectionPdfForms.add(allSectionPdfForm);
            List<Sectionlog> sectionlogs = sectionlogRepository.findAllBySectionId(section.getId());
            for(Sectionlog sectionlog : sectionlogs){
                AllSectionlogPdfForm allSectionlogPdfForm = new AllSectionlogPdfForm();
                allSectionlogPdfForm.setDate(format1.format(sectionlog.getWorkDate()).toString());
                allSectionlogPdfForm.setLateMin(getLateMinutes(section.getSecStarted(),sectionlog.getClockInSec()));
                allSectionlogPdfForm.setStatus(sectionlog.getStatus());
                allSectionlogPdfForm.setClassName(section.getSubject().getName());
                allSectionlogPdfForms.add(allSectionlogPdfForm);
                if(sectionlog.getStatus().equals("waiting")){
                    result[0] += 1;
                }else if(sectionlog.getStatus().equals("ontime")){
                    result[2] += 1;
                    result[4] += 1;
                }else if(sectionlog.getStatus().equals("late")){
                    result[1] += 1;
                    result[4] += 1;
                }else if(sectionlog.getStatus().equals("absent")){
                    result[3] += 1;
                }
            }
        }
        allSectionPdfFormList.setAllSectionPdfForms(allSectionPdfForms);
        allSectionPdfFormList.setAllSectionlogPdfForms(allSectionlogPdfForms);
        allSectionPdfFormList.setTotalLate(Integer.toString(result[1]));
        allSectionPdfFormList.setTotalOngoing(Integer.toString(result[0]));
        allSectionPdfFormList.setTotalOntime(Integer.toString(result[2]));
        allSectionPdfFormList.setTotalAbsent(Integer.toString(result[3]));
        allSectionPdfFormList.setTotalPresent(Integer.toString(result[4]));
        allSectionPdfFormLists.add(allSectionPdfFormList);
        return allSectionPdfFormLists;
    }

    public JasperPdfModelBean getWorklogPdfView(int userId){
        Semesterlog semesterlog = semesterlogRepository.findOneBySemesterAndYear(getCurrentSemester(),getCurrentYear());
        User user = userRepository.findById(userId);
        List<WorklogPdfFormList> beanList = new ArrayList<>();
        List<Worklog> worklogList = new ArrayList<>();
        worklogList = worklogRepository.findAllByUserIdAndStartDateAndEnddate(userId,semesterlog.getStarted(),semesterlog.getEnded());
        List<Section> section = sectionRepository.findAllSectionByUserId(userId);
        beanList = setWorklogPdfFormList(userId,worklogList,section);

        String fileName = "Worklog_user_" + user.getFirstName() + '_' + user.getLastName() + '_' + getCurrentSemester() + '/' + getCurrentYear() ;
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new JasperPdfModelBean(jasperPath + "/worklog/worklog_report.jrxml",beanList,fileName,jasperPath);
    }

    public List<WorklogPdfFormList> setWorklogPdfFormList(int userId,List<Worklog> worklogList,List<Section>sectionList){
        User user = userRepository.findById(userId);
        Map<Integer,String> lateMinMap = worklogService.getLateMinMap(worklogList,userId);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        List<WorklogPdfFormList> worklogPdfFormLists = new ArrayList<>();
        List<WorklogPdfForm> worklogPdfForms = new ArrayList<>();
        WorklogPdfFormList worklogPdfFormList = new WorklogPdfFormList();
        worklogPdfFormList.setName(user.getFirstName() + " " + user.getLastName());
        worklogPdfFormList.setSemester(getCurrentSemester() + "/" + getCurrentYear());
        int lateTime = 0;
        int ontime = 0;
        int ongoing = 0;
        int absent = 0;
        int present = 0;
        Map<Integer,String> map = new HashMap<>();
        for(Section section : sectionList){
            if(section.getSecStarted() < 34201){
                map.put(section.getDay(),section.getSubject().getName() + '(' + getHour(section.getSecStarted()) + ':' + getMinute(section.getSecStarted()) + ')');
            }
        }
        for(int i = 0;i < 6;i++){
            if(map.get(i) == null){
                map.put(i,"No class");
            }
        }
        for(Worklog worklog : worklogList){
            WorklogPdfForm worklogPdfForm = new WorklogPdfForm();
            worklogPdfForm.setDate(format1.format(worklog.getDate()).toString());
            worklogPdfForm.setStatus(worklog.getStatus());
            worklogPdfForm.setLateMin(lateMinMap.get(worklog.getId()));
            worklogPdfForm.setClassDay(map.get(worklog.getDate().getDay() - 1));
            worklogPdfForm.setClockInTime(worklog.getClockInTime().toString());
            worklogPdfForms.add(worklogPdfForm);
            if(worklog.getStatus().equals("late")){
                lateTime += 1;
                present += 1;
            }else if(worklog.getStatus().equals("ontime")){
                ontime += 1;
                present += 1;
            }else if(worklog.getStatus().equals("waiting")){
                ongoing += 1;
            }else if(worklog.getStatus().equals("absent")){
                absent += 1;
            }
        }
        worklogPdfFormList.setWorklogPdfForm(worklogPdfForms);
        worklogPdfFormList.setLateTime(Integer.toString(lateTime));
        worklogPdfFormList.setOntime(Integer.toString(ontime));
        worklogPdfFormList.setOngoing(Integer.toString(ongoing));
        worklogPdfFormList.setAbsent(Integer.toString(absent));
        worklogPdfFormList.setPresent(Integer.toString(present));
        worklogPdfFormLists.add(worklogPdfFormList);
        return worklogPdfFormLists;
    }

    public List<SectionPdfFormList> setSectionPdfFormList(List<Sectionlog> sectionlogList,Section section){
        List<SectionPdfFormList> beanList = new ArrayList<>();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        int[] result = calSectionlogResult(sectionlogList);
        SectionPdfFormList sectionPdfFormList = new SectionPdfFormList();
        sectionPdfFormList.setName(section.getUser().getFirstName() + " " + section.getUser().getLastName());
        sectionPdfFormList.setSemester(appService.getCurrentSemester() + "/" + appService.getCurrentYear());
        sectionPdfFormList.setDay(getDay(section.getDay()));
        sectionPdfFormList.setSubjectName(section.getSubject().getName());
        sectionPdfFormList.setStarted(format1.format(section.getStarted()).toString());
        sectionPdfFormList.setEnded(format1.format(section.getEnded()).toString());
        sectionPdfFormList.setTime(appService.getHour(section.getSecStarted()) + ":" + appService.getMinute(section.getSecStarted()));
        sectionPdfFormList.setPresent(Integer.toString(result[1]));
        sectionPdfFormList.setAbsent(Integer.toString(result[2]));
        sectionPdfFormList.setLateTimes(Integer.toString(result[3]));
        sectionPdfFormList.setOnTimes(Integer.toString(result[4]));
        List<SectionlogPdfForm> sectionlogPdfForms = new ArrayList<>();
        for(Sectionlog sectionlog : sectionlogList){
            SectionlogPdfForm sectionlogPdfForm = new SectionlogPdfForm();
            sectionlogPdfForm.setLateMin(sectionlogService.getLateMinutes(section.getSecStarted(),sectionlog.getClockInSec()));
            sectionlogPdfForm.setStatus(sectionlog.getStatus());
            sectionlogPdfForm.setWorkDate(format1.format(sectionlog.getWorkDate()).toString());
            sectionlogPdfForms.add(sectionlogPdfForm);
        }
        sectionPdfFormList.setSectionlogPdfForms(sectionlogPdfForms);
        beanList.add(sectionPdfFormList);
        return beanList;
    }

    public void getJasperPdfPreview(byte[] documentInBytes,HttpServletResponse response) throws Exception {
        response.setDateHeader("Expires", -1);
        response.setContentType("application/pdf");
        response.setContentLength(documentInBytes.length);
        response.getOutputStream().write(documentInBytes);
        response.flushBuffer();
    }
}
