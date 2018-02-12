package com.attendance.domain.service;

import com.attendance.domain.bean.AllUserSectionPdfForm;
import com.attendance.domain.bean.AllUserWorklogPdfForm;
import com.attendance.domain.bean.JasperPdfModelBean;
import com.attendance.domain.bean.SectionlogPdfForm;
import com.attendance.domain.entity.Section;
import com.attendance.domain.entity.Sectionlog;
import com.attendance.domain.entity.User;
import com.attendance.domain.repository.SectionRepository;
import com.attendance.domain.repository.SectionlogRepository;
import com.attendance.domain.repository.SemesterlogRepository;
import com.attendance.domain.repository.WorklogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SectionExportPdfService extends AppService{


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

    public JasperPdfModelBean getAllUserSectionPdfView(Date started, Date ended){
        List<User> userList = userService.findAll();
        List<AllUserSectionPdfForm> beanList = new ArrayList<>();
        setAllUserSectionfFormListInfo(beanList,userList,started,ended);

        String fileName = "All_user_worklog_report";
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new JasperPdfModelBean(jasperPath + "/alluser/allsection_report.jrxml",beanList,fileName,jasperPath);
    }

    public void setAllUserSectionfFormListInfo(List<AllUserSectionPdfForm> beanList,List<User> userList,Date started,Date ended){
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        for(User user : userList){
            AllUserSectionPdfForm allUserSectionPdfForm = new AllUserSectionPdfForm();
            allUserSectionPdfForm.setName(user.getFirstName() + ' ' + user.getLastName());
            List<Section> sectionList = sectionRepository.findAllSectionByUserIdAndSemesterAndYear(user.getId(),getCurrentSemester(),getCurrentYear());
            if(started != null && ended != null){
                allUserSectionPdfForm.setStarted(started.toString());
                allUserSectionPdfForm.setEnded(ended.toString());
                allUserSectionPdfForm.setIsSemesterReport(false);
            }else {
                allUserSectionPdfForm.setIsSemesterReport(true);
                allUserSectionPdfForm.setSemesterYear(Integer.toString(getCurrentSemester()) + '/' + Integer.toString(getCurrentYear()));
            }
            for(Section section : sectionList){
                int ontime = 0;
                int late = 0;
                int absent = 0;
                int present = 0;
                int holiday = 0;
                int ongoing = 0;
                List<SectionlogPdfForm> sectionlogPdfForms = new ArrayList<>();

                if(started == null && ended == null) {
                    List<Sectionlog> sectionlogcList = sectionlogRepository.findAllBySectionId(section.getId());
                    for(Sectionlog sectionlog : sectionlogcList){
                        if(!sectionlog.getStatus().equals("waiting")){
                            SectionlogPdfForm sectionlogPdfForm = new SectionlogPdfForm();
                            sectionlogPdfForm.setLateMin(sectionlogService.getLateMinutes(section.getSecStarted(),sectionlog.getClockInSec()));
                            sectionlogPdfForm.setStatus(sectionlog.getStatus());
                            sectionlogPdfForm.setWorkDate(format1.format(sectionlog.getWorkDate()).toString());
                            sectionlogPdfForms.add(sectionlogPdfForm);
                        }
                        if(sectionlog.getStatus().equals("waiting")){
                            ongoing = ongoing + 1;
                        }else if(sectionlog.getStatus().equals("ontime")){
                            ontime = ontime + 1;
                            present = present + 1;
                        }else if(sectionlog.getStatus().equals("late")){
                            late = late + 1;
                            present = present + 1;
                        }else if(sectionlog.getStatus().equals("absent")){
                            absent = absent + 1;
                        }

                    }

                }

                allUserSectionPdfForm.setSubjectName(section.getSubject().getName());
                allUserSectionPdfForm.setTotalAbsent(absent);
                allUserSectionPdfForm.setTotalPresent(present);
                allUserSectionPdfForm.setTotalLate(late);
                allUserSectionPdfForm.setTotalOngoing(ongoing);
                allUserSectionPdfForm.setTotalOntime(ontime);
                allUserSectionPdfForm.setSectionlogPdfForm(sectionlogPdfForms);
            }
            beanList.add(allUserSectionPdfForm);
        }

    }
}
