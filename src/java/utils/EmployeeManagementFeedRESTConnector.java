package utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jaffid
 */
public class EmployeeManagementFeedRESTConnector extends EmployeeManagementFeedConnector{
    
        public EmployeeManagementFeedRESTConnector(){
            employeesData = new LinkedList<>();
        }
        
    
        public EmployeeManagementFeedRESTConnector(String URI){
            this();
            loadData(URI);
        }
        
        @Override
        public void loadData(String URI) throws NotFoundException{
            URL url;
            InputStream in;
            
            try {
                url = new URL(URI);
                HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", MediaType.APPLICATION_JSON);
                in = connection.getInputStream();
                if(in != null){
                    fillEmployeesData(in);
                }
                connection.disconnect();
            
            } catch (IOException ex) {
                Logger.getLogger(EmployeeManagementFeedRESTConnector.class.getName()).log(Level.SEVERE, null, ex);
                throw new NotFoundException(URI);
            }
        }
    
}
