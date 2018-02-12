package com.attendance.domain.service;

import com.attendance.domain.bean.*;
import com.attendance.domain.entity.Section;
import com.attendance.domain.entity.User;
import com.attendance.domain.entity.Worklog;
import com.attendance.domain.repository.SectionRepository;
import com.attendance.domain.repository.SectionlogRepository;
import com.attendance.domain.repository.SemesterlogRepository;
import com.attendance.domain.repository.WorklogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WorklogExportPdfService extends AppService{

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

    @Autowired
    UserService userService;

    public JasperPdfModelBean getWorklogBySemesterPdfView(Date started,Date ended){
        List<User> userList = userService.findAll();
        List<AllUserWorklogPdfForm> beanList = new ArrayList<>();
        setAllUserWorklogPdfFormListInfo(beanList,userList,started,ended);

        String fileName = "All_user_worklog_report";
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new JasperPdfModelBean(jasperPath + "/alluser/AllUserWorklogReport.jrxml",beanList,fileName,jasperPath);
    }

    public void setAllUserWorklogPdfFormListInfo(List<AllUserWorklogPdfForm> beanList, List<User> userList,Date started,Date ended) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        for (User user : userList) {
            AllUserWorklogPdfForm bean = new AllUserWorklogPdfForm();
            bean.setName(user.getFirstName() + ' ' + user.getLastName());
            List<Worklog> worklogList = new ArrayList<>();

            if (started != null && ended != null) {
                worklogList = worklogRepository.findAllByUserIdAndStartDateAndEnddate(user.getId(), started, ended);
                bean.setIsSemesterReport(false);
                bean.setStarted(started.toString());
                bean.setEnded(ended.toString());
            } else {
                worklogList = worklogRepository.findAllByUserId(user.getId());
                bean.setIsSemesterReport(true);
                bean.setSemester(Integer.toString(getCurrentSemester()));
                bean.setYear(Integer.toString(getCurrentYear()));
            }

            List<Section> sectionList = sectionRepository.findAllSectionByUserId(user.getId());
            Map<Integer, String> lateMinMap = worklogService.getLateMinMap(worklogList, user.getId());
            List<WorklogPdfForm> worklogPdfForms = new ArrayList<>();
            int lateTime = 0;
            int ontime = 0;
            int ongoing = 0;
            int absent = 0;
            int present = 0;
            Map<Integer, String> map = new HashMap<>();
            for (Section section : sectionList) {
                if (section.getSecStarted() < 34201) {
                    map.put(section.getDay(), section.getSubject().getName() + '(' + getHour(section.getSecStarted()) + ':' + getMinute(section.getSecStarted()) + ')');
                }
            }
            for (int i = 0; i < 6; i++) {
                if (map.get(i) == null) {
                    map.put(i, "No class");
                }
            }
            for (Worklog worklog : worklogList) {
                if (worklog.getStatus().equals("late")) {
                    lateTime += 1;
                    present += 1;
                } else if (worklog.getStatus().equals("ontime")) {
                    ontime += 1;
                    present += 1;
                } else if (worklog.getStatus().equals("waiting")) {
                    ongoing += 1;
                } else if (worklog.getStatus().equals("absent")) {
                    absent += 1;
                }
                if(!worklog.getStatus().equals("waiting")) {
                    WorklogPdfForm worklogPdfForm = new WorklogPdfForm();
                    worklogPdfForm.setDate(format1.format(worklog.getDate()).toString());
                    worklogPdfForm.setStatus(worklog.getStatus());
                    worklogPdfForm.setLateMin(lateMinMap.get(worklog.getId()));
                    worklogPdfForm.setClassDay(map.get(worklog.getDate().getDay() - 1));
                    worklogPdfForm.setClockInTime(worklog.getClockInTime().toString());
                    worklogPdfForm.setNote(worklog.getNote() == null ? "" : worklog.getNote());
                    worklogPdfForms.add(worklogPdfForm);
                }
            }
            bean.setTotalPresent(present);
            bean.setTotalAbsent(absent);
            bean.setTotalLate(lateTime);
            bean.setTotalOntime(ontime);
            bean.setWorklogPdfFormList(worklogPdfForms);
            beanList.add(bean);
        }
    }
}
