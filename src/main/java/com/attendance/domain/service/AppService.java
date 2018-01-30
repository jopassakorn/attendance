package com.attendance.domain.service;

import com.attendance.config.oauth2.GoogleUser;
import com.attendance.domain.entity.Section;
import com.attendance.domain.entity.Sectionlog;
import com.attendance.domain.entity.User;
import com.attendance.domain.repository.HolidayRepository;
import com.attendance.domain.repository.UserRepository;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by developer on 8/1/2561.
 */
@Service
public class AppService {

    @Autowired
    HolidayRepository holidayRepository;

    @Autowired
    UserRepository userRepository;

    public User getUser(Principal principal){
        Authentication auth = (Authentication) principal;
        ObjectMapper objectMapper = new ObjectMapper();
        Object detail = ((OAuth2Authentication) auth).getUserAuthentication().getDetails();
        Map<String, Object> map = objectMapper.convertValue(detail, Map.class);
        GoogleUser googleUser = GoogleUser.fromUserInfoMap(map);
        return userRepository.findByEmail(googleUser.getEmail());
    }

    public String getDay(int day){
        if(day == 6){
            return "Sunday";
        }else if(day == 0){
            return "Monday";
        }else if(day == 1){
            return "Tuesday";
        }else if(day == 2){
            return "Wednesday";
        }else if(day == 3){
            return "Thursday";
        }else if(day == 4){
            return "Friday";
        }else{
            return "Saturday";
        }
    }

    public int getCurrentSemester(){
        LocalDate today = LocalDate.now();
        if(today.getMonthValue() > 4 && today.getMonthValue() < 11){
            return 1;
        }else{
            return 2;
        }
    }

    public int getCurrentYear(){
        LocalDate today = LocalDate.now();
        if(today.getMonthValue()<5){
            return today.getYear() - 1;
        }else{
            return today.getYear();
        }
    }

    public String[] getDayList(){
        String[] day = new String[]{"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        return day;
    }

    public String getHour(int seconds){
        if(seconds/3600 < 10){
            return "0" + Integer.toString(seconds/3600);
        }else{
            return Integer.toString(seconds/3600);
        }
    }

    public String getMinute(int seconds){
        int hour = seconds/3600;
        int secMin = seconds - (hour * 3600);
        if(secMin/60 < 10){
            return "0"+secMin/60;
        }else {
            return Integer.toString(secMin/60);
        }
    }

    public Map<Integer,String> convertSecondsToHour(List<Section> sectionList){
        Map<Integer,String> map = new HashMap<>();
        for(Section section : sectionList){
            StringBuilder hour = new StringBuilder();
            hour.append(getHour(section.getSecStarted()) + ":");
            hour.append(getMinute(section.getSecStarted()));
            map.put(section.getId(),hour.toString());
        }
        return map;
    }

    public Page<Section> getAllPastSection(List<Section> allSectionList){
        List<Section> curSections = new ArrayList<>();
        for(Section section : allSectionList){
            if(section.getSemester() != getCurrentSemester() || section.getYear() != getCurrentYear()){
                curSections.add(section);
            }
        }
        Page<Section> page = new PageImpl<>(curSections);
        return page;
    }

    public int[] calSectionlogResult(List<Sectionlog> sectionlogList){
        int[] result = {0,0,0,0,0,0,0};

        for(Sectionlog sectionlog : sectionlogList){
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
        return result;
    }

    public String getLateMinutes(int startsec,int cinsec){
        StringBuilder result = new StringBuilder();
        if(cinsec > startsec){
            int resultSec = cinsec - startsec;
            if(resultSec/3600 > 1){
                result.append("0" + resultSec/3600 + ":");
                int minSec = cinsec - (3600 *(cinsec/3600));
                if(minSec/60 < 10){
                    result.append("0" + minSec/60);
                }else {
                    result.append(minSec/60);
                }
            }else if(resultSec/3600 > 10){
                result.append("0" + resultSec/3600 + ":");
                int minSec = cinsec - (3600 *(cinsec/3600));
                if(minSec/60 < 10){
                    result.append(minSec/60);
                }else {
                    result.append(minSec/60);
                }
            } else{
                result.append("00" + ":");
                if(resultSec/60 < 10){
                    result.append("0" + resultSec/60);
                }else{
                    result.append(resultSec/60);
                }
            }
        }else{
            result.append("00:00");
        }
        return result.toString();
    }
}
