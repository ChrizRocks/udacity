package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    List<Schedule> findByEmployeeIds(long employeeId);
    List<Schedule> findByPetIds(long petId);
    //List<Schedule> findByCustomerId(long customerId);
}
