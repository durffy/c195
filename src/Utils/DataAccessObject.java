/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author CDuffy
 */
public abstract class DataAccessObject <T extends DataTransferObject>{
    protected final Connection connection;
    
    public DataAccessObject(Connection connection){
        super();
        this.connection = connection;
    }
    
    public abstract T findById(int id);
    public abstract ObservableList<T> findAll();
    public abstract T update(T dto);
    public abstract T create(T dto);
    public abstract void delete(int id);
    
}
