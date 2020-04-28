package com.kesizo.cetpe.front.controller.dtos;

import java.util.LinkedHashMap;
import java.util.Map;

public class ItemRubric {

    private Long id;
    private String description;
    private int weight;
    private AssessmentRubric assessmentRubric;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public AssessmentRubric getAssessmentRubric() {
        return assessmentRubric;
    }

    public void setAssessmentRubric(AssessmentRubric assessmentRubric) {
        this.assessmentRubric = assessmentRubric;
    }

    @Override
    public String toString() {
        return "ItemRubric{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", assessmentRubric=" + assessmentRubric +
                '}';
    }

    public Map<String, Object> toMap() {

        LinkedHashMap<String, Object> mapOfProperties = new LinkedHashMap<>();
        mapOfProperties.put("id",this.id);
        mapOfProperties.put("description",this.description);
        mapOfProperties.put("weight",this.weight);
        mapOfProperties.put("assessmentRubric_id",this.assessmentRubric.getId());

        return mapOfProperties;

    }
}