/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.util.Date;

/**
 *
 * @author Jaffid
 */
public class Employee {
    private final int id;
    private String firstName;
    private String middleInitial;
    private String lastName;
    private Date dateOfBirth;
    private Date dateOfEmployment;

    public Employee(int id){
        this.id = id;
        firstName = new String();
        middleInitial = new String();
        lastName = new String();
        dateOfBirth = new Date();
        dateOfEmployment = new Date();
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the middleInitial
     */
    public String getMiddleInitial() {
        return middleInitial;
    }

    /**
     * @param middleInitial the middleInitial to set
     */
    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the dateOfBirth
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param dateOfBirth the dateOfBirth to set
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @return the dateOfEmployment
     */
    public Date getDateOfEmployment() {
        return dateOfEmployment;
    }

    /**
     * @param dateOfEmployment the dateOfEmployment to set
     */
    public void setDateOfEmployment(Date dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }

    /**
     * @return Comma separated values for this class
     */
    public String toString() {
        return 
                firstName
            + ", " + middleInitial
            + ", " + lastName
            + ", " + dateOfBirth.toString()
            + ", " + dateOfEmployment.toString();
    }
}
