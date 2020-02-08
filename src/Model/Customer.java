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
public class Customer {
    private int customerId;
    private String customerName; //varchar 45
    private int addressId;
    private int active; // tiny int 1
    private Calendar createDate;
    private Calendar lastUpdate;
    private String createdBy;
    private String lastUpdateBy;
}
