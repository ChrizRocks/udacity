package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "SCHEDULE")
public class Schedule {
    @Id
    @GeneratedValue
    @Column(name = "SCHEDULE_ID", unique = true)
    private long scheduleId;
    @OneToMany(fetch = FetchType.LAZY)
    @Column(name = "SCHEDULE_EMPLOYEEIDS")
    private List<Employee> employeeIds;
    @OneToMany(fetch = FetchType.LAZY)
    @Column(name = "SCHEDULE_PETIDS")
    private List<Pet> petIds;

    @Column(name="SCHEDULE_DATE")
    private LocalDate date;

    @ElementCollection
    @Column(name = "SCHEDULE_ACTIVITIES")
    private Set<EmployeeSkill> activities;

    public long getId() {
        return scheduleId;
    }

    public void setId(long id) {
        this.scheduleId = id;
    }

    public List<Pet> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Pet> petIds) {
        this.petIds = petIds;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
