/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 * 
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */

package run;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pojo.Employee;
import utils.EmployeeManagementFeedConnector;
import utils.EmployeeManagementFeedFileConnector;
import utils.EmployeeToJSONTranslator;


/**
 * REST Web Service
 *
 * @author Jaffid
 */

@Stateless
@Path("")
public class EmployeeResource {
    private static final Map<Integer, Employee> employees = new HashMap<>();
    private static final Map<Integer, Employee> inactiveEmployees = new HashMap<>();
    private static final String EMPLOYEE_PATH = "/employee";
    private static final String NOT_FOUND = "Not Found";
    private static int employeeIdCounter = 0;

    public EmployeeResource() {
        try {
            EmployeeManagementFeedFileConnector emffc;
            emffc = new EmployeeManagementFeedFileConnector("/data/employeesData.json");        
            addBatchOfEmployees(emffc); 
        } catch (Exception ex) {  
            Logger.getLogger(EmployeeResource.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Loading defaults");
            Employee e = new Employee(1);
            e.setFirstName("pedro");
            Employee e2 = new Employee(2);
            e2.setFirstName("luz");
            this.employees.put(e.getId(), e);
            this.employees.put(e2.getId(), e2);
        }
    }
    
    public EmployeeResource(EmployeeManagementFeedConnector EMF){
        this();
        addBatchOfEmployees(EMF);
    }
    
    @GET
    @Consumes(MediaType.TEXT_HTML)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/employees")
    public Response getEmployees() {
        try {
            return Response.status(Response.Status.OK).entity(
                    EmployeeToJSONTranslator.getJsonArray(employees)
            ).build();
        } catch (NumberFormatException e) {
            // Log exception
            System.err.println(e.getMessage());
        }
        
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Consumes(MediaType.TEXT_HTML)
    @Produces(MediaType.APPLICATION_JSON)
    @Path(EMPLOYEE_PATH)
    public Response getEmployee(@QueryParam("id") String id) {
        try {
            int employeeId = Integer.valueOf(id);
            
            Employee employee = employees.get(employeeId);
            if (employee != null) {
                return Response.status(Response.Status.OK).entity(
                        EmployeeToJSONTranslator.getJsonObject(employee))
                        .build();
            }
            return Response.status(Response.Status.NO_CONTENT).entity(NOT_FOUND).build();
        } catch (NumberFormatException e) {
            // Log exception
            System.err.println(e.getMessage());
        }
        
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    @Path(EMPLOYEE_PATH)
    public Response updateEmployee(String employeeData) {
       int employeeId = EmployeeToJSONTranslator.getEmployeeId(employeeData);
       Employee updateEmployee = employees.remove(employeeId);
       if(updateEmployee != null){
           try{
               employees.put(employeeId,
                       EmployeeToJSONTranslator.updateEmployee(updateEmployee, employeeData));
               return Response.status(Response.Status.OK).build();
           }catch(Exception e){
               // Log exception
               System.err.println(e.getMessage());
               employees.put(employeeId, updateEmployee);
           }
       }
       return Response.status(Response.Status.BAD_REQUEST).build();
   }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    @Path(EMPLOYEE_PATH)
    public Response createEmployee(String employeeData) {
        try{
            createEmployeeInternal(employeeData);
            return Response.status(Response.Status.OK).build(); 
        }catch(Exception e){
            // Log exception
            System.err.println(e.getMessage()); 
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
        
    }
    
    public static void createEmployeeInternal(String employeeData) throws Exception{
        EmployeeResource.employees.put(employeeIdCounter, EmployeeToJSONTranslator.getEmployeeFromJson(employeeIdCounter, employeeData)); 
        employeeIdCounter++;
    }

    @DELETE
    @Consumes(MediaType.TEXT_HTML)
    @Path(EMPLOYEE_PATH)
    public Response deleteEmployee(@QueryParam("id") String id) {
        try {
            int employeeId = Integer.valueOf(id);
            Employee removedEmployee = employees.remove(employeeId);
            if (removedEmployee != null) {
                inactiveEmployees.put(employeeId, removedEmployee);
                return Response.status(Response.Status.OK).entity("").build();
            }
            return Response.status(Response.Status.NO_CONTENT).entity(NOT_FOUND).build();
        } catch (NumberFormatException e) {
            // Log exception
            System.err.println(e.getMessage());
        }
        
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    private static void addBatchOfEmployees(EmployeeManagementFeedConnector EMF) {
        List<String> employeesData = EMF.getEmployeesData();
        for(String employeeData : employeesData){
            try {
                createEmployeeInternal(employeeData);
            } catch (Exception ex) {
                Logger.getLogger(EmployeeResource.class.getName()).log(Level.SEVERE, employeeData, ex);
            }
        }
    }
}
