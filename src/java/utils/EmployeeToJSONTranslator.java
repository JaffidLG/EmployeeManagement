/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.StringReader;
import java.util.Date;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import pojo.Employee;

/**
 *
 * @author Jaffid
 */
public class EmployeeToJSONTranslator {
    
 
    private static final String ID = "ID";
    private static final String FIRST_NAME = "firstName";
    private static final String MIDDLE_INITIAL = "middleInitial";
    private static final String LAST_NAME = "lastName";
    private static final String DOB = "dateOfBirth";
    private static final String DOE = "dateOfEmployment";
    
    public static final JsonObject getJsonObject(Employee employee){
        return Json.createObjectBuilder()
        .add(ID, employee.getId())
        .add(FIRST_NAME, employee.getFirstName())
        .add(MIDDLE_INITIAL, employee.getMiddleInitial())
        .add(LAST_NAME, employee.getLastName())
        .add(DOB, employee.getDateOfBirth().toString())
        .add(DOE, employee.getDateOfEmployment().toString())
        .build();
    }
    
    public static final Employee getEmployeeFromJson(int id, String jsonString){
        Employee receivedEmployee = new Employee(id);
        return updateEmployee(receivedEmployee, jsonString);
    }
        
    public static final Employee updateEmployee(Employee employee, String jsonString){
        JsonReader jr = Json.createReader(new StringReader(jsonString));
        JsonObject jo = jr.readObject();
        employee.setFirstName(jo.getString(FIRST_NAME));
        employee.setMiddleInitial(jo.getString(MIDDLE_INITIAL));
        employee.setLastName(jo.getString(LAST_NAME));
        employee.setDateOfBirth(new Date(jo.getString(DOB)));
        employee.setDateOfEmployment(new Date(jo.getString(DOE)));
       
        return employee;
    }
    
    public static final int getEmployeeId(String jsonString){
       JsonReader jr = Json.createReader(new StringReader(jsonString));
       return Integer.getInteger(jr.readObject().getString(ID));
   }
    
    public static final JsonArray getJsonArray(Map<Integer, Employee> employees){
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        for (Employee employee : employees.values()) {
            jsonArray.add(
                    getJsonObject(employee) 
                    );
        }
        return jsonArray.build();
    }
        
}
