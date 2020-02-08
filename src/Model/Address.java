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
public class Address {
    private int addressId;
    private String address; //varchar50
    private String address2; //varchar50
    private int cityID;
    private String postalCode; // varchar10
    private String phone; //var char
    private Calendar createDate;
    private Calendar lastUpdate;
    private String createdBy;//varchar 40
    private String lastUpdateBy; //varchar 40
}
