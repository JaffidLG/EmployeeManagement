/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.InputStream;
import java.util.LinkedList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 *
 * @author Jaffid
 */
public abstract class EmployeeManagementFeedConnector {
        LinkedList<String> employeesData;
    
   
        
    /**
     *
     * @return List of data for employees read from the file passed to this class
     */
    public LinkedList<String> getEmployeesData(){
        return employeesData;
    }

    /**
     *
     * @param resource Path to resource data
     * @return true if the data was loaded, false in case of errors.
     * @throws java.lang.Exception When resource is not reachable
     */
    public abstract void loadData(String resource) throws Exception;
    
    void fillEmployeesData(InputStream employeesIS){
        int employeeIndex = 0;
        boolean endOfArray = false;
        JsonArray employeesJson = Json.createReaderFactory(null).createReader(employeesIS).readArray();
        JsonObject currentEmployee;

        do{
            try{
                currentEmployee = employeesJson.getJsonObject(employeeIndex);
                employeesData.add(currentEmployee.toString());
                employeeIndex++;
            }catch(java.lang.IndexOutOfBoundsException e){
                endOfArray = true;
            }
        }while(!endOfArray);
    }
    
}
