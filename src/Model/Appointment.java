/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Calendar;

/**
 *
 * @author CDuffy
 */
public class Appointment {
    private int appointmentId;
    private int customerId;
    private int userId;

    private String description;
    private String location;
    private String contact;
    private String type;
    
    //TODO: must convert from UTC to Local and back in get/set
    private Calendar startTime;
    private Calendar endTime;
    private Calendar createdDate;
    private Calendar lastUpdate;
    
    //TODO: limit String Size
    private String createdBy; //VARCHAR(255)
    private String url; //VARCHAR(255)
    private String title; //VARCHAR(40)
    private String lastUpdateBy; //VARCHAR(40)
}
