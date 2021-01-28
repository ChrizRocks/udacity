package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;


    public void saveSchedule(Schedule schedule){
        scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Schedule schedule){
        scheduleRepository.delete(schedule);
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    public Schedule findScheduleById(Long scheduleId){
        Optional<Schedule> schedule = scheduleRepository.findById(scheduleId);
        if(schedule.isPresent()){
            return schedule.get();
        } else {
            throw new ObjectNotFoundException(300,"Schedule with id: "+scheduleId + " not found");
        }
    }

    public List<Schedule> getScheduleForPet(Long petId){
        return scheduleRepository.findByPetIds(petId);
    }

//    public List<Schedule> getScheduleForEmployee(long employeeId){
//        return scheduleRepository.findByEmployee(employeeId);
//    }
}
