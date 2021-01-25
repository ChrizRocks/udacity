package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.pet.PetType;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PET")
public class Pet {
    @Id
    @GeneratedValue
    @Column(name = "PET_ID", unique = true)
    private long petId;

    @Enumerated(EnumType.STRING)
    @Column(name = "PET_TYPE")
    private PetType type;

    @Nationalized
    @Column(name = "PET_NAME")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMER_ID")//FOREIGN KEY FROM Owner
    private Customer customer;
    //private Customer owner;//error here
    //private long ownerId;




    @Column(name = "PET_BIRTHDATE")
    private LocalDate birthDate;

    @Column(name = "PET_NOTES")
    private String notes;

    public Pet(){

    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Pet(long id, PetType type, String name, Customer customer, LocalDate birthDate, String notes) {
        this.petId = id;
        this.type = type;
        this.name = name;
        this.customer = customer;
        this.birthDate = birthDate;
        this.notes = notes;
    }
    /*
    public Pet(long id, PetType type, String name, Customer owner, LocalDate birthDate, String notes) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.owner = owner;
        this.birthDate = birthDate;
        this.notes = notes;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }*/

    public long getId() {
        return petId;
    }

    public void setId(long id) {
        this.petId = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
