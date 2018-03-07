package com.attendance.app.sectionlog;

import com.attendance.domain.bean.JasperPdfModelBean;
import com.attendance.domain.entity.Sectionlog;
import com.attendance.domain.service.ExportPdfService;
import com.attendance.domain.service.SectionService;
import com.attendance.domain.service.SectionlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

/**
 * Created by developer on 18/1/2561.
 */
@Controller
@RequestMapping("/sectionlog")
public class SectionlogController {

    @Autowired
    SectionlogService sectionlogService;

    @Autowired
    SectionService sectionService;

    @Autowired
    ExportPdfService exportPdfService;

    @RequestMapping("/view/{id}")
    public ModelAndView view(@PathVariable(value = "id")int id, ModelAndView modelAndView, Principal principal){
        modelAndView.setViewName("/sectionlog/view");
        List<Sectionlog> sectionlogList = sectionlogService.findAllNoWaitingSection(id);
        modelAndView.addObject("sectionlogList",sectionlogList);
        modelAndView.addObject("section",sectionService.findOne(id));
        modelAndView.addObject("lateMinMap",sectionlogService.getLateMinMap(sectionlogList,sectionService.findOne(id).getSecStarted()));
        return modelAndView;
    }

    @RequestMapping("/pdf/{id}")
    public ModelAndView exportsectionlogpdf(@PathVariable(value = "id")int id){
        JasperPdfModelBean jasperPdfModelBean = exportPdfService.getSectionlogPdfView(id);
        return new ModelAndView(jasperPdfModelBean, new HashMap());
    }

    @RequestMapping("/pdf/current/{id}")
    public ModelAndView exportallcurrentsectionlogpdf(@PathVariable(value = "id") int id){
        JasperPdfModelBean jasperPdfModelBean = exportPdfService.getAllCurrentSectionPdfView(id);
        return new ModelAndView(jasperPdfModelBean, new HashMap());
    }

    @RequestMapping("/pdf/past/{id}")
    public ModelAndView exportallpastsectionlogpdf(@PathVariable(value = "id") int id){
        JasperPdfModelBean jasperPdfModelBean = exportPdfService.getAllPastSectionPdfView(id);
        return new ModelAndView(jasperPdfModelBean, new HashMap());
    }
}
