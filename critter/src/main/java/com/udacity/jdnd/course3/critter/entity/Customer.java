package com.udacity.jdnd.course3.critter.entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long customerId;

    @Nationalized
    @Column(name="CUSTOMER_NAME",nullable = false)
    private String name;

    @Column(name = "CUSTOMER_NUMBER")
    private String phoneNumber;

    @Column(name= "CUSTOMER_NOTES", length = 512)
    private String notes;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Pet> pets;

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public Customer(){

    }

    public Customer(Long id, String name, String phoneNumber, String notes, List<Pet> pets) {
        this.customerId = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        this.pets = pets;
    }

    /*public Customer(long id, String name, String phoneNumber, String notes, List<Pet> petIds) {
            this.id = id;
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.notes = notes;
            this.petIds = petIds;
        }

            private List<Pet> petIds;


            public List<Pet> getPetIds() {
                return petIds;
            }

            public void setPetIds(List<Pet> petIds) {
                this.petIds = petIds;
            }
        */
    public Long getId() {
        return customerId;
    }

    public void setId(Long id) {
        this.customerId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
