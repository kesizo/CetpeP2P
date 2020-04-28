package com.kesizo.cetpe.front.controller.dtos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class AssessmentRubric {

    private Long id;
    private String title;
    private LocalDateTime starting_date_time;
    private LocalDateTime end_date_time;
    private Integer rank;
    private RubricType rubricType;
    private LearningProcess learningProcess;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStarting_date_time() {
        return starting_date_time;
    }

    public void setStarting_date_time(LocalDateTime starting_date_time) {
        this.starting_date_time = starting_date_time;
    }

    public LocalDateTime getEnd_date_time() {
        return end_date_time;
    }

    public void setEnd_date_time(LocalDateTime end_date_time) {
        this.end_date_time = end_date_time;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public RubricType getRubricType() {
        return rubricType;
    }

    public void setRubricType(RubricType rubricType) {
        this.rubricType = rubricType;
    }

    public LearningProcess getLearningProcess() {
        return learningProcess;
    }

    public void setLearningProcess(LearningProcess learningProcess) {
        this.learningProcess = learningProcess;
    }

    @Override
    public String toString() {
        return "AssessmentRubric{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", starting_date_time=" + starting_date_time +
                ", end_date_time=" + end_date_time +
                ", rank=" + rank +
                ", rubricType=" + rubricType +
                ", learningProcess=" + learningProcess +
                '}';
    }

    public Map<String, Object> toMap() {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LinkedHashMap<String, Object> mapOfProperties = new LinkedHashMap<>();
        mapOfProperties.put("id",this.id);
        mapOfProperties.put("title",this.title);
        mapOfProperties.put("starting_date_time",this.starting_date_time.format(formatter));
        mapOfProperties.put("end_date_time",this.end_date_time.format(formatter));
        mapOfProperties.put("rank", this.rank);
        mapOfProperties.put("rubricType_id", this.rubricType.getId());
        mapOfProperties.put("learningProcess_id",this.learningProcess.getId());

        return mapOfProperties;

    }
}
