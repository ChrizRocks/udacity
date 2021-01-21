package com.udacity.course3.entity.plant;

import javax.persistence.Entity;

@Entity(name = "Shrub")
public class Shrub extends Plant{

    private Double heightCm;
    private Double widthCm;

    public Double getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(Double heightCm) {
        this.heightCm = heightCm;
    }

    public Double getWidthCm() {
        return widthCm;
    }

    public void setWidthCm(Double widthCm) {
        this.widthCm = widthCm;
    }
}
