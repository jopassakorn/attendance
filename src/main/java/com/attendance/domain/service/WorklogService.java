package com.attendance.domain.service;

import com.attendance.domain.entity.*;
import com.attendance.domain.repository.HolidayRepository;
import com.attendance.domain.repository.SectionRepository;
import com.attendance.domain.repository.SemesterlogRepository;
import com.attendance.domain.repository.WorklogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by developer on 19/1/2561.
 */
@Service
public class WorklogService extends AppService{

    @Autowired
    WorklogRepository worklogRepository;

    @Autowired
    SemesterlogRepository semesterlogRepository;

    @Autowired
    HolidayRepository holidayRepository;

    @Autowired
    SectionRepository sectionRepository;

    public Worklog findOne(int id){
        return worklogRepository.findOne(id);
    }

    public void save(Worklog worklog){
        worklogRepository.save(worklog);
    }

    public int[] getWorklogStatus(List<Worklog> worklogs){
        int[] result = {0,0,0,0,0,0}; // ongoing present absent late ontime
        for(Worklog worklog : worklogs){
            if(worklog.getStatus().equals("waiting")){
                result[0] = result[0] + 1;
            }else if(worklog.getStatus().equals("holiday")){
                result[5] = result[5] + 1;
            } else{
                if(worklog.getStatus().equals("late")){
                    result[1] = result[1] + 1;
                    result[3] = result[3] + 1;
                }else if(worklog.getStatus().equals("ontime")){
                    result[1] = result[1] + 1;
                    result[4] = result[4] + 1;
                }else if(worklog.getStatus().equals("absent")){
                    result[2] = result[2] + 1;
                }
            }
        }
        return result;
    }

    public List<Worklog> getAllCurrentWorklogByUserId(int userId){
        Semesterlog semesterlog = semesterlogRepository.findOneBySemesterAndYear(getCurrentSemester(),getCurrentYear());
        return worklogRepository.findAllByUserIdAndStartDateAndEnddate(userId,semesterlog.getStarted(),semesterlog.getEnded());
    }

    public void generateWorklog(Date startDate,int userId){
        LocalDate localStartDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Semesterlog semesterlog = semesterlogRepository.findOneBySemesterAndYear(getCurrentSemester(),getCurrentYear());
        LocalDate localEndDate = semesterlog.getEnded().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localEndDate = localEndDate.plusDays(1);
        localStartDate = localStartDate.plusDays(1);
        while (localStartDate.isBefore(localEndDate)){
            String dayOfWeek = localStartDate.getDayOfWeek().toString();
            Date date = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            if(!dayOfWeek.equals("SATURDAY") && !dayOfWeek.equals("SUNDAY")) {
                Worklog worklog = new Worklog();
                worklog.setUserId(userId);
                worklog.setDate(date);
                worklog.setClockInTime(new Time(0,0,0));
                if (holidayRepository.findOneByDate(date) != null) {
                    worklog.setStatus("holiday");
                } else {
                    worklog.setStatus("waiting");
                }
                worklog.setFinger("0");
                worklogRepository.save(worklog);
            }
            localStartDate = localStartDate.plusDays(1);
        }
    }

    public Map<Integer,String> getLateMinMap(List<Worklog> worklogList,int userId){
        List<Section> sectionList = sectionRepository.findAllSectionByUserIdAndSemesterAndYear(userId,getCurrentSemester(),getCurrentYear());
        Map<Integer,String> map = new HashMap<>();
        for(Worklog worklog : worklogList){
            int clockInSec = 0;
            for(Section section : sectionList){
                if(section.getDay() == worklog.getDate().getDay() - 1 && section.getSecStarted() < 34200){
                    clockInSec = section.getSecStarted();
                    break;
                }
            }
            if(clockInSec == 0){
                clockInSec = 34200;
            }
            map.put(worklog.getId(),getLateMinutes(clockInSec,worklog.getClockInSec()));
        }
        return map;
    }

    public Map<Integer,String> getSectionMap(int userId){
        List<Section> sectionList = sectionRepository.findAllSectionByUserId(userId);
        Map<Integer,String> map = new HashMap<>();
        for(Section section : sectionList){
            if(section.getSecStarted() < 32401){
                map.put(section.getDay(),section.getSubject().getName() + '(' + getHour(section.getSecStarted()) + ':' + getMinute(section.getSecStarted()) + ')');
            }
        }
        for(int i = 0;i < 6;i++){
            if(map.get(i) == null){
                map.put(i,"No class");
            }
        }
        return map;
    }

    public int[] getWorkResult(List<Worklog> worklogList){
        int[] result = {0,0,0};
        for(Worklog worklog : worklogList){
            if(worklog.getStatus().equals("waiting")){
                result[0] += 1;
            }else if(worklog.getStatus().equals("late")){
                result[1] += 1;
            }else if(worklog.getStatus().equals("ontime")){
                result[2] += 1;
            }
        }
        return result;
    }
}
