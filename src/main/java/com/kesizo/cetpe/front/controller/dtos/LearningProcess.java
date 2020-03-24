package com.kesizo.cetpe.front.controller.dtos;

import java.util.HashMap;
import java.util.Map;

public class LearningProcess {

private Integer id;
private String name;
private String description;
private String startingDateTime;
private String endDateTime;
private Boolean isCal1Available;
private Boolean isCal2Available;
private Boolean isCal3Available;
private Boolean isCalFAvailable;
private Integer limitCal1;
private Integer limitCal2;
private Integer limitRev1;
private Integer limitRev2;
private Integer weightParamA;
private Integer weightParamB;
private Integer weightParamC;
private Integer weightParamD;
private Integer weightParamE;
private Status status;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public String getStartingDateTime() {
return startingDateTime;
}

public void setStartingDateTime(String startingDateTime) {
this.startingDateTime = startingDateTime;
}

public String getEndDateTime() {
return endDateTime;
}

public void setEndDateTime(String endDateTime) {
this.endDateTime = endDateTime;
}

public Boolean getIsCal1Available() {
return isCal1Available;
}

public void setIsCal1Available(Boolean isCal1Available) {
this.isCal1Available = isCal1Available;
}

public Boolean getIsCal2Available() {
return isCal2Available;
}

public void setIsCal2Available(Boolean isCal2Available) {
this.isCal2Available = isCal2Available;
}

public Boolean getIsCal3Available() {
return isCal3Available;
}

public void setIsCal3Available(Boolean isCal3Available) {
this.isCal3Available = isCal3Available;
}

public Boolean getIsCalFAvailable() {
return isCalFAvailable;
}

public void setIsCalFAvailable(Boolean isCalFAvailable) {
this.isCalFAvailable = isCalFAvailable;
}

public Integer getLimitCal1() {
return limitCal1;
}

public void setLimitCal1(Integer limitCal1) {
this.limitCal1 = limitCal1;
}

public Integer getLimitCal2() {
return limitCal2;
}

public void setLimitCal2(Integer limitCal2) {
this.limitCal2 = limitCal2;
}

public Integer getLimitRev1() {
return limitRev1;
}

public void setLimitRev1(Integer limitRev1) {
this.limitRev1 = limitRev1;
}

public Integer getLimitRev2() {
return limitRev2;
}

public void setLimitRev2(Integer limitRev2) {
this.limitRev2 = limitRev2;
}

public Integer getWeightParamA() {
return weightParamA;
}

public void setWeightParamA(Integer weightParamA) {
this.weightParamA = weightParamA;
}

public Integer getWeightParamB() {
return weightParamB;
}

public void setWeightParamB(Integer weightParamB) {
this.weightParamB = weightParamB;
}

public Integer getWeightParamC() {
return weightParamC;
}

public void setWeightParamC(Integer weightParamC) {
this.weightParamC = weightParamC;
}

public Integer getWeightParamD() {
return weightParamD;
}

public void setWeightParamD(Integer weightParamD) {
this.weightParamD = weightParamD;
}

public Integer getWeightParamE() {
return weightParamE;
}

public void setWeightParamE(Integer weightParamE) {
this.weightParamE = weightParamE;
}

public Status getStatus() {
return status;
}

public void setStatus(Status status) {
this.status = status;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}