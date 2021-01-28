package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule newSchedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO,newSchedule);
        scheduleRepository.save(newSchedule);
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> tmp = scheduleRepository.findAll();
        List<ScheduleDTO> ret = new ArrayList<>();
        BeanUtils.copyProperties(tmp,ret);
        return ret;
        //throw new UnsupportedOperationException();
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> tmp = scheduleService.getScheduleForPet(petId);
        List<ScheduleDTO> ret = new ArrayList<>();
        BeanUtils.copyProperties(tmp,ret);
        return ret;
        //scheduleRepository.findByEmployeeIds()
        //return scheduleRepository.findAllById(Long.getLong(String.valueOf(petId)));
       // throw new UnsupportedOperationException();

    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> tmp = scheduleRepository.findByEmployeeIds(employeeId);
        List<ScheduleDTO> ret = new ArrayList<>();
        BeanUtils.copyProperties(tmp,ret);
        return ret;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> tmp = scheduleRepository.findByEmployeeIds(customerId);
//        List<Schedule> tmp = scheduleRepository.findByCustomerId(customerId);
        List<ScheduleDTO> ret = new ArrayList<>();
        BeanUtils.copyProperties(tmp,ret);
        return ret;
        //throw new UnsupportedOperationException();
    }
}
