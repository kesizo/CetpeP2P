package com.kesizo.cetpe.front.controller.dtos;

import java.util.LinkedHashMap;
import java.util.Map;

public class LearningStudent {

    private String username;
    private String firstName;
    private String lastName;
    //private List<UserGroup> userGroupList;
    // private Set<ItemRateByStudent> itemRatesByStudent; //This is the answer to the items (N:M rel with independent entity)

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "LearningStudent{" +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public Map<String, Object> toMap() {

        LinkedHashMap<String, Object> mapOfProperties = new LinkedHashMap<>();
        mapOfProperties.put("username",this.username);
        mapOfProperties.put("firstName",this.firstName);
        mapOfProperties.put("lastName",this.lastName);

        return mapOfProperties;

    }
}
