package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee save(Employee employee){

        return employeeRepository.save(employee);
    }

    public void delete(Employee employee){

        employeeRepository.delete(employee);
    }

    public List<Employee> findAllEmployees(){
        //List<Customer> customerList =
        return employeeRepository.findAll();
    }

    public Employee findById(long employeeId){
        Optional<Employee> owner = employeeRepository.findById(employeeId);
        if(owner.isPresent()){
            return owner.get();
        } else {
            throw new ObjectNotFoundException(300,"Customer with id: "+employeeId + " not found");
        }
    }

    public List<Employee> findAvailableEmployeesFor(Set<EmployeeSkill> skills, DayOfWeek day){
//        List<Employee> candidates = employeeRepository.findAll();
        List<Employee> candidates = employeeRepository.findByDaysAvailable(day);
        List<Employee> res = new ArrayList<>();
        for(Employee candidate : candidates){
            if(candidate.getSkills().containsAll(skills)){
//                if(candidate.getDaysAvailable().contains(day)){
//                    res.add(candidate);
//                if(candidate.getDaysAvailable().contains(day)){
                res.add(candidate);

            }
        }
        return res;
    }



}
