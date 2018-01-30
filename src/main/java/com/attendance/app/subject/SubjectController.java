package com.attendance.app.subject;

import com.attendance.domain.component.PageWrapper;
import com.attendance.domain.entity.Subject;
import com.attendance.domain.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;

/**
 * Created by developer on 8/1/2561.
 */
@Controller
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @RequestMapping("/list")
    public ModelAndView list(ModelAndView modelAndView, Principal principal,@PageableDefault(value= 10) Pageable pageable){
        modelAndView.setViewName("subject/list");
        Page<Subject> subjectPageList = subjectService.getAllPageSubject(pageable);
        modelAndView.addObject("page",subjectPageList);
        modelAndView.addObject("subjects",subjectPageList.getContent());
        modelAndView.addObject("url","/subject/list");
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(ModelAndView modelAndView,Principal principal,SubjectAddForm subjectAddForm){
        modelAndView.setViewName("subject/create");
        modelAndView.addObject("subjectAddForm",subjectAddForm);
        modelAndView.addObject("subejectList",subjectService.getAllSubject());
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView add(@Valid SubjectAddForm subjectAddForm, BindingResult bindingResult,ModelAndView modelAndView,Principal principal){
        Subject subject1 = subjectService.findByName(subjectAddForm.getName());
        if(subject1 != null) {
            if (subjectAddForm.getName().equals(subject1.getName())) {
                FieldError error = new FieldError("subjectAddForm", "name", "Duplicate subject name");
                bindingResult.addError(error);
            }
        }
        Subject subject2 = subjectService.findByCode(subjectAddForm.getCode());
        if(subject2 != null) {
            if (subjectAddForm.getCode().equals(subject2.getCode())){
                FieldError error = new FieldError("subjectAddForm", "code", "Duplicate subject code");
                bindingResult.addError(error);
            }
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("subject/create");
            modelAndView.addObject("subjectAddForm",subjectAddForm);
            modelAndView.addObject("subejectList",subjectService.getAllSubject());
        }else {
            Subject subject = new Subject();
            subject.setCode(subjectAddForm.getCode());
            subject.setName(subjectAddForm.getName());
            subject.setDepartment(subjectAddForm.getDepartment());
            subject.setFaculty(subjectAddForm.getFaculty());
            subject.setCreated(new Date());
            subjectService.save(subject);
            modelAndView.setViewName("redirect:/subject/list");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public void delete(@RequestParam(value = "id") String data){
        subjectService.delete(Integer.parseInt(data));
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable int id, ModelAndView modelAndView,Principal principal,SubjectAddForm subjectAddForm){
        modelAndView.setViewName("subject/edit");
        modelAndView.addObject("subjectId",id);
        modelAndView.addObject("subject",subjectService.findOneById(id));
        modelAndView.addObject("subjectAddForm",subjectAddForm);
        return modelAndView;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public ModelAndView edited(@PathVariable int id,@Valid SubjectAddForm subjectAddForm,BindingResult bindingResult,
                               ModelAndView modelAndView,Principal principal){
        if(subjectAddForm.getName().equals(subjectService.findByName(subjectAddForm.getName()).getName())){
            FieldError error = new FieldError("subjectAddForm","name","Duplicate subject name");
            bindingResult.addError(error);
        }
        if(subjectAddForm.getCode().equals(subjectService.findByCode(subjectAddForm.getCode()).getCode())){
            FieldError error = new FieldError("subjectAddForm","code","Duplicate subject code");
            bindingResult.addError(error);
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("subject/edit");
            modelAndView.addObject("subjectAddForm",subjectAddForm);
        }else {
            Subject subject = new Subject();
            subject.setId(id);
            subject.setCode(subjectAddForm.getCode());
            subject.setName(subjectAddForm.getName());
            subject.setDepartment(subjectAddForm.getDepartment());
            subject.setFaculty(subjectAddForm.getFaculty());
            subject.setCreated(new Date());
            subjectService.save(subject);
            modelAndView.setViewName("redirect:/subject/list");
        }
        return modelAndView;
    }
}
