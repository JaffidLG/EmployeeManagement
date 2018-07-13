/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.NotFoundException;

/**
 *
 * @author Jaffid
 */
public class EmployeeManagementFeedFileConnector extends EmployeeManagementFeedConnector{
    
        public EmployeeManagementFeedFileConnector(){
            employeesData = new LinkedList<>();
        
        }
        public EmployeeManagementFeedFileConnector(String filePath) throws FileNotFoundException{
            this();
            loadData(filePath);
                
        }
        
        @Override
        public void loadData(String filePath) throws NotFoundException{
            InputStream in;
            in =  this.getClass().getResourceAsStream(filePath);
            if(in!=null){
                fillEmployeesData(in);
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(EmployeeManagementFeedFileConnector.class.getName()).log(Level.SEVERE, null, ex);
                    throw new NotFoundException(filePath);
                }
            }else{
                throw new NotFoundException(filePath);
            }
        }
}
