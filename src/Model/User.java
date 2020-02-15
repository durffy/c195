/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Utils.DataTransferObject;
import java.util.Calendar;

/**
 *
 * @author CDuffy
 */
public class User implements DataTransferObject{
    
    private int userId;
    public static String userName = "test";
    private String password;
    private int active;
    private Calendar createdate;
    private Calendar lastUpdate;
    private String createdBy;
    private String lastUpdateBy;

    public User(int userId, String userName, String password, int active, Calendar createdate, Calendar lastUpdate, String createdBy, String lastUpdateBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.createdate = createdate;
        this.lastUpdate = lastUpdate;
        this.createdBy = createdBy;
        this.lastUpdateBy = lastUpdateBy;
    }
    
    @Override
    public long getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static String getUserName() {
        return userName;
    }


    public String getPassword() {
        return password;
    }


    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Calendar getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Calendar createdate) {
        this.createdate = createdate;
    }

    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }


    
    
    
}
