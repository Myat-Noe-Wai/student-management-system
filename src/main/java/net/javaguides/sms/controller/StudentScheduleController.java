package net.javaguides.sms.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import net.javaguides.sms.entity.ClassSchedule;
import net.javaguides.sms.service.ScheduleService;

@Controller
@RequestMapping("/student")
@PreAuthorize("hasRole('STUDENT')")
public class StudentScheduleController {
    private final ScheduleService scheduleService;

    public StudentScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/schedule")
    public String viewSchedule(Model model, Principal principal) {
        List<ClassSchedule> upcomingClasses = scheduleService.getUpcomingClasses();
        model.addAttribute("upcomingClasses", upcomingClasses);
        return "schedule";
    }
}

