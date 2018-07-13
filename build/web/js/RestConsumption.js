/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @author Jaffid
 */

var GET_ALL_URI = "/employees";
var EMP_URI = "/employee";
var GET_METHOD = "GET";
var POST_METHOD = "POST";
var DELETE_METHOD = "DELETE";
var CONTENT_TYPE = "Content-type";
var APP_JSON = "application/json";
var SEARCH_STRING_ID = "?id=";

function getAllEmployees(outputFieldId) {
    var xhttp = new XMLHttpRequest();
    xhttp.open(GET_METHOD, GET_ALL_URI, true);
    xhttp.setRequestHeader(CONTENT_TYPE, APP_JSON);
    xhttp.send();
    document.getElementById(outputFieldId).textContent = JSON.parse(xhttp.responseText);
}

function getEmployee(employeeId, outputFieldId){
    var xhttp = new XMLHttpRequest();
    var uri = EMP_URI + SEARCH_STRING_ID + employeeId;
    xhttp.open(GET_METHOD, uri, true);
    xhttp.setRequestHeader(CONTENT_TYPE, APP_JSON);
    xhttp.send();
    document.getElementById(outputFieldId).textContent = JSON.parse(xhttp.responseText);
}

function deleteEmployee(employeeId, outputFieldId){
    var xhttp = new XMLHttpRequest();
    var uri = EMP_URI + SEARCH_STRING_ID + employeeId;
    xhttp.open(DELETE_METHOD, uri, true);
    xhttp.setRequestHeader(CONTENT_TYPE, APP_JSON);
    xhttp.send();
    document.getElementById(outputFieldId).textContent = JSON.parse(xhttp.responseText);
}

function createEmployee(jsonString, outputFieldId){
    var xhttp = new XMLHttpRequest();
    xhttp.open(POST_METHOD, EMP_URI, true);
    xhttp.setRequestHeader(CONTENT_TYPE, APP_JSON);
    xhttp.send(jsonString);
    document.getElementById(outputFieldId).textContent = JSON.parse(xhttp.responseText);
}
