package com.kesizo.cetpe.front.controller.dtos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class LearningProcess {

    private Long id; //Primitive types cannot be used on dtos because of the mapping with Jackson
    private String name;
    private String description;
    private LocalDateTime starting_date_time;
    private LocalDateTime end_date_time;
    private Boolean is_cal1_available;
    private Boolean is_cal2_available;
    private Boolean is_cal3_available;
    private Boolean is_calF_available;
    private Float limit_cal1;
    private Float limit_cal2;
    private Float limit_rev1;
    private Float limit_rev2;
    private Integer weight_param_A;
    private Integer weight_param_B;
    private Integer weight_param_C;
    private Integer weight_param_D;
    private Integer weight_param_E;
    private Status learning_process_status;

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

    public String getDescription() {
    return description;
    }

    public void setDescription(String description) {
    this.description = description;
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

    public Boolean getIs_cal1_available() {
        return is_cal1_available;
    }

    public void setIs_cal1_available(Boolean is_cal1_available) {
        this.is_cal1_available = is_cal1_available;
    }

    public Boolean getIs_cal2_available() {
        return is_cal2_available;
    }

    public void setIs_cal2_available(Boolean is_cal2_available) {
        this.is_cal2_available = is_cal2_available;
    }

    public Boolean getIs_cal3_available() {
        return is_cal3_available;
    }

    public void setIs_cal3_available(Boolean is_cal3_available) {
        this.is_cal3_available = is_cal3_available;
    }

    public Boolean getIs_calF_available() {
        return is_calF_available;
    }

    public void setIs_calF_available(Boolean is_calF_available) {
        this.is_calF_available = is_calF_available;
    }

    public Float getLimit_cal1() {
        return limit_cal1;
    }

    public void setLimit_cal1(Float limit_cal1) {
        this.limit_cal1 = limit_cal1;
    }

    public Float getLimit_cal2() {
        return limit_cal2;
    }

    public void setLimit_cal2(Float limit_cal2) {
        this.limit_cal2 = limit_cal2;
    }

    public Float getLimit_rev1() {
        return limit_rev1;
    }

    public void setLimit_rev1(Float limit_rev1) {
        this.limit_rev1 = limit_rev1;
    }

    public Float getLimit_rev2() {
        return limit_rev2;
    }

    public void setLimit_rev2(Float limit_rev2) {
        this.limit_rev2 = limit_rev2;
    }

    public Integer getWeight_param_A() {
        return weight_param_A;
    }

    public void setWeight_param_A(Integer weight_param_A) {
        this.weight_param_A = weight_param_A;
    }

    public Integer getWeight_param_B() {
        return weight_param_B;
    }

    public void setWeight_param_B(Integer weight_param_B) {
        this.weight_param_B = weight_param_B;
    }

    public Integer getWeight_param_C() {
        return weight_param_C;
    }

    public void setWeight_param_C(Integer weight_param_C) {
        this.weight_param_C = weight_param_C;
    }

    public Integer getWeight_param_D() {
        return weight_param_D;
    }

    public void setWeight_param_D(Integer weight_param_D) {
        this.weight_param_D = weight_param_D;
    }

    public Integer getWeight_param_E() {
        return weight_param_E;
    }

    public void setWeight_param_E(Integer weight_param_E) {
        this.weight_param_E = weight_param_E;
    }

    public Status getLearning_process_status() {
        return learning_process_status;
    }

    public void setLearning_process_status(Status learning_process_status) {
        this.learning_process_status = learning_process_status;
    }

    @Override
    public String toString() {
        return "LearningProcess{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", starting_date_time=" + starting_date_time +
                ", end_date_time=" + end_date_time +
                ", is_cal1_available=" + is_cal1_available +
                ", is_cal2_available=" + is_cal2_available +
                ", is_cal3_available=" + is_cal3_available +
                ", is_calF_available=" + is_calF_available +
                ", limit_cal1=" + limit_cal1 +
                ", limit_cal2=" + limit_cal2 +
                ", limit_rev1=" + limit_rev1 +
                ", limit_rev2=" + limit_rev2 +
                ", weight_param_A=" + weight_param_A +
                ", weight_param_B=" + weight_param_B +
                ", weight_param_C=" + weight_param_C +
                ", weight_param_D=" + weight_param_D +
                ", weight_param_E=" + weight_param_E +
                ", learning_process_status=" + learning_process_status +
                '}';
    }

    public  Map<String, Object> toMap() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LinkedHashMap<String, Object> mapOfProperties = new LinkedHashMap<>();
        mapOfProperties.put("id",this.id);
        mapOfProperties.put("name",this.name);
        mapOfProperties.put("description",this.description);
        mapOfProperties.put("starting_date_time",this.starting_date_time.format(formatter));
        mapOfProperties.put("end_date_time",this.end_date_time.format(formatter));
        mapOfProperties.put("is_cal1_available",this.is_cal1_available);
        mapOfProperties.put("is_cal2_available",this.is_cal2_available);
        mapOfProperties.put("is_cal3_available",this.is_cal3_available);
        mapOfProperties.put("is_calF_available",this.is_calF_available);
        mapOfProperties.put("limit_cal1",this.limit_cal1);
        mapOfProperties.put("limit_cal2",this.limit_cal2);
        mapOfProperties.put("limit_rev1",this.limit_rev1);
        mapOfProperties.put("limit_rev2",this.limit_rev2);
        mapOfProperties.put("weight_param_A",this.weight_param_A);
        mapOfProperties.put("weight_param_B",this.weight_param_B);
        mapOfProperties.put("weight_param_C",this.weight_param_C);
        mapOfProperties.put("weight_param_D",this.weight_param_D);
        mapOfProperties.put("weight_param_E",this.weight_param_E);
        mapOfProperties.put("learning_process_status_id",this.learning_process_status.getId());

        return mapOfProperties;

    }
}