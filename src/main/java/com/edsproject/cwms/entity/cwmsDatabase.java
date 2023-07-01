/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * OWNER: EDWARD LAI
 * PROJECT DEVELOPER: EDWARD LAI
 * APPLICATION: CAR WASH MANAGEMENT SYSTEM
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package com.edsproject.cwms.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cwms_queue_history") //Specified the table name
public class cwmsDatabase {

    @Id
    @Column(name = "entry")
    private String entry;
    @Column(name = "model")
    private String model;
    @Column(name = "plate")
    private String plate;
    @Column(name = "segment")
    private String segment;
    @Column(name = "type")
    private String type;
    @Column(name = "notes")
    private String notes;
    @Column(name = "adjustment")
    private double adjustment;
    @Column(name = "state")
    private String state;

    public void addEntry(String entry, String model, String plate, String segment, String type, String notes, double adjustment, String state) {
        this.entry = entry;
        this.model = model;
        this.plate = plate;
        this.segment = segment;
        this.type = type;
        this.notes = notes;
        this.adjustment = adjustment;
        this.state = state;
    }

    @Override //Overridden toString method to return per desired format
    public String toString() {
        return entry + " | " + model + " | " + plate + " | " + segment + " | " + type + " | " + notes + " | " + adjustment + " | " + state;
    }
}
