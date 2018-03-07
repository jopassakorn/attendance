package com.attendance.domain.service;

import com.attendance.domain.entity.Section;
import com.attendance.domain.entity.Sectionlog;
import com.attendance.domain.repository.HolidayRepository;
import com.attendance.domain.repository.SectionlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by developer on 18/1/2561.
 */
@Service
public class SectionlogService extends AppService {

    @Autowired
    SectionlogRepository sectionlogRepository;

    @Autowired
    HolidayRepository holidayRepository;

    public int[] getSectionStatus(List<Section> sections){
        int[] result = {0,0,0,0,0,0}; // ongoing present absent late ontime holiday
        for(Section section : sections){
            List<Sectionlog> sectionlogs = findAllBySectionId(section.getId());
            for(Sectionlog sectionlog : sectionlogs){
                if(sectionlog.getStatus().equals("waiting")){
                    result[0] = result[0] + 1;
                }else if(sectionlog.getStatus().equals("holiday")){
                    result[5] = result[5] + 1;
                } else{
                if(sectionlog.getStatus().equals("late")){
                    result[1] = result[1] + 1;
                    result[3] = result[3] + 1;
                }else if(sectionlog.getStatus().equals("ontime")){
                    result[1] = result[1] + 1;
                    result[4] = result[4] + 1;
                }else if(sectionlog.getStatus().equals("absent")){
                    result[2] = result[2] + 1;
                }
                }
            }
        }
        return result;
    }

    public void deleteBySectionId(int id){
        List<Sectionlog> sectionlogs = sectionlogRepository.findAllBySectionId(id);
        for(Sectionlog sectionlog : sectionlogs){
            sectionlogRepository.delete(sectionlog);
        }
    }

    public void save(Sectionlog sectionlog){ sectionlogRepository.save(sectionlog);}

    public void generatedSectionlog(Date startDate,Date endDate,int sectionId){
        LocalDate today = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localStartDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        while(localStartDate.isBefore(localEndDate)){
            if(!isHoliday(Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant())) && localStartDate.isAfter(today)) {
                Sectionlog sectionlog = new Sectionlog();
                sectionlog.setStatus("waiting");
                sectionlog.setClockInSec(0);
                sectionlog.setSectionId(sectionId);
                sectionlog.setWorkDate(Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                sectionlogRepository.save(sectionlog);
            }else if(localStartDate.isBefore(today)){
                Sectionlog sectionlog = new Sectionlog();
                sectionlog.setStatus("absent");
                sectionlog.setClockInSec(0);
                sectionlog.setSectionId(sectionId);
                sectionlog.setWorkDate(Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                sectionlogRepository.save(sectionlog);
            }else if(localStartDate.isEqual(today)){
                LocalTime timePoint = LocalTime.now();
                LocalTime sixPM = LocalTime.parse("17:55:00");
                if(timePoint.isBefore(sixPM)){
                    Sectionlog sectionlog = new Sectionlog();
                    sectionlog.setStatus("waiting");
                    sectionlog.setClockInSec(0);
                    sectionlog.setSectionId(sectionId);
                    sectionlog.setWorkDate(Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    sectionlogRepository.save(sectionlog);
                }else{
                    Sectionlog sectionlog = new Sectionlog();
                    sectionlog.setStatus("absent");
                    sectionlog.setClockInSec(0);
                    sectionlog.setSectionId(sectionId);
                    sectionlog.setWorkDate(Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    sectionlogRepository.save(sectionlog);
                }
            }else{
                Sectionlog sectionlog = new Sectionlog();
                sectionlog.setStatus("holiday");
                sectionlog.setClockInSec(0);
                sectionlog.setSectionId(sectionId);
                sectionlog.setWorkDate(Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                sectionlogRepository.save(sectionlog);
            }
            localStartDate = localStartDate.plusWeeks(1);
        }
    }

    public boolean isHoliday(Date date){
        if(holidayRepository.findOneByDate(date) != null){
            return true;
        }else{
            return false;
        }
    }

    public Map<Integer,String> getLateMinMap(List<Sectionlog> sectionlogList,int startsec){
        Map<Integer,String> map = new HashMap<>();
        for(Sectionlog sectionlog : sectionlogList){
            map.put(sectionlog.getId(),getLateMinutes(startsec,sectionlog.getClockInSec()));
        }
        return map;
    }

    public List<Sectionlog> findAllBySectionId(int sectionId){ return sectionlogRepository.findAllBySectionId(sectionId);}

    public List<Sectionlog> findAllNoWaitingSection(int sectionId){ return sectionlogRepository.findAllNoWaitingSection(sectionId);}

    public boolean isDateMatchDay(Date startDate,Date endDate,int day){
        if((startDate.getDay() - 1) == day && (endDate.getDay() - 1) == day){
            return true;
        }else {
            return false;
        }
    }
}
