package com.kesizo.cetpe.front.controller.dtos;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserGroup {

    private Long id;
    private String name;
    private LearningProcess learningProcess;
    private LearningStudent authorizedStudent;

    private List<LearningStudent> learningStudentList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LearningProcess getLearningProcess() {
        return learningProcess;
    }

    public void setLearningProcess(LearningProcess learningProcess) {
        this.learningProcess = learningProcess;
    }

    public LearningStudent getAuthorizedStudent() {
        return authorizedStudent;
    }

    public void setAuthorizedStudent(LearningStudent authorizedStudent) {
        this.authorizedStudent = authorizedStudent;
    }

    public List<LearningStudent> getLearningStudentList() {
        if (null==learningStudentList) {
            learningStudentList = new ArrayList<>();
        }
        return learningStudentList;
    }

    public void setLearningStudentList(List<LearningStudent> learningStudentList) {
        this.learningStudentList = learningStudentList;
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", learningProcess=" + learningProcess +
                ", authorizedStudent=" + authorizedStudent +
                ", learningStudentList=" + learningStudentList +
                '}';
    }

    public Map<String, Object> toMap() {

        LinkedHashMap<String, Object> mapOfProperties = new LinkedHashMap<>();
        mapOfProperties.put("id",this.id);
        mapOfProperties.put("name",this.name);
        mapOfProperties.put("learningProcess_id",this.learningProcess !=null ? this.learningProcess.getId() :null);
        mapOfProperties.put("authorizedStudent_id",this.authorizedStudent !=null ? this.authorizedStudent.getUsername() : null);
        mapOfProperties.put("learningStudentList",this.learningStudentList !=null ? this.learningStudentList : null);

        return mapOfProperties;

    }


}

