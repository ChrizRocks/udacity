package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule newSchedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, newSchedule);
        List < Long > employeeIds = scheduleDTO.getEmployeeIds();
        List < Long > petIds = scheduleDTO.getPetIds();
        List < Employee > employeeList = new ArrayList < > ();
        List < Pet > petList = new ArrayList < > ();
        if (employeeIds != null) {
            for (Long id: employeeIds) {
                employeeList.add(employeeService.findById(id));
            }
        }
        if (petIds != null) {
            for (Long id: petIds) {
                petList.add(petService.findById(id));
            }
        }
        newSchedule.setEmployeeIds(employeeList);
        newSchedule.setPetIds(petList);
        newSchedule.setActivities(scheduleDTO.getActivities());
        scheduleService.saveSchedule(newSchedule);
        scheduleDTO.setId(newSchedule.getId());
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> tmp = scheduleService.getAllSchedules();
        List<ScheduleDTO> ret = new ArrayList<>();
        ScheduleDTO newSchedule;
        if(tmp!=null) {
            for (Schedule schedule : tmp) {
                newSchedule = new ScheduleDTO();
                BeanUtils.copyProperties(schedule, newSchedule);
                newSchedule.setEmployeeIds(schedule.getEmployeeIds().stream().map(Employee::getId).collect(Collectors.toList()));
                List<Long> pets = new ArrayList<>();
                for(Pet p : schedule.getPetIds()){
                    System.out.println("PET ID: " + p.getId());
                    pets.add(p.getId());
                }
                newSchedule.setPetIds(pets);
                ret.add(newSchedule);
            }
        }
        return ret;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> tmp = scheduleService.getScheduleForPet(petId);
        List<ScheduleDTO> ret = new ArrayList<>();
        ScheduleDTO newSchedulePet;
        if(tmp != null){
            for(Schedule schedule : tmp){
                newSchedulePet = new ScheduleDTO();
                newSchedulePet.setId(schedule.getId());
                newSchedulePet.setActivities(schedule.getActivities());
                newSchedulePet.setDate(schedule.getDate());
                newSchedulePet.setEmployeeIds(schedule.getEmployeeIds().stream().map(Employee::getId).collect(Collectors.toList()));
                List<Long> pets = new ArrayList<>();
                for(Pet p : schedule.getPetIds()){
                    System.out.println("PET ID: " + p.getId());
                    pets.add(p.getId());
                }
                newSchedulePet.setPetIds(pets);
                ret.add(newSchedulePet);
            }
        }
        return ret;
    }


    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> tmp = scheduleRepository.findByEmployeeIds_EmployeeId(employeeId);
        List<ScheduleDTO> ret = new ArrayList<>();
        ScheduleDTO newScheduleEmployee;
        if(tmp != null){
            for(Schedule schedule : tmp){
                newScheduleEmployee = new ScheduleDTO();
                newScheduleEmployee.setId(schedule.getId());
                newScheduleEmployee.setActivities(schedule.getActivities());
                newScheduleEmployee.setDate(schedule.getDate());
                newScheduleEmployee.setEmployeeIds(schedule.getEmployeeIds().stream().map(Employee::getId).collect(Collectors.toList()));
                List<Long> pets = new ArrayList<>();
                for(Pet p : schedule.getPetIds()){
                    System.out.println("PET ID: " + p.getId());
                    pets.add(p.getId());
                }
                newScheduleEmployee.setPetIds(pets);
                ret.add(newScheduleEmployee);
            }
        }

        return ret;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        ScheduleDTO newScheduleCustomer;
        List<ScheduleDTO> ret = new ArrayList<>();
        Customer customer = customerService.findById(customerId);
        List<Pet> pets = customer.getPets();
        ArrayList<Schedule> schedules = new ArrayList<>();
        for(Pet pet : pets){
            schedules.addAll(scheduleRepository.findByPetIds_PetId(pet.getId()));
        }
        if(schedules != null){
            for(Schedule schedule : schedules){
                newScheduleCustomer = new ScheduleDTO();
                newScheduleCustomer.setId(schedule.getId());
                newScheduleCustomer.setActivities(schedule.getActivities());
                newScheduleCustomer.setDate(schedule.getDate());
                newScheduleCustomer.setEmployeeIds(schedule.getEmployeeIds().stream().map(Employee::getId).collect(Collectors.toList()));
                List<Long> petIds = new ArrayList<>();
                for(Pet p : schedule.getPetIds()){
                    System.out.println("PET ID: " + p.getId());
                    petIds.add(p.getId());
                }
                newScheduleCustomer.setPetIds(petIds);
                ret.add(newScheduleCustomer);
            }
        }
        return ret;
    }
}
